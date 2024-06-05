package com.example.qzzy.controller;

import com.example.qzzy.dto.CategoryDto;
import com.example.qzzy.dto.CategoryResponseDto;
import com.example.qzzy.dto.SelectedCategoriesRequest;
import com.example.qzzy.dto.SelectedCategoriesResponse;
import com.example.qzzy.models.Category;
import com.example.qzzy.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "Get all Categories")
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
    public ResponseEntity<CategoryResponseDto> getAllCategories() {
        List<Category> categories = categoryService.findAll();

        List<CategoryDto> items = new ArrayList<>();

        for (Category category : categories) {
            Long parentId = category.getParentCategory() != null ? category.getParentCategory().getId() : null;
            CategoryDto dto = new CategoryDto(category.getId(), category.getCategoryName(), parentId);
            items.add(dto);
        }

        CategoryResponseDto response = new CategoryResponseDto(items);

        return ResponseEntity.ok(response);
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
    @ResponseStatus(OK)
    @PostMapping("/select")
    public void saveSelectedCategories(@RequestBody SelectedCategoriesRequest dto) {
        categoryService.select(dto);
    }
}
