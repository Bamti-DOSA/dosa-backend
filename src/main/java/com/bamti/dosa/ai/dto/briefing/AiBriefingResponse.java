package com.bamti.dosa.ai.dto.briefing;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AiBriefingResponse {
    private String summary;
    private int usedMessageCount;
}
