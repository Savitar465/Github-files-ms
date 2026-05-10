package com.githubx.githubfilesms.dto.response;

import com.githubx.githubfilesms.model.enums.GitObjectType;

public record DirectoryEntryResponse(
        String name,
        String path,
        String sha,
        GitObjectType type,
        Long size,
        String downloadUrl
) {}
