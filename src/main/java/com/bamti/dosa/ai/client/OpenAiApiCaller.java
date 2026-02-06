package com.bamti.dosa.ai.client;

import com.bamti.dosa.ai.dto.ChatGptRequest;
import com.bamti.dosa.ai.dto.ChatGptResponse;
import com.bamti.dosa.ai.dto.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.*;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


@Component
@RequiredArgsConstructor
//호출 컴포넌트
public class OpenAiApiCaller {

    private final RestTemplate restTemplate;

    @Value("${openai.model}")
    private String model;


    @Value("${openai.api.url}")
    private String apiURL;

    public ChatGptResponse call(ChatGptRequest request) {

        try {
            ChatGptResponse response =
                    restTemplate.postForObject(apiURL, request, ChatGptResponse.class);

            if (response == null || response.getChoices() == null || response.getChoices().isEmpty()
                    || response.getChoices().get(0).getMessage() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "OpenAI 응답이 비어 있습니다.");
            }

            return response;

        } catch (HttpClientErrorException e) { // 4xx
            if (e.getStatusCode().value() == 429) {
                throw new ResponseStatusException(
                        HttpStatus.SERVICE_UNAVAILABLE,
                        "OpenAI 요청 한도 초과: " + safeBody(e)
                );
            }
            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "OpenAI 요청 오류(" + e.getStatusCode() + "): " + safeBody(e)
            );

        } catch (HttpServerErrorException e) { // 5xx
            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "OpenAI 서버 오류(" + e.getStatusCode() + "): " + safeBody(e)
            );

        } catch (ResourceAccessException e) { // timeout/connection
            throw new ResponseStatusException(
                    HttpStatus.GATEWAY_TIMEOUT,
                    "OpenAI 연결 실패 또는 타임아웃"
            );

        } catch (RestClientException e) { // 기타
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "OpenAI 호출 중 오류 발생"
            );
        }
    }

    private String safeBody(RestClientResponseException e) {
        String body = e.getResponseBodyAsString();
        return (body == null || body.isBlank()) ? "(no body)" : body;
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
