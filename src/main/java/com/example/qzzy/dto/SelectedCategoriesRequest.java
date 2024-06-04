package com.example.qzzy.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SelectedCategoriesRequest {
    List<Long> categoryIds;
}
