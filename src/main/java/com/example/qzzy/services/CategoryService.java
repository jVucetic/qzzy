package com.example.qzzy.services;

import com.example.qzzy.dto.CategoryDto;
import com.example.qzzy.dto.CategoryRequestDto;
import com.example.qzzy.dto.SelectedCategoriesRequest;
import com.example.qzzy.dto.SelectedCategoriesResponse;
import com.example.qzzy.models.Category;
import com.example.qzzy.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    public CategoryDto save(CategoryRequestDto categoryDto) {
        Category category = new Category();
        category.setCategoryName(categoryDto.getName());

        Optional<Category> parent = categoryRepository.findById(categoryDto.getParentId());
        if (parent.isPresent()) {
            category.setParentCategory(parent.get());
        } else {
            category.setParentCategory(null);
        }
        categoryRepository.save(category);

        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getCategoryName())
                .categoryId(category.getParentCategory() == null ? 0 : category.getParentCategory().getId())
                .build();
    }

    public CategoryDto update(Category category, CategoryRequestDto dto) {
        Optional<Category> parent = categoryRepository.findById(dto.getParentId());
        if (parent.isPresent()) {
            category.setParentCategory(parent.get());
        } else {
            category.setParentCategory(null);
        }

        category.setCategoryName(dto.getName());
        categoryRepository.save(category);

        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getCategoryName())
                .categoryId(category.getParentCategory() == null ? 0 : category.getParentCategory().getId())
                .build();
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    public SelectedCategoriesResponse getSelectedCategories() {
        return new SelectedCategoriesResponse(categoryRepository.findAllSelectedCategories().stream()
                .map(Category::getId).collect(Collectors.toList())
        );
    }

    public void select(SelectedCategoriesRequest selectedCategories) {
        List<Category> allCategories = new ArrayList<>();

        List<Category> oldSelectedCategories = categoryRepository.findAllSelectedCategories();
        oldSelectedCategories.forEach(item -> {
            item.setSelected(false);
            allCategories.add(item);
        });

        List<Category> categories = categoryRepository.findAllById(selectedCategories.getCategoryIds());
        categories.forEach(category -> {
            category.setSelected(true);
            allCategories.add(category);
        });

        categoryRepository.saveAll(allCategories);
    }
}
