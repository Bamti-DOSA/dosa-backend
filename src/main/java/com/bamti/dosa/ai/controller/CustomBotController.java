package com.bamti.dosa.ai.controller;


import com.bamti.dosa.ai.domain.enums.ProductSystemPrompt;
import com.bamti.dosa.ai.dto.ChatGptRequest;
import com.bamti.dosa.ai.dto.ChatGptResponse;
import com.bamti.dosa.ai.dto.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class CustomBotController {
    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiURL;

    @Autowired
    private RestTemplate template;



    @GetMapping("/chat")
    public String chat(  @RequestParam(name = "system") String system,
                         @RequestParam(name = "prompt")String prompt)
    {
        // 시스템 프롬프트 선택
        ProductSystemPrompt prodPrompt = ProductSystemPrompt.valueOf(system.toUpperCase());

        //request 생성 !
        ChatGptRequest request = new ChatGptRequest(model);
        //시스템 프롬프트
        request.addSystemMessage(prodPrompt.getPrompt());
        //유저 프롬프트
        request.addUserMessage(prompt);

        ChatGptResponse chatGptResponse = template.postForObject(apiURL, request, ChatGptResponse.class);
        return chatGptResponse.getChoices().get(0).getMessage().getContent();
    }
}
