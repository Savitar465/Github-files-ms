package com.githubx.githubfilesms.dto.response;

public record CommitSignatureResponse(
        String name,
        String email,
        String date
) {}
