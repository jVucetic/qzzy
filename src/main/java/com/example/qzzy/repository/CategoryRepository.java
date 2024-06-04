package com.example.qzzy.repository;

import com.example.qzzy.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = "select c from Category c where (c.selected = :selected or :selected is null) order by c.id")
    List<Category> findAllCategories(@Param("selected") Boolean selected);

    @Query(value = "select c.id from Category c where c.selected = true order by c.id")
    List<Long> findAllSelectedCategories();
}
