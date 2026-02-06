package com.bamti.dosa.ai.service;

import com.bamti.dosa.ai.dto.ChatGptRequest;
import com.bamti.dosa.ai.dto.ChatGptResponse;
import com.bamti.dosa.ai.repository.AiRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class OpenAiService {
    private final AiRepository aiRepository;
    private final RestTemplate restTemplate;

    //ai 서비스 호출

//    //RestTemplate 사용
//    RestTemplate은 스프링 프레임워크가 제공하는 클래스로, RESTful 서비스를 소비하기 위한 동기식 HTTP 클라이언트이다. RestTemplate을 사용하면 HTTP 요청을 보내고, 응답을 받아, 자바 객체로 변환하는 과정을 간편하게 처리할 수 있다.
//    RestTemplate은 다양한 HTTP 메소드(GET, POST, DELETE 등)를 지원하며, HttpEntity를 통해 요청 본문과 헤더를 설정할 수 있고, ResponseEntity를 통해 응답 본문과 상태 코드 등을 받을 수 있다.

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


}
