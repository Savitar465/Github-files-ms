package com.githubx.githubfilesms.dto.response;

import java.util.List;

public record CompareResponse(
        List<CommitResponse> commits,
        int totalCommits,
        List<CommitFileResponse> files,
        int aheadBy,
        int behindBy
) {}
