package com.githubx.githubfilesms.mapper;

import com.githubx.githubfilesms.dto.request.CreateRepositoryRequest;
import com.githubx.githubfilesms.dto.response.RepositoryResponse;
import com.githubx.githubfilesms.model.RepositoryEntity;
import com.githubx.githubfilesms.model.enums.RepoVisibility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryMapperTest {

    private RepositoryMapper mapper;
    private Instant now;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(RepositoryMapper.class);
        now = Instant.now();
    }

    @Test
    void debeMapearRepositoryEntityAResponse() {
        RepositoryEntity entity = RepositoryEntity.builder()
                .id(1L)
                .owner("testowner")
                .name("testrepo")
                .description("A test repository")
                .visibility(RepoVisibility.PUBLIC)
                .defaultBranch("main")
                .createdAt(now)
                .updatedAt(now)
                .build();

        RepositoryResponse result = mapper.toResponse(entity);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("testowner", result.owner());
        assertEquals("testrepo", result.name());
        assertEquals("A test repository", result.description());
        assertEquals(RepoVisibility.PUBLIC, result.visibility());
        assertEquals("main", result.defaultBranch());
        assertEquals("testowner/testrepo", result.fullName());
        assertEquals("/v1/repos/testowner/testrepo", result.htmlUrl());
    }

    @Test
    void debeMapearRepositoryEntityPrivado() {
        RepositoryEntity entity = RepositoryEntity.builder()
                .id(2L)
                .owner("privateowner")
                .name("privaterepo")
                .description("Private repository")
                .visibility(RepoVisibility.PRIVATE)
                .defaultBranch("develop")
                .createdAt(now)
                .updatedAt(now)
                .build();

        RepositoryResponse result = mapper.toResponse(entity);

        assertNotNull(result);
        assertEquals(RepoVisibility.PRIVATE, result.visibility());
        assertEquals("develop", result.defaultBranch());
        assertEquals("privateowner/privaterepo", result.fullName());
    }

    @Test
    void debeMapearRepositoryEntitySinDescripcion() {
        RepositoryEntity entity = RepositoryEntity.builder()
                .id(3L)
                .owner("owner")
                .name("nodesc")
                .description(null)
                .visibility(RepoVisibility.PUBLIC)
                .defaultBranch("main")
                .createdAt(now)
                .updatedAt(now)
                .build();

        RepositoryResponse result = mapper.toResponse(entity);

        assertNotNull(result);
        assertNull(result.description());
    }

    @Test
    void debeMapearListaDeRepositoryEntities() {
        RepositoryEntity repo1 = RepositoryEntity.builder()
                .id(1L)
                .owner("owner1")
                .name("repo1")
                .visibility(RepoVisibility.PUBLIC)
                .defaultBranch("main")
                .createdAt(now)
                .updatedAt(now)
                .build();

        RepositoryEntity repo2 = RepositoryEntity.builder()
                .id(2L)
                .owner("owner2")
                .name("repo2")
                .visibility(RepoVisibility.PRIVATE)
                .defaultBranch("master")
                .createdAt(now)
                .updatedAt(now)
                .build();

        List<RepositoryResponse> result = mapper.toResponseList(List.of(repo1, repo2));

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("owner1/repo1", result.get(0).fullName());
        assertEquals("owner2/repo2", result.get(1).fullName());
    }

    @Test
    void debeMapearListaVacia() {
        List<RepositoryResponse> result = mapper.toResponseList(Collections.emptyList());
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void debeMapearCreateRepositoryRequestAEntity() {
        CreateRepositoryRequest request = new CreateRepositoryRequest(
                "newrepo", "New repository description", RepoVisibility.PUBLIC, "main");

        RepositoryEntity result = mapper.toEntity(request);

        assertNotNull(result);
        assertNull(result.getId());
        assertNull(result.getOwner());
        assertEquals("newrepo", result.getName());
        assertEquals("New repository description", result.getDescription());
        assertEquals(RepoVisibility.PUBLIC, result.getVisibility());
        assertEquals("main", result.getDefaultBranch());
        assertNull(result.getCreatedAt());
        assertNull(result.getUpdatedAt());
    }

    @Test
    void debeMapearCreateRepositoryRequestPrivado() {
        CreateRepositoryRequest request = new CreateRepositoryRequest(
                "privaterepo", "Private repo", RepoVisibility.PRIVATE, "develop");

        RepositoryEntity result = mapper.toEntity(request);

        assertNotNull(result);
        assertEquals("privaterepo", result.getName());
        assertEquals(RepoVisibility.PRIVATE, result.getVisibility());
        assertEquals("develop", result.getDefaultBranch());
    }

    @Test
    void debeGenerarFullNameCorrectamente() {
        RepositoryEntity entity = RepositoryEntity.builder()
                .id(1L)
                .owner("my-org")
                .name("my-project")
                .visibility(RepoVisibility.PUBLIC)
                .defaultBranch("main")
                .createdAt(now)
                .updatedAt(now)
                .build();

        RepositoryResponse result = mapper.toResponse(entity);

        assertEquals("my-org/my-project", result.fullName());
    }

    @Test
    void debeGenerarHtmlUrlCorrectamente() {
        RepositoryEntity entity = RepositoryEntity.builder()
                .id(1L)
                .owner("company")
                .name("api-service")
                .visibility(RepoVisibility.PUBLIC)
                .defaultBranch("main")
                .createdAt(now)
                .updatedAt(now)
                .build();

        RepositoryResponse result = mapper.toResponse(entity);

        assertEquals("/v1/repos/company/api-service", result.htmlUrl());
    }
}
