package com.bamti.dosa.ai.controller;


import com.bamti.dosa.ai.domain.enums.ProductSystemPrompt;
import com.bamti.dosa.ai.dto.ChatGptRequest;
import com.bamti.dosa.ai.dto.ChatGptResponse;
import com.bamti.dosa.ai.dto.ChatRequestBody;
import com.bamti.dosa.ai.dto.ChatResponseDto;
import com.bamti.dosa.ai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OpenAiController {
    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiURL;

    private final OpenAiService openAiService;


    @PostMapping("/chat")
    public ChatResponseDto chat(@RequestBody ChatRequestBody body)
    {
        if (body.getPrompt() == null || body.getPrompt().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "prompt는 필수입니다.");
        }
        // 시스템 프롬프트 선택
        //NPE 오류 수정 !
        String system = body.getSystem();
              if (system == null || system.isBlank()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "system은 필수입니다.");
              }
                       ProductSystemPrompt prodPrompt;
             try {
                prodPrompt = ProductSystemPrompt.valueOf(system.trim().toUpperCase());
            } catch (IllegalArgumentException ex) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid system prompt");
                }
        //request 생성 !
        ChatGptRequest request = new ChatGptRequest(model);
        //시스템 프롬프트
        request.addSystemMessage(prodPrompt.getPrompt());
        //유저 프롬프트
        request.addUserMessage(body.getPrompt());

        //ai 호출
        ChatGptResponse response = openAiService.call(request);

        //NULL 안정성 체크
        if (response == null) {
                        return ChatResponseDto.fail("OpenAI로부터 응답을 받지 못했습니다.");
                   }

        String content = response.getFirstMessageContent();


        if (content == null || content.isBlank()) {
            return ChatResponseDto.fail("응답 내용이 비어있습니다.");
        }

        return ChatResponseDto.success(content);

    }
}
