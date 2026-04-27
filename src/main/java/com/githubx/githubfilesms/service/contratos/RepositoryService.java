package com.githubx.githubfilesms.service.contratos;

import com.githubx.githubfilesms.dto.request.CreateRepositoryRequest;
import com.githubx.githubfilesms.dto.response.RepositoryResponse;

import java.util.List;

public interface RepositoryService {

    RepositoryResponse createRepository(String owner, CreateRepositoryRequest request);

    RepositoryResponse getRepository(String owner, String repo);

    List<RepositoryResponse> listRepositories(String owner);

    void deleteRepository(String owner, String repo);
}
