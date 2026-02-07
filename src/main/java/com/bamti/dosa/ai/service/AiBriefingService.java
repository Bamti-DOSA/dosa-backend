package com.bamti.dosa.ai.service;

import com.bamti.dosa.ai.client.OpenAiApiCaller;
import com.bamti.dosa.ai.dto.AiBriefingRequest;
import com.bamti.dosa.ai.dto.AiBriefingResponse;
import com.bamti.dosa.ai.dto.Message;
import com.bamti.dosa.ai.repository.AiRepository;
import com.bamti.dosa.aiassistant.entity.AiAssistantChat;
import com.bamti.dosa.aiassistant.entity.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AiBriefingService {

    private final OpenAiApiCaller openAiApiCaller;

    public AiBriefingResponse summarize(AiBriefingRequest req) {

        //요청에서 메시지 리스트 꺼내서 original 에 저장
        List<Message> original = req.getMessages();

        if(original == null || original.isEmpty()) {
            return new AiBriefingResponse("요약할 대화가 없어요.", 0);

        }
        //최대 몇개의 메시지만 요약할지 설정 !
        int limit = req.getMaxMessages() != null && req.getMaxMessages() > 0 ? req.getMaxMessages() : 30;

        //실제 요약에 보낼 메시지 리스트를 만듦
        List<Message> trimmed = original.size() > limit
                ? original.subList(original.size() - limit, original.size())
                : original;

        List<Message> filtered = trimmed.stream()
                .filter(m -> m != null && m.getRole() != null)
                .filter(m -> {
                    String r = m.getRole().trim().toLowerCase();
                    return r.equals("user") || r.equals("assistant");
                })
                .toList();

        if (filtered.isEmpty()) {
            return new AiBriefingResponse("요약할 대화가 없어요.", 0);
        }


        //요약용 프롬프트
        String system = """
너는 학습 내용을 정리해주는 AI 브리핑 비서다.

사용자의 대화 내용을 분석해서, 반드시 아래 JSON 형식으로만 요약해라.
JSON 외의 어떤 텍스트(인사말, 설명, 코드블록 ``` 등)도 절대 출력하지 마라.

[요약 규칙]
- title: 사용자가 학습한 전체 주제를 한 문장으로 요약
- items: 항목별 핵심 정리 목록
  - name: 개념/부품/키워드 이름
  - description: 그 항목의 역할/원리/핵심 내용을 1~2문장으로 짧고 명확하게 설명
- 불필요한 인사말/결론 금지
- 반드시 한국어로 작성
- 문장은 짧게 유지하고, UI에 바로 표시될 수 있게 간결하게 작성

[출력 형식]
{
  "title": "사용자가 학습한 전체 내용을 한 문장으로 요약",
  "items": [
    {
      "name": "키워드",
      "description": "핵심 요약"
    }
  ]
}
""";
        //openAI에 보낼 메시지 새롭게 구성
        List<Message> toSend = new ArrayList<>();
        toSend.add(new Message("system",system));
        toSend.addAll(filtered);

        String summary = openAiApiCaller.callMessages(toSend);

        if (summary == null || summary.isBlank()) {
            return new AiBriefingResponse("요약 생성에 실패했습니다.", 0);
        }

        return new AiBriefingResponse(summary, filtered.size());
    }
}
