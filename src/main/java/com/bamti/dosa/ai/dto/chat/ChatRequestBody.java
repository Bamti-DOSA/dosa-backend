package com.bamti.dosa.ai.dto.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRequestBody {
    private String system;
    private String prompt;
}
