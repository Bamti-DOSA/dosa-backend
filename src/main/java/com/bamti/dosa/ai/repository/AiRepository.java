package com.bamti.dosa.ai.repository;

import com.bamti.dosa.aiassistant.entity.AiAssistantChat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AiRepository extends JpaRepository <AiAssistantChat,Long> {

}
