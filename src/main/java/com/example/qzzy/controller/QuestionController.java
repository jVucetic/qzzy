package com.example.qzzy.controller;

import com.example.qzzy.dto.*;
import com.example.qzzy.mappers.QuestionsAndAnswersMapper;
import com.example.qzzy.services.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;
    private final QuestionsAndAnswersMapper mapper;

    @Operation(summary = "Get all Questions and Answers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Questions and Answers found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = QuestionsResponseDto.class)
                            )
                    })
    })
    @GetMapping
    public ResponseEntity<QuestionsResponseDto> getAllQuestions() {
        List<QuestionDto> questionDtos = questionService.getAllQuestions().stream()
                .map(mapper::toQuestionDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new QuestionsResponseDto(questionDtos));
    }


    @Operation(summary = "Check if Answer is correct")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Is Answer for a Question correct",
                    content = @Content)
    })
    @GetMapping("/{questionId}/answers/{answerId}/correct")
    public boolean isAnswerCorrect(@PathVariable Long questionId, @PathVariable Long answerId) {
        questionService.simulateDelay();
        return questionService.isAnswerCorrect(questionId, answerId);
    }
}
