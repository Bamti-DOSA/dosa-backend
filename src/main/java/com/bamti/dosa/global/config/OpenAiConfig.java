package com.bamti.dosa.global.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;


import java.time.Duration;

@Configuration
public class OpenAiConfig {

    @Value("${openai.api-key}")

    private String openAiKey;

    @Bean("openAiRestTemplate")

    public RestTemplate openAiRestTemplate(RestTemplateBuilder builder) {
        {
            RestTemplate restTemplate = builder
                    .setConnectTimeout(Duration.ofSeconds(10))
                    .setReadTimeout(Duration.ofSeconds(120))
                    .build();
            restTemplate.getInterceptors().add((request, body, execution) ->
            {
                request.getHeaders().add("Authorization", "Bearer " + openAiKey);
                return execution.execute(request, body);
            });
            return restTemplate;
        }
    }
}
