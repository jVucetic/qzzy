package com.example.qzzy.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CreateQuestionRequest {
    private String question;
    List<UpdateAnswerRequest> answers;
}
