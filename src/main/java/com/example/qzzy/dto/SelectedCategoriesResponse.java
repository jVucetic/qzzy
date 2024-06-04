package com.example.qzzy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SelectedCategoriesResponse {
    private List<Long> selected;
}
