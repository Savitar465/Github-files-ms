package com.githubx.githubfilesms.dto.response;

import java.util.List;

public record CommitResponse(
        String sha,
        String message,
        CommitSignatureResponse author,
        CommitSignatureResponse committer,
        String htmlUrl,
        List<CommitParentResponse> parents
) {}
