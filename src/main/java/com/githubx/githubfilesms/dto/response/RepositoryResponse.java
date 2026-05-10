package com.githubx.githubfilesms.dto.response;

import com.githubx.githubfilesms.model.enums.RepoVisibility;

import java.time.Instant;

public record RepositoryResponse(
        Long id,
        String owner,
        String name,
        String fullName,
        String description,
        RepoVisibility visibility,
        String defaultBranch,
        String htmlUrl,
        Instant createdAt,
        Instant updatedAt
) {}
