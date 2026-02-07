package com.bamti.dosa.part.entity;

import com.bamti.dosa.material.entity.Material;
import com.bamti.dosa.memo.entity.Memo;
import com.bamti.dosa.object.entity.ModelObject;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "parts")
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "part_id", nullable = false)
    private Long partId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "object_id", nullable = false, foreignKey = @ForeignKey(name = "fk_parts_object"))
    private ModelObject object;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Lob
    @Column(name = "description", columnDefinition = "LONGTEXT")
    private String description;

    //GLB 내부 mesh 식별자
    @Column(name = "mesh_name", nullable = false, length = 255)
    private String meshName;

    @Column(name = "part_image_url", length = 1000)
    private String partImageUrl;

    @OneToMany(mappedBy = "part", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Material> materials = new ArrayList<>();

    @OneToMany(mappedBy = "part", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Memo> memos = new ArrayList<>();
}
