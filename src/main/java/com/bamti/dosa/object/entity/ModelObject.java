package com.bamti.dosa.object.entity;

import com.bamti.dosa.aiassistant.entity.AiAssistantChat;
import com.bamti.dosa.part.entity.Part;
import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "objects")
public class ModelObject {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "object_id", nullable = false)
        private Long objectId;

        @Column(name = "name", nullable = false, length = 255)
        private String name;

        @Column(name = "type", length = 100)
        private String type;

        @Column(name = "is_bookmarked", nullable = false)
        private boolean bookmarked;

        @Column(name = "last_accessed_at")
        private LocalDateTime lastAccessedAt;

        @Lob
        @Column(name = "ai_briefing", columnDefinition = "LONGTEXT")
        private String aiBriefing;

        @Lob
        @Column(name = "description", columnDefinition = "LONGTEXT")
        private String description;

        //통합(조립본) GLB URL/경로
        @Column(name = "assembly_model_url", nullable = false, length = 1000)
        private String assemblyModelUrl;

        @Column(name = "thumbnail_url", length = 1000)
        private String thumbnailUrl;

        @OneToMany(mappedBy = "object", cascade = CascadeType.ALL, orphanRemoval = true)
        @Builder.Default
        private List<Part> parts = new ArrayList<>();

        @OneToMany(mappedBy = "object", cascade = CascadeType.ALL, orphanRemoval = true)
        @Builder.Default
        private List<AiAssistantChat> aiAssistants = new ArrayList<>();

}
