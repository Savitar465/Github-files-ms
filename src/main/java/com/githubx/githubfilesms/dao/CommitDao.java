package com.githubx.githubfilesms.dao;

import com.githubx.githubfilesms.model.CommitEntity;
import com.githubx.githubfilesms.model.RepositoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommitDao extends JpaRepository<CommitEntity, Long> {

    Optional<CommitEntity> findByRepositoryAndSha(RepositoryEntity repository, String sha);

    Page<CommitEntity> findByRepositoryAndBranchOrderByCommitterDateDesc(
            RepositoryEntity repository, String branch, Pageable pageable);

    Page<CommitEntity> findByRepositoryOrderByCommitterDateDesc(
            RepositoryEntity repository, Pageable pageable);

    @Query("SELECT c FROM CommitEntity c JOIN c.files f " +
            "WHERE c.repository = :repo AND f.filename LIKE :path% " +
            "ORDER BY c.committerDate DESC")
    Page<CommitEntity> findByRepositoryAndFilePath(
            @Param("repo") RepositoryEntity repository,
            @Param("path") String path,
            Pageable pageable);

    @Query("SELECT c FROM CommitEntity c WHERE c.repository = :repo " +
            "AND c.branch = :branch ORDER BY c.committerDate DESC")
    List<CommitEntity> findByRepositoryAndBranchOrderByDateDesc(
            @Param("repo") RepositoryEntity repository,
            @Param("branch") String branch);

    @Query("SELECT COUNT(c) FROM CommitEntity c WHERE c.repository = :repo " +
            "AND c.branch = :headBranch AND c.sha NOT IN " +
            "(SELECT c2.sha FROM CommitEntity c2 WHERE c2.repository = :repo AND c2.branch = :baseBranch)")
    int countAheadBy(@Param("repo") RepositoryEntity repository,
                     @Param("baseBranch") String baseBranch,
                     @Param("headBranch") String headBranch);

    @Query("SELECT COUNT(c) FROM CommitEntity c WHERE c.repository = :repo " +
            "AND c.branch = :baseBranch AND c.sha NOT IN " +
            "(SELECT c2.sha FROM CommitEntity c2 WHERE c2.repository = :repo AND c2.branch = :headBranch)")
    int countBehindBy(@Param("repo") RepositoryEntity repository,
                      @Param("baseBranch") String baseBranch,
                      @Param("headBranch") String headBranch);
}
