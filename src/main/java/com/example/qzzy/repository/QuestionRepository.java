package com.example.qzzy.repository;

import com.example.qzzy.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT a.question FROM Answer a WHERE a.id = :answerId")
    Question findQuestionByAnswerId(@Param("answerId") Long answerId);
}
