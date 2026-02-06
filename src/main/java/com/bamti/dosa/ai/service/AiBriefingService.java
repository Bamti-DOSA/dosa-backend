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
        int limit = req.getMaxMessages() != null ? req.getMaxMessages() : 30;

        //실제 요약에 보낼 메시지 리스트를 만듦
        List<Message> trimmed = original.size() > limit
                ? original.subList(original.size() - limit, original.size())
                : original;

        //요약용 프롬프트
        String system = """
너는 학습 내용을 정리해주는 AI 브리핑 비서다.

사용자의 학습 대화를 분석하여 아래 형식으로 요약한다.

[출력 규칙]
1. 첫 줄에는 사용자가 학습한 전체 주제를 한 문장으로 요약한다.
   - 예: "사용자는 Impellar Blade, Leg, Arm gear, 그리고 전체 용도에 대해 학습했습니다."

2. 그 아래에는 항목별로 bullet point(- 또는 •) 형식으로 정리한다.

3. 각 bullet은 아래 구조를 따른다:
   - [부품명 (한글 설명)]: 해당 부품의 역할, 원리, 학습한 핵심 개념을 1~2문장으로 설명

4. 불필요한 인사말, 설명, 결론은 쓰지 않는다.
5. 반드시 한국어로 작성한다.
6. UI에 바로 표시될 수 있도록 간결하고 명확하게 작성한다.

[목표 스타일]
- 학습 노트처럼 정리
- 기술 설명은 쉽고 명확하게
- 문장은 짧게 유지

[출력 형식 규칙]

반드시 아래 JSON 형식으로만 응답한다.
JSON 외의 설명, 인사말, 코드블록(```)은 절대 포함하지 않는다.

{
  "title": "사용자가 학습한 전체 내용을 한 문장으로 요약",
  "items": [
    {
      "name": "부품명",
      "description": "학습한 핵심 내용 요약"
    }
  ]
}

""";
        //openAI에 보낼 메시지 새롭게 구성
        List<Message> toSend = new ArrayList<>();
        toSend.add(new Message("system",system));
        toSend.addAll(trimmed);

        String summary = openAiApiCaller.callMessages(toSend);

        return new AiBriefingResponse(summary, trimmed.size());
    }
}
