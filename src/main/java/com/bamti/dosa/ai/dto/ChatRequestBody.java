package com.bamti.dosa.ai.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRequestBody {
    private String system;
    private String prompt;
}
