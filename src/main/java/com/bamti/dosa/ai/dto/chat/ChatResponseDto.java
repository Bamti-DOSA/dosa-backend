package com.bamti.dosa.ai.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatResponseDto {
    private boolean success;
    private String content;
    private String error;

    // 성공 시 사용하는 정적 팩토리 메서드
    public static ChatResponseDto success(String content) {
        return new ChatResponseDto(true, content, null);
    }

    // 실패 시 사용하는 정적 팩토리 메서드
    public static ChatResponseDto fail(String errorMessage) {
        return new ChatResponseDto(false, null, errorMessage);
    }
}