package com.bamti.dosa.memo.entity;

import com.bamti.dosa.part.entity.Part;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "memos")
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memo_id", nullable = false)
    private Long memoId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "part_id", nullable = false, foreignKey = @ForeignKey(name = "fk_memos_part"))
    private Part part;

    @Lob
    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "importance", nullable = false, length = 20)
    private MemoImportance importance;
}
