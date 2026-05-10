package com.githubx.githubfilesms.service.implementacion;

import com.githubx.githubfilesms.dao.RepositoryDao;
import com.githubx.githubfilesms.dto.request.CreateRepositoryRequest;
import com.githubx.githubfilesms.dto.response.RepositoryResponse;
import com.githubx.githubfilesms.mapper.RepositoryMapper;
import com.githubx.githubfilesms.model.RepositoryEntity;
import com.githubx.githubfilesms.model.enums.RepoVisibility;
import com.githubx.githubfilesms.service.contratos.RepositoryService;
import com.githubx.githubfilesms.util.errorhandling.EntityConflictException;
import com.githubx.githubfilesms.util.errorhandling.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RepositoryServiceImpl implements RepositoryService {

    private final RepositoryDao repositoryDao;
    private final RepositoryMapper repositoryMapper;

    @Override
    @Transactional
    public RepositoryResponse createRepository(String owner, CreateRepositoryRequest request) {
        if (repositoryDao.existsByOwnerAndName(owner, request.name())) {
            throw new EntityConflictException("El repositorio ya existe: " + owner + "/" + request.name());
        }

        RepositoryEntity entity = RepositoryEntity.builder()
                .owner(owner)
                .name(request.name())
                .description(request.description())
                .visibility(request.visibility() != null ? request.visibility() : RepoVisibility.PRIVATE)
                .defaultBranch(request.defaultBranch() != null ? request.defaultBranch() : "main")
                .build();

        entity = repositoryDao.save(entity);
        return repositoryMapper.toResponse(entity);
    }

    @Override
    public RepositoryResponse getRepository(String owner, String repo) {
        RepositoryEntity entity = repositoryDao.findByOwnerAndName(owner, repo)
                .orElseThrow(() -> new EntityNotFoundException("Repositorio", owner + "/" + repo));
        return repositoryMapper.toResponse(entity);
    }

    @Override
    public List<RepositoryResponse> listRepositories(String owner) {
        List<RepositoryEntity> entities = repositoryDao.findAll().stream()
                .filter(r -> r.getOwner().equals(owner))
                .toList();
        return repositoryMapper.toResponseList(entities);
    }

    @Override
    @Transactional
    public void deleteRepository(String owner, String repo) {
        RepositoryEntity entity = repositoryDao.findByOwnerAndName(owner, repo)
                .orElseThrow(() -> new EntityNotFoundException("Repositorio", owner + "/" + repo));
        repositoryDao.delete(entity);
    }
}
