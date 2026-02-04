package com.bamti.dosa.aiassistant.entity;

import com.bamti.dosa.object.entity.ModelObject;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ai_assistants")
public class AiAssistantChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ai_assistant_id", nullable = false)
    private Long aiAssistantId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "object_id", nullable = false, foreignKey = @ForeignKey(name = "fk_ai_assistants_object"))
    private ModelObject object;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    //대화내역(List)을 MySQL JSON 컬럼에 저장
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "chat_history", columnDefinition = "json")
    @Builder.Default
    private List<ChatMessage> chatHistory = new ArrayList<>();
}
