package com.bamti.dosa.ai.service.quiz;

import com.bamti.dosa.ai.client.OpenAiApiCaller;
import com.bamti.dosa.ai.dto.quiz.AiQuizRequest;
import com.bamti.dosa.ai.dto.quiz.AiQuizResponse;
import com.bamti.dosa.ai.dto.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiQuizService {

    private final OpenAiApiCaller openAiApiCaller;
    private final ObjectMapper objectMapper; // JSON 파싱용

    public AiQuizResponse generateQuiz(AiQuizRequest req) {
        String topic = req.getModelName();
        if (topic == null || topic.isBlank()) {
            topic = "기계공학 기초";
        }

        // 🧠 퀴즈 생성용 강력한 프롬프트
        String systemPrompt = """
            너는 기계공학 전공 교수 AI다.
            사용자가 요청한 주제에 대해 4지선다형 퀴즈 3문제를 출제하라.

            [출제 규칙]
            1. 난이도: 기계공학 학부 2~3학년 수준 (공학적 원리, 역학, 재료 특성 위주)
            2. 언어: 한국어
            3. 형식: 오직 아래의 JSON 포맷으로만 출력할 것. (Markdown, 인사말 절대 금지)

            [JSON 출력 예시]
            {
              "topic": "주제명",
              "quizzes": [
                {
                  "id": 1,
                  "question": "문제 내용...",
                  "options": ["보기1", "보기2", "보기3", "보기4"],
                  "answer": 0,
                  "explanation": "정답에 대한 상세 해설..."
                }
              ]
            }
            """;

        String userPrompt = "주제: " + topic + "에 대한 퀴즈를 만들어줘.";

        List<Message> messages = new ArrayList<>();
        messages.add(new Message("system", systemPrompt));
        messages.add(new Message("user", userPrompt));

        try {
            // OpenAI 호출
            String jsonResponse = openAiApiCaller.callMessages(messages);

            // 혹시라도 AI가 ```json ... ``` 형태로 줄 경우를 대비해 태그 제거
            jsonResponse = jsonResponse.replace("```json", "").replace("```", "").trim();

            // String -> Object 변환 (JSON 파싱)
            return objectMapper.readValue(jsonResponse, AiQuizResponse.class);

        } catch (Exception e) {
            log.error("퀴즈 생성 실패", e);
            // 실패 시 빈 응답 대신 에러 메시지를 담은 객체 반환 (혹은 예외 던지기)
            throw new RuntimeException("퀴즈 생성 중 오류가 발생했습니다.");
        }
    }
}