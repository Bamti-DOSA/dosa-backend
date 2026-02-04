package com.bamti.dosa.material.entity;

import com.bamti.dosa.part.entity.Part;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "materials")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "material_id", nullable = false)
    private Long materialId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "part_id", nullable = false, foreignKey = @ForeignKey(name = "fk_materials_part"))
    private Part part;

    @Column(name = "material_name", nullable = false, length = 255)
    private String materialName;

    @Lob
    @Column(name = "material_description", columnDefinition = "LONGTEXT")
    private String materialDescription;

    @Column(name = "image_url", length = 1000)
    private String imageUrl;
}
