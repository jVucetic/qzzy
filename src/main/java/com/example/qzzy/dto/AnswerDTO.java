package com.example.qzzy.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AnswerDTO {
    private Long id;
    private String answer;
}
