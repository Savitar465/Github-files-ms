package com.githubx.githubfilesms.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "commit_files")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommitFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commit_id", nullable = false)
    private CommitEntity commit;

    @Column(nullable = false, length = 500)
    private String filename;

    @Column(nullable = false, length = 20)
    private String status;

    @Column
    private Integer additions;

    @Column
    private Integer deletions;

    @Column
    private Integer changes;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String patch;
}
