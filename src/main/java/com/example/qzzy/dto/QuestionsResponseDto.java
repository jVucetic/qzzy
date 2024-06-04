package com.example.qzzy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class QuestionsResponseDto {
    private List<QuestionDto> questions;
}
