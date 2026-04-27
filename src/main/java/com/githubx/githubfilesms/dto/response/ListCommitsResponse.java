package com.githubx.githubfilesms.dto.response;

import java.util.List;

public record ListCommitsResponse(
        List<CommitResponse> commits,
        PaginationMeta pagination
) {}
