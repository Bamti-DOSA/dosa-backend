package com.bamti.dosa.ai.dto.quiz;

import lombok.Data;
import java.util.List;

@Data
public class AiQuizResponse {
    private String topic;
    private List<QuizItem> quizzes;

    @Data
    public static class QuizItem {
        private int id;
        private String question;      // 문제
        private List<String> options; // 보기 4개
        private int answer;           // 정답 인덱스 (0~3)
        private String explanation;   // 해설
    }
}