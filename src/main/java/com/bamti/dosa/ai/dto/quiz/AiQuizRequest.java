package com.bamti.dosa.ai.dto.quiz;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class AiQuizRequest {
    @NotBlank(message = "모델 이름은 필수입니다.")
    private String modelName; // 예: "Suspension", "V4 Engine"
    private String difficulty; // 예: "Hard", "Normal" (선택)
}