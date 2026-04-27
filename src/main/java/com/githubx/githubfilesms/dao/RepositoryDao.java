package com.githubx.githubfilesms.dao;

import com.githubx.githubfilesms.model.RepositoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositoryDao extends JpaRepository<RepositoryEntity, Long> {

    Optional<RepositoryEntity> findByOwnerAndName(String owner, String name);

    boolean existsByOwnerAndName(String owner, String name);
}
