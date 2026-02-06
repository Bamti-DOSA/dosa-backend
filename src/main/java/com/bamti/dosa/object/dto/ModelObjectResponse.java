package com.bamti.dosa.object.dto;

import com.bamti.dosa.object.entity.ModelObject;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ModelObjectResponse {
    private Long objectId;
    private String name;
    private String type;
    private String description;
    private String thumbnailUrl;
    private boolean isBookmarked;
    private LocalDateTime lastAccessedAt;

    // Entity -> DTO 변환 메서드 (편의상 여기에 만듭니다)
    public static ModelObjectResponse from(ModelObject entity) {
        return ModelObjectResponse.builder()
                .objectId(entity.getObjectId())
                .name(entity.getName())
                .type(entity.getType())
                .description(entity.getDescription())
                .thumbnailUrl(entity.getThumbnailUrl())
                .isBookmarked(entity.isBookmarked())
                .lastAccessedAt(entity.getLastAccessedAt())
                .build();
    }
}