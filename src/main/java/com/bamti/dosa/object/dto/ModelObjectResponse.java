package com.bamti.dosa.object.dto;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class ModelObjectResponse {
    private Long objectId;
    private String name;
    private String type;
    private String description;
    private String thumbnailUrl;
    private String assemblyModelUrl;

    private List<PartDto> parts;

    @Getter
    @Builder
    public static class PartDto {
        private String name;      // 화면에 보일 한글 이름 (예: "메인 샤프트")
        private String meshName;  // ★ 3D 모델 내부의 영문 ID (예: "shaft_main")
        private String description; // 부품 설명
        private String partImageUrl;
        private String partUrl;
    }
}
