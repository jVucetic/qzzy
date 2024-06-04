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

    @Operation(summary = "Get Question by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the question and answers",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = QuestionDto.class))}),
            @ApiResponse(responseCode = "404", description = "Question not found",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public QuestionDto getQuestionById(@PathVariable Long id) {
        return mapper.toQuestionDTO(questionService.getQuestionById(id));
    }

    @Operation(summary = "Create a new Question")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Question and Answers created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = QuestionDto.class))})
    })
    @PostMapping
    public QuestionDto createQuestion(@RequestBody CreateQuestionRequest question) {
        return mapper.toQuestionDTO(questionService.saveQuestion(question));
    }

    @Operation(summary = "Update an existing Question")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Question updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = QuestionDto.class))}),
            @ApiResponse(responseCode = "404", description = "Question not found",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public QuestionDto updateQuestion(@PathVariable Long id, @RequestBody UpdateQuestionRequest questionRequest) {
        return mapper.toQuestionDTO(questionService.updateQuestion(id, questionRequest));
    }

    @Operation(summary = "Update Answer for a Question")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Answer updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = QuestionDto.class))}),
            @ApiResponse(responseCode = "404", description = "Question not found",
                    content = @Content)
    })
    @PutMapping("/answer/{answerId}")
    public QuestionDto updateQuestion(@PathVariable Long answerId, @RequestBody UpdateAnswerRequest answerRequest) {
        return mapper.toQuestionDTO(questionService.updateAnswer(answerId, answerRequest));
    }

    @Operation(summary = "Delete a question")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Question and Answers deleted",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
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
