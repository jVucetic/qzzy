package com.example.qzzy.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDto {
    private Long id;
    private String name;
    private Long categoryId;

    public CategoryDto(Long id, String name, Long categoryId) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
    }
}
