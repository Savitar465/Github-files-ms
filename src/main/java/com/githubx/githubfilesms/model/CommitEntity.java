package com.githubx.githubfilesms.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "commits", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"repository_id", "sha"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repository_id", nullable = false)
    private RepositoryEntity repository;

    @Column(nullable = false, length = 40)
    private String sha;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(name = "author_name", nullable = false, length = 100)
    private String authorName;

    @Column(name = "author_email", nullable = false, length = 255)
    private String authorEmail;

    @Column(name = "author_date", nullable = false)
    private Instant authorDate;

    @Column(name = "committer_name", nullable = false, length = 100)
    private String committerName;

    @Column(name = "committer_email", nullable = false, length = 255)
    private String committerEmail;

    @Column(name = "committer_date", nullable = false)
    private Instant committerDate;

    @Column(name = "parent_sha", length = 40)
    private String parentSha;

    @Column(length = 100)
    private String branch;

    @OneToMany(mappedBy = "commit", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CommitFileEntity> files = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
        if (authorDate == null) {
            authorDate = Instant.now();
        }
        if (committerDate == null) {
            committerDate = Instant.now();
        }
    }

    public String getHtmlUrl() {
        return String.format("/v1/repos/%s/%s/commits/%s",
                repository.getOwner(), repository.getName(), sha);
    }
}
