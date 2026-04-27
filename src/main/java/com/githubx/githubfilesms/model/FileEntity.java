package com.githubx.githubfilesms.model;

import com.githubx.githubfilesms.model.enums.GitObjectType;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "files", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"repository_id", "path", "branch"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repository_id", nullable = false)
    private RepositoryEntity repository;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 1000)
    private String path;

    @Column(nullable = false, length = 40)
    private String sha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private GitObjectType type;

    @Column
    private Long size;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(length = 20)
    private String encoding;

    @Column(length = 100)
    private String branch;

    @Column(name = "last_commit_sha", length = 40)
    private String lastCommitSha;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
        updatedAt = Instant.now();
        if (branch == null) {
            branch = "main";
        }
        if (encoding == null && type == GitObjectType.FILE) {
            encoding = "base64";
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }

    public String getDownloadUrl() {
        return String.format("/v1/repos/%s/%s/download?path=%s&ref=%s",
                repository.getOwner(), repository.getName(), path, branch);
    }

    public String getHtmlUrl() {
        return String.format("/v1/repos/%s/%s/contents/%s?ref=%s",
                repository.getOwner(), repository.getName(), path, branch);
    }
}
