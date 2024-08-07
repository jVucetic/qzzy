package com.example.qzzy.seeder;

import com.example.qzzy.models.Answer;
import com.example.qzzy.models.Category;
import com.example.qzzy.models.Question;
import com.example.qzzy.repository.AnswerRepository;
import com.example.qzzy.repository.CategoryRepository;
import com.example.qzzy.repository.QuestionRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitSeeder implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Override
    public void run(String... args) throws IOException {


        ObjectMapper mapper = new ObjectMapper();

        InputStream is = InitSeeder.class.getResourceAsStream("/data/categories.json");
        JsonNode rootCategoriesNode = mapper.readTree(is);
        JsonNode itemsNode = rootCategoriesNode.get("items");
        for (JsonNode itemNode : itemsNode) {
            Category category = parseCategory(itemNode);
            categoryRepository.save(category);
        }


        InputStream isQuestions = InitSeeder.class.getResourceAsStream("/data/questions.json");
        JsonNode rootQuestionsNode = mapper.readTree(isQuestions);
        JsonNode questionsNode = rootQuestionsNode.get("questions");
        for (JsonNode questionNode : questionsNode) {
            Question question = parseQuestion(questionNode);
            question = questionRepository.save(question);
            saveAnswers(question, questionNode.get("answers"));
        }
    }

    private Category parseCategory(JsonNode itemNode) {
        Long id = itemNode.has("id") ? itemNode.get("id").asLong() : null;
        String name = itemNode.has("name") ? itemNode.get("name").asText() : null;
        Long parentId = itemNode.has("category_id") ? itemNode.get("category_id").asLong() : null;
        Boolean selected = itemNode.has("selected");

        Category parentCategory = null;
        if (parentId != null) {
            parentCategory = categoryRepository.findById(parentId).orElse(null);
        }

        return Category.builder()
                .id(id)
                .categoryName(name)
                .parentCategory(parentCategory)
                .subcategories(new HashSet<>())
                .selected(selected)
                .build();
    }

    private Question parseQuestion(JsonNode questionNode) {
        Long id = questionNode.get("id").asLong();
        String questionText = questionNode.get("question").asText();
        Long categoryId = questionNode.get("category_id").asLong();
        Category category = categoryRepository.findById(categoryId).orElseThrow();

        return Question.builder()
                .id(id)
                .question(questionText)
                .category(category)
                .build();
    }

    private void saveAnswers(Question question, JsonNode answersNode) {
        List<Answer> answers = new ArrayList<>();
        for (JsonNode answerNode : answersNode) {
            Answer answer = parseAnswer(answerNode);
            answer.setQuestion(question);
            answers.add(answer);
        }
        answerRepository.saveAll(answers);
    }

    private Answer parseAnswer(JsonNode answerNode) {
        Long id = answerNode.get("id").asLong();
        String answerText = answerNode.get("answer").asText();
        boolean isCorrect = answerNode.has("correct") && answerNode.get("correct").asBoolean();

        return Answer.builder()
                .id(id)
                .answer(answerText)
                .isCorrect(isCorrect)
                .build();
    }
}
