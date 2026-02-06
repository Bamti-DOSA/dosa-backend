//package com.bamti.dosa.ai.controller;
//
//import com.bamti.dosa.ai.client.OpenAiApiCaller;
//import com.bamti.dosa.ai.dto.Message;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/briefings")
//public class AiBriefTestController {
//
//    private final OpenAiApiCaller openAiApiCaller;
//
//    // GET /api/briefings/mock-summary
//    @GetMapping("/test")
//    public String mockSummary() {
//
//        String systemPrompt = """
//
//        너는 학습 내용을 정리해주는 AI 브리핑 비서다.
//
//        사용자의 학습 대화를 분석하여 아래 형식으로 요약한다.
//
//        [출력 규칙]
//        1. 첫 줄에는 사용자가 학습한 전체 주제를 한 문장으로 요약한다.
//           - 예: "사용자는 Impellar Blade, Leg, Arm gear, 그리고 전체 용도에 대해 학습했습니다."
//
//        2. 그 아래에는 항목별로 bullet point(- 또는 •) 형식으로 정리한다.
//
//        3. 각 bullet은 아래 구조를 따른다:
//           - [부품명 (한글 설명)]: 해당 부품의 역할, 원리, 학습한 핵심 개념을 1~2문장으로 설명
//
//        4. 불필요한 인사말, 설명, 결론은 쓰지 않는다.
//        5. 반드시 한국어로 작성한다.
//        6. UI에 바로 표시될 수 있도록 간결하고 명확하게 작성한다.
//
//        [목표 스타일]
//        - 학습 노트처럼 정리
//        - 기술 설명은 쉽고 명확하게
//        - 문장은 짧게 유지
//
//        [출력 형식 규칙]
//
//        반드시 아래 JSON 형식으로만 응답한다.
//        JSON 외의 설명, 인사말, 코드블록(```)은 절대 포함하지 않는다.
//
//        {
//          "title": "사용자가 학습한 전체 내용을 한 문장으로 요약",
//          "items": [
//            {
//              "name": "부품명",
//              "description": "학습한 핵심 내용 요약"
//            }
//          ]
//        }
//
//        """;
//
//        List<Message> toSend = new ArrayList<>();
//        toSend.add(new Message("system", systemPrompt));
//
//        // 목데이터
//        toSend.add(new Message("user", "드론 구조를 설명해줘."));
//        toSend.add(new Message("assistant", "드론은 프레임, 모터, ESC, 배터리, 비행 컨트롤러로 구성돼."));
//        toSend.add(new Message("user", "프로펠러가 왜 4개야?"));
//        toSend.add(new Message("assistant", "안정적인 자세 제어를 위해 서로 반대 방향으로 회전하는 4개의 프로펠러를 사용해."));
//        toSend.add(new Message("user", "그럼 배터리는 어느 정도 가?"));
//
//        return openAiApiCaller.callMessages(toSend);
//    }
//}
