package com.bamti.dosa.ai.client;

import com.bamti.dosa.ai.dto.ChatGptRequest;
import com.bamti.dosa.ai.dto.ChatGptResponse;
import com.bamti.dosa.ai.dto.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;


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

        ChatGptResponse response = restTemplate.postForObject(
                apiUrl,
                request,
                ChatGptResponse.class
        );
        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()
                                || response.getChoices().get(0).getMessage() == null) {
                      throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "OpenAI 응답이 비어 있습니다.");
                   }
              return response;
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
