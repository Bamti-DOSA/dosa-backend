package com.bamti.dosa.aiassistant.entity;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {
    private ChatRole role;
    private String message;
    private LocalDateTime createdAt;
}
