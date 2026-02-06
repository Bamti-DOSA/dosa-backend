package com.bamti.dosa.ai.controller;

import com.bamti.dosa.ai.dto.AiBriefingRequest;
import com.bamti.dosa.ai.dto.AiBriefingResponse;
import com.bamti.dosa.ai.service.AiBriefingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
// AI 브리핑
public class AiBriefingController {
    private final AiBriefingService aiBriefingService;

    @PostMapping("/ai/briefing")
        public AiBriefingResponse summarize(@RequestBody AiBriefingRequest req) {
                if (req.getMessages() == null || req.getMessages().isEmpty()) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "messages는 필수입니다.");
                   }
        return aiBriefingService.summarize(req);
    }




}
