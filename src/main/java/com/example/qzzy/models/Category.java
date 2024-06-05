package com.example.qzzy.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Category {
    @Id
    private Long id;

    private String categoryName;

    private Boolean selected = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Category> subcategories;
}
