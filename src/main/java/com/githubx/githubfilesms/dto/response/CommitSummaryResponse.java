package com.githubx.githubfilesms.dto.response;

public record CommitSummaryResponse(
        String sha,
        String message,
        CommitSignatureResponse author,
        CommitSignatureResponse committer,
        String htmlUrl
) {}
