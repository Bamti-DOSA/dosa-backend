package com.bamti.dosa.ai.controller;

import com.bamti.dosa.ai.dto.quiz.AiQuizRequest;
import com.bamti.dosa.ai.dto.quiz.AiQuizResponse;
import com.bamti.dosa.ai.service.quiz.AiQuizService;
import com.bamti.dosa.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "AI Quiz API", description = "3D 모델 관련 학습 퀴즈 생성 by 우혁")
@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiQuizController {

    private final AiQuizService aiQuizService;

    @Operation(summary = "퀴즈 생성", description = "특정 3D 모델(주제)에 대한 전공 수준 퀴즈를 생성합니다. (Hard, Normal 선택)")
    @PostMapping("/quiz")
    public ApiResponse<AiQuizResponse> createQuiz(@RequestBody AiQuizRequest req) {
        AiQuizResponse response = aiQuizService.generateQuiz(req);
        return ApiResponse.success(response);
    }
}