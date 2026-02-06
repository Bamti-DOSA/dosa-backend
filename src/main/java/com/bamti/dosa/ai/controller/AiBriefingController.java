package com.bamti.dosa.ai.controller;

import com.bamti.dosa.ai.dto.AiBriefingRequest;
import com.bamti.dosa.ai.dto.AiBriefingResponse;
import com.bamti.dosa.ai.service.AiBriefingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
// AI 브리핑
public class AiBriefingController {
    private final AiBriefingService aiBriefingService;

    @PostMapping("/ai/briefing")
    public AiBriefingResponse summarize(@RequestBody AiBriefingRequest req) {
        return aiBriefingService.summarize(req);
    }




}
