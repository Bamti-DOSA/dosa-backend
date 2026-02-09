package com.bamti.dosa.ai.dto.quiz;

import lombok.Data;

@Data
public class AiQuizRequest {
    private String modelName; // 예: "Suspension", "V4 Engine"
    private String difficulty; // 예: "Hard", "Normal" (선택)
}