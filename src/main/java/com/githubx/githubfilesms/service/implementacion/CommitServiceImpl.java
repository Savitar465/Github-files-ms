package com.githubx.githubfilesms.service.implementacion;

import com.githubx.githubfilesms.dao.CommitDao;
import com.githubx.githubfilesms.dao.RepositoryDao;
import com.githubx.githubfilesms.dto.response.*;
import com.githubx.githubfilesms.mapper.CommitMapper;
import com.githubx.githubfilesms.model.CommitEntity;
import com.githubx.githubfilesms.model.RepositoryEntity;
import com.githubx.githubfilesms.service.contratos.CommitService;
import com.githubx.githubfilesms.util.errorhandling.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommitServiceImpl implements CommitService {

    private final CommitDao commitDao;
    private final RepositoryDao repositoryDao;
    private final CommitMapper commitMapper;

    @Override
    public ListCommitsResponse listCommits(String owner, String repo, String sha, String path, int page, int perPage) {
        RepositoryEntity repository = getRepository(owner, repo);
        Pageable pageable = PageRequest.of(page, perPage);

        Page<CommitEntity> commitsPage;

        if (path != null && !path.isEmpty()) {
            commitsPage = commitDao.findByRepositoryAndFilePath(repository, path, pageable);
        } else if (sha != null && !sha.isEmpty()) {
            commitsPage = commitDao.findByRepositoryAndBranchOrderByCommitterDateDesc(repository, sha, pageable);
        } else {
            commitsPage = commitDao.findByRepositoryOrderByCommitterDateDesc(repository, pageable);
        }

        List<CommitResponse> commits = commitMapper.toCommitResponseList(commitsPage.getContent());
        PaginationMeta pagination = PaginationMeta.of(page, perPage, commitsPage.getTotalElements());

        return new ListCommitsResponse(commits, pagination);
    }

    @Override
    public GetCommitResponse getCommit(String owner, String repo, String sha) {
        RepositoryEntity repository = getRepository(owner, repo);

        CommitEntity commit = commitDao.findByRepositoryAndSha(repository, sha)
                .orElseThrow(() -> new EntityNotFoundException("Commit", sha));

        CommitResponse commitResponse = commitMapper.toCommitResponse(commit);
        List<CommitFileResponse> files = commitMapper.toCommitFileResponseList(commit.getFiles());

        return new GetCommitResponse(commitResponse, files);
    }

    @Override
    public String getCommitDiff(String owner, String repo, String sha) {
        RepositoryEntity repository = getRepository(owner, repo);

        CommitEntity commit = commitDao.findByRepositoryAndSha(repository, sha)
                .orElseThrow(() -> new EntityNotFoundException("Commit", sha));

        StringBuilder diff = new StringBuilder();
        diff.append("commit ").append(commit.getSha()).append("\n");
        diff.append("Author: ").append(commit.getAuthorName())
                .append(" <").append(commit.getAuthorEmail()).append(">\n");
        diff.append("Date:   ").append(commit.getAuthorDate()).append("\n\n");
        diff.append("    ").append(commit.getMessage()).append("\n\n");

        for (var file : commit.getFiles()) {
            diff.append("diff --git a/").append(file.getFilename())
                    .append(" b/").append(file.getFilename()).append("\n");
            diff.append("--- a/").append(file.getFilename()).append("\n");
            diff.append("+++ b/").append(file.getFilename()).append("\n");
            if (file.getPatch() != null) {
                diff.append(file.getPatch()).append("\n");
            }
        }

        return diff.toString();
    }

    @Override
    public CompareResponse compareCommits(String owner, String repo, String baseBranch, String headBranch) {
        RepositoryEntity repository = getRepository(owner, repo);

        List<CommitEntity> headCommits = commitDao.findByRepositoryAndBranchOrderByDateDesc(repository, headBranch);
        List<CommitEntity> baseCommits = commitDao.findByRepositoryAndBranchOrderByDateDesc(repository, baseBranch);

        List<String> baseShas = baseCommits.stream().map(CommitEntity::getSha).toList();
        List<CommitEntity> uniqueHeadCommits = headCommits.stream()
                .filter(c -> !baseShas.contains(c.getSha()))
                .toList();

        List<CommitResponse> commits = commitMapper.toCommitResponseList(uniqueHeadCommits);

        List<CommitFileResponse> allFiles = new ArrayList<>();
        for (CommitEntity commit : uniqueHeadCommits) {
            allFiles.addAll(commitMapper.toCommitFileResponseList(commit.getFiles()));
        }

        int aheadBy = commitDao.countAheadBy(repository, baseBranch, headBranch);
        int behindBy = commitDao.countBehindBy(repository, baseBranch, headBranch);

        return new CompareResponse(
                commits,
                commits.size(),
                allFiles,
                aheadBy,
                behindBy
        );
    }

    private RepositoryEntity getRepository(String owner, String repo) {
        return repositoryDao.findByOwnerAndName(owner, repo)
                .orElseThrow(() -> new EntityNotFoundException("Repositorio", owner + "/" + repo));
    }
}
