package com.githubx.githubfilesms.dto.response;

import com.githubx.githubfilesms.model.enums.GitObjectType;

public record FileContentResponse(
        String name,
        String path,
        String sha,
        GitObjectType type,
        Long size,
        String encoding,
        String content,
        String downloadUrl,
        String htmlUrl,
        String lastCommitSha
) {}
