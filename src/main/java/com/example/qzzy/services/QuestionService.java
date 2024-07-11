package com.example.qzzy.services;

import com.example.qzzy.dto.*;
import com.example.qzzy.models.Answer;
import com.example.qzzy.models.Question;
import com.example.qzzy.repository.AnswerRepository;
import com.example.qzzy.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    Random random = new Random();
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public Question getQuestionById(Long id) {
        return questionRepository.findById(id).orElse(null);
    }

    public Question saveQuestion(CreateQuestionRequest question) {
        List<Answer> answers = new ArrayList<>();
        question.getAnswers().forEach(item -> {
            Answer answer = Answer.builder()
                    .answer(item.getAnswer())
                    .isCorrect(item.isCorrect())
                    .build();
            answers.add(answer);
        });
        Question newQuestion = Question.builder()
                .answers(answers)
                .build();
        return questionRepository.save(newQuestion);
    }

    public Question updateQuestion(Long id, UpdateQuestionRequest dto) {
        Question question = questionRepository.findById(id).orElse(null);
        if (question != null) {
            question.setQuestion(dto.getQuestion());
            return questionRepository.save(question);
        }
        return null;
    }

    public Question updateAnswer(Long id, UpdateAnswerRequest dto) {
        Answer answer = answerRepository.findById(id).orElse(null);
        if (answer != null) {
           answer.setAnswer(dto.getAnswer());
           answerRepository.save(answer);

           return  questionRepository.findQuestionByAnswerId(answer.getId());
        }
        return null;
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    public boolean isAnswerCorrect(Long questionId, Long answerId) {
        Question question = questionRepository.findById(questionId).orElse(null);
        if (question == null) {
            return false;
        }

        for (Answer answer : question.getAnswers()) {
            if (answer.getId().equals(answerId)) {
                return answer.isCorrect();
            }
        }

        return false;
    }

    public void simulateDelay() {
        try {
            int delay;
            if (random.nextDouble() < 0.8) {
                delay = 2000 + random.nextInt(3000);
            } else {
                delay = 5000 + random.nextInt(5000);
            }
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public List<Question> getQuestionsByCategoryIds(List<Long> categoryIds) {
        return questionRepository.findQuestionByCategoryIds(categoryIds);
    }

}
