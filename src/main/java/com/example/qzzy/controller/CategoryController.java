package com.example.qzzy.controller;

import com.example.qzzy.dto.*;
import com.example.qzzy.models.Category;
import com.example.qzzy.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "Get all Categories")
    @Parameters({
            @Parameter(name = "selected", description = "Selected Categories"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Categories",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CategoryResponseDto.class),
                                    examples = @ExampleObject(
                                            value = "{\"items\":[{\"id\":1,\"name\":\"Category 1\",\"category_id\":null}," +
                                                    "{\"id\":2,\"name\":\"Subcategory 1\",\"category_id\":1}]}"))
                    })
    })
    @GetMapping
    public ResponseEntity<CategoryResponseDto> getAllCategories(@RequestParam(value = "selected", required = false) Boolean selected) {
        List<Category> categories = categoryService.findAll(selected);

        List<CategoryDto> items = new ArrayList<>();

        for (Category category : categories) {
            Long parentId = category.getParentCategory() != null ? category.getParentCategory().getId() : null;
            CategoryDto dto = new CategoryDto(category.getId(), category.getCategoryName(), parentId);
            items.add(dto);
        }

        CategoryResponseDto response = new CategoryResponseDto(items);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get Category by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Category",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Category.class))}),
            @ApiResponse(responseCode = "404", description = "Category not found",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Optional<Category> category = categoryService.findById(id);
        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new Category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryDto.class))})
    })
    @PostMapping
    public CategoryDto createCategory(@RequestBody CategoryRequestDto dto) {
        return categoryService.save(dto);
    }


    @Operation(summary = "Update an existing Category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryDto.class))}),
            @ApiResponse(responseCode = "404", description = "Category not found",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @RequestBody CategoryRequestDto dto) {
        Optional<Category> category = categoryService.findById(id);
        return category.map(value -> ResponseEntity.ok(categoryService.update(value, dto)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a Category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category deleted",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get Selected Categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get selected Categories",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SelectedCategoriesRequest.class))})
    })
    @GetMapping("/select")
    public SelectedCategoriesResponse getSelectedCategories() {
        return categoryService.getSelectedCategories();
    }

    @Operation(summary = "Select Categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Save selected Categories",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SelectedCategoriesRequest.class))})
    })
    @PostMapping("/select")
    public SelectedCategoriesResponse saveSelectedCategories(@RequestBody SelectedCategoriesRequest dto) {
        return categoryService.select(dto);
    }
}
