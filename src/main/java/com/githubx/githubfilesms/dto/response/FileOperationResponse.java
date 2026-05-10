package com.githubx.githubfilesms.dto.response;

public record FileOperationResponse(
        FileContentResponse content,
        CommitSummaryResponse commit
) {}
