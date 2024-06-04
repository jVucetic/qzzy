package com.example.qzzy.mappers;

import com.example.qzzy.dto.AnswerDTO;
import com.example.qzzy.dto.UpdateAnswerRequest;
import com.example.qzzy.dto.QuestionDto;
import com.example.qzzy.models.Answer;
import com.example.qzzy.models.Question;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionsAndAnswersMapper {
    public QuestionDto toQuestionDTO(Question question) {
        return QuestionDto.builder()
                .id(question.getId())
                .question(question.getQuestion())
                .answers(toAnswerDTOs(question.getAnswers()))
                .build();
    }

    public List<AnswerDTO> toAnswerDTOs(List<Answer> answers) {
        return answers.stream().map(this::toAnswerDTO).collect(Collectors.toList());
    }

    public AnswerDTO toAnswerDTO(Answer answer) {
        return AnswerDTO.builder()
                .id(answer.getId())
                .answer(answer.getAnswer())
                .build();
    }


    public List<Answer> toAnswers(List<UpdateAnswerRequest> dtos) {
        return dtos.stream().map(this::toAnswer).collect(Collectors.toList());
    }
    public Answer toAnswer(UpdateAnswerRequest dto) {
        return Answer.builder()
                .answer(dto.getAnswer())
                .isCorrect(dto.isCorrect())
                .build();
    }
}
