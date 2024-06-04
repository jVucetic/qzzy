package com.example.qzzy.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoryName;

    private Boolean selected = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Category> subcategories;

    @Builder
    public Category(Long id, String categoryName, Category parentCategory, Set<Category> subcategories) {
        this.id = id;
        this.categoryName = categoryName;
        this.parentCategory = parentCategory;
        this.subcategories = subcategories;
    }


}
