package com.bamti.dosa.ai.controller;


import com.bamti.dosa.ai.domain.enums.ProductSystemPrompt;
import com.bamti.dosa.ai.dto.ChatGptRequest;
import com.bamti.dosa.ai.dto.ChatGptResponse;
import com.bamti.dosa.ai.dto.ChatRequestBody;
import com.bamti.dosa.ai.dto.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class CustomBotController {
    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiURL;

    @Autowired
    private RestTemplate template;



    @PostMapping("/chat")
    public String chat(@RequestBody ChatRequestBody body)
    {
        if (body.getPrompt() == null || body.getPrompt().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "prompt는 필수입니다.");
        }
        // 시스템 프롬프트 선택
        ProductSystemPrompt prodPrompt;

              try {
                   prodPrompt = ProductSystemPrompt.valueOf(body.getSystem().toUpperCase());
               } catch (IllegalArgumentException ex) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid system prompt");
              }
        //request 생성 !
        ChatGptRequest request = new ChatGptRequest(model);
        //시스템 프롬프트
        request.addSystemMessage(prodPrompt.getPrompt());
        //유저 프롬프트
        request.addUserMessage(body.getPrompt());

        ChatGptResponse chatGptResponse = template.postForObject(apiURL, request, ChatGptResponse.class);
                if (chatGptResponse == null
                           || chatGptResponse.getChoices() == null
                           || chatGptResponse.getChoices().isEmpty()
                           || chatGptResponse.getChoices().get(0).getMessage() == null) {
                   throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "OpenAI 응답이 비어 있습니다.");
               }
              return chatGptResponse.getChoices().get(0).getMessage().getContent();
    }
}
