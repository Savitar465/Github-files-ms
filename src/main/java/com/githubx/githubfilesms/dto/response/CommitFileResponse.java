package com.githubx.githubfilesms.dto.response;

public record CommitFileResponse(
        String filename,
        String status,
        Integer additions,
        Integer deletions,
        Integer changes,
        String patch
) {}
