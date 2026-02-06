package com.bamti.dosa.ai.client;

import com.bamti.dosa.ai.dto.ChatGptRequest;
import com.bamti.dosa.ai.dto.ChatGptResponse;
import com.bamti.dosa.ai.dto.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Component
@RequiredArgsConstructor
//호출 컴포넌트
public class OpenAiApiCaller {

    private final RestTemplate restTemplate;

    @Value("${openai.model}")
    private String model;


    @Value("${openai.api.url}")
    private String apiUrl;

    public ChatGptResponse call(ChatGptRequest request) {

        return restTemplate.postForObject(
                apiUrl,
                request,
                ChatGptResponse.class
        );
    }
    public String callMessages(List<Message> messages) {
        ChatGptRequest request = new ChatGptRequest(model);
        request.setMessages(messages);

        ChatGptResponse response = call(request);
        return response.getChoices()
                .get(0)
                .getMessage()
                .getContent();
    }
}
