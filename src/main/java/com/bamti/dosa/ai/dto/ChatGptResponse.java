package com.bamti.dosa.ai.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatGptResponse {
    private List<Choice> choices;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Choice {
        private int index;
        private Message message;
    }
    public String getFirstMessageContent() {
        if (choices == null || choices.isEmpty()) {
            return null;
        }

        Choice firstChoice = choices.get(0);
        if (firstChoice == null || firstChoice.getMessage() == null) {
            return null;
        }

        return firstChoice.getMessage().getContent();
    }


}
