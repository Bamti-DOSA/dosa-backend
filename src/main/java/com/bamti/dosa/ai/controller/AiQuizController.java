package com.bamti.dosa.ai.controller;

import com.bamti.dosa.ai.dto.quiz.AiQuizRequest;
import com.bamti.dosa.ai.dto.quiz.AiQuizResponse;
import com.bamti.dosa.ai.service.quiz.AiQuizService;
import com.bamti.dosa.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Tag(name = "AI Quiz API", description = "3D 모델 관련 학습 퀴즈 생성 by 우혁")
@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiQuizController {

    private final AiQuizService aiQuizService;

    @Operation(summary = "퀴즈 생성", description = "특정 3D 모델(주제)에 대한 전공 수준 퀴즈를 생성합니다. (Hard, Normal 선택)")
    @PostMapping("/quiz")
    public ApiResponse<AiQuizResponse> createQuiz(@RequestBody AiQuizRequest req) {
        if (req.getModelName() == null || req.getModelName().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "modelName은 필수입니다.");
        }
        try {
        AiQuizResponse response = aiQuizService.generateQuiz(req);
        return ApiResponse.success(response);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "퀴즈 생성 중 오류가 발생했습니다.");
        }
    }
}