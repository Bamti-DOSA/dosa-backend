package com.bamti.dosa.ai.dto;

import lombok.Data;

import java.util.List;

@Data
public class AiBriefingRequest {
    //private Long aiAssistantId;
    private List<Message> messages;
    private Integer maxMessages;

}
