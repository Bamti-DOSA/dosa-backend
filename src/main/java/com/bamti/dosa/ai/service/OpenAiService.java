package com.bamti.dosa.ai.service;

import com.bamti.dosa.ai.client.OpenAiApiCaller;
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
    private final OpenAiApiCaller apiCaller;

    //ai 서비스 호출

//    //RestTemplate 사용
//    RestTemplate은 스프링 프레임워크가 제공하는 클래스로, RESTful 서비스를 소비하기 위한 동기식 HTTP 클라이언트이다. RestTemplate을 사용하면 HTTP 요청을 보내고, 응답을 받아, 자바 객체로 변환하는 과정을 간편하게 처리할 수 있다.
//    RestTemplate은 다양한 HTTP 메소드(GET, POST, DELETE 등)를 지원하며, HttpEntity를 통해 요청 본문과 헤더를 설정할 수 있고, ResponseEntity를 통해 응답 본문과 상태 코드 등을 받을 수 있다.



    public ChatGptResponse call(ChatGptRequest request) {


        return apiCaller.call(request);
    }

}
