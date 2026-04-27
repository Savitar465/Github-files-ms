package com.githubx.githubfilesms.service.contratos;

import com.githubx.githubfilesms.dto.response.CompareResponse;
import com.githubx.githubfilesms.dto.response.GetCommitResponse;
import com.githubx.githubfilesms.dto.response.ListCommitsResponse;

public interface CommitService {

    ListCommitsResponse listCommits(String owner, String repo, String sha, String path, int page, int perPage);

    GetCommitResponse getCommit(String owner, String repo, String sha);

    String getCommitDiff(String owner, String repo, String sha);

    CompareResponse compareCommits(String owner, String repo, String baseBranch, String headBranch);
}
