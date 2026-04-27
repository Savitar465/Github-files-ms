package com.githubx.githubfilesms.dao;

import com.githubx.githubfilesms.model.FileEntity;
import com.githubx.githubfilesms.model.RepositoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileDao extends JpaRepository<FileEntity, Long> {

    Optional<FileEntity> findByRepositoryAndPathAndBranch(
            RepositoryEntity repository, String path, String branch);

    List<FileEntity> findByRepositoryAndBranch(RepositoryEntity repository, String branch);

    @Query("SELECT f FROM FileEntity f WHERE f.repository = :repo AND f.branch = :branch " +
            "AND f.path LIKE :parentPath% AND f.path NOT LIKE :parentPathDeep%")
    List<FileEntity> findDirectChildrenByRepositoryAndBranchAndParentPath(
            @Param("repo") RepositoryEntity repository,
            @Param("branch") String branch,
            @Param("parentPath") String parentPath,
            @Param("parentPathDeep") String parentPathDeep);

    @Query("SELECT f FROM FileEntity f WHERE f.repository = :repo AND f.branch = :branch " +
            "AND (f.path = '' OR f.path NOT LIKE '%/%')")
    List<FileEntity> findRootEntriesByRepositoryAndBranch(
            @Param("repo") RepositoryEntity repository,
            @Param("branch") String branch);

    boolean existsByRepositoryAndPathAndBranch(
            RepositoryEntity repository, String path, String branch);

    void deleteByRepositoryAndPathAndBranch(
            RepositoryEntity repository, String path, String branch);
}
