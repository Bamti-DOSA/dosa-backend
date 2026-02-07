package com.bamti.dosa.part.dto;

import com.bamti.dosa.part.entity.Part;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public class PartDto {

    @Getter
    @AllArgsConstructor
    public static class PartListItem {
        private Long partId;
        private String name;
        private String meshName;
        private String partImageUrl;

        public static PartListItem from(Part part) {
            return new PartListItem(part.getPartId(), part.getName(), part.getMeshName(), part.getPartImageUrl());
        }
    }

    @Getter
    @AllArgsConstructor
    public static class PartDetail {
        private Long partId;
        private Long objectId;
        private String name;
        private String meshName;
        private String description;
        private String partImageUrl;

        public static PartDetail from(Part part) {
            return new PartDetail(
                    part.getPartId(),
                    part.getObject().getObjectId(),
                    part.getName(),
                    part.getMeshName(),
                    part.getDescription(),
                    part.getPartImageUrl()
            );
        }
    }

    @Getter
    @AllArgsConstructor
    public static class PartListResponse {
        private Long objectId;
        private List<PartListItem> parts;
    }
}
