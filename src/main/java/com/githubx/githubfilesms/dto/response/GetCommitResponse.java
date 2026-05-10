package com.githubx.githubfilesms.dto.response;

import java.util.List;

public record GetCommitResponse(
        CommitResponse commit,
        List<CommitFileResponse> files
) {}
