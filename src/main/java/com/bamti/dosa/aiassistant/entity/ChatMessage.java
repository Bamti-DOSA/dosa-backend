package com.bamti.dosa.aiassistant.entity;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {
    private Long id;
    private ChatRole role;
    private String message;
    private LocalDateTime createdAt;
}
