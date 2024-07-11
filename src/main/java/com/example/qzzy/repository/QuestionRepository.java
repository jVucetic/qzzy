package com.example.qzzy.repository;

import com.example.qzzy.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT a.question FROM Answer a WHERE a.id = :answerId")
    Question findQuestionByAnswerId(@Param("answerId") Long answerId);

    @Query("Select q from Question q where q.category.id in :categoryIds")
    List<Question> findQuestionByCategoryIds(@Param("categoryIds") List<Long> categoryIds);
}
