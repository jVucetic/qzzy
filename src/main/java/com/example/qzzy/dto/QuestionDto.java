package com.example.qzzy.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class QuestionDto {
    private Long id;
    private Long categoryId;
    private String question;
    private List<AnswerDTO> answers;
}
