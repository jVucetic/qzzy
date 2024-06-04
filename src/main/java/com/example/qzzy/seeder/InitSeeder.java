package com.example.qzzy.seeder;

import com.example.qzzy.models.Answer;
import com.example.qzzy.models.Category;
import com.example.qzzy.models.Question;
import com.example.qzzy.repository.CategoryRepository;
import com.example.qzzy.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class InitSeeder implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final QuestionRepository questionRepository;

    @Override
    public void run(String... args) {
        Faker faker = new Faker();
        Random random = new Random();

        List<Category> categories = new ArrayList<>();
        int numberOfCategories = 20;

        for (int i = 0; i < numberOfCategories; i++) {
            Category category = Category.builder()
                    .categoryName(faker.animal().species())
                    .parentCategory(null)
                    .build();
            categories.add(category);

            Category subcategory = Category.builder()
                    .categoryName(faker.animal().name())
                    .parentCategory(category)
                    .build();

            categories.add(subcategory);
        }

        categoryRepository.saveAll(categories);

        List<Question> questions = new ArrayList<>();
        int numberOfQuestions = 10;

        for (int i = 0; i < numberOfQuestions; i++) {
            Question question = new Question();
            question.setQuestion(faker.lorem().sentence());

            List<Answer> answers = new ArrayList<>();
            int correctAnswerIndex = random.nextInt(3);
            for (int j = 0; j < 3; j++) {
                Answer answer = new Answer();
                answer.setAnswer(faker.lorem().word());
                answer.setCorrect(j == correctAnswerIndex); // Set the first answer as correct for simplicity
                answer.setQuestion(question);
                answers.add(answer);
            }

            question.setAnswers(answers);
            questions.add(question);
        }

        questionRepository.saveAll(questions);
    }
}
