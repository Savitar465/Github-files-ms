package com.githubx.githubfilesms.service.implementacion;

import com.githubx.githubfilesms.dao.RepositoryDao;
import com.githubx.githubfilesms.dto.request.CreateRepositoryRequest;
import com.githubx.githubfilesms.dto.response.RepositoryResponse;
import com.githubx.githubfilesms.mapper.RepositoryMapper;
import com.githubx.githubfilesms.model.RepositoryEntity;
import com.githubx.githubfilesms.model.enums.RepoVisibility;
import com.githubx.githubfilesms.util.errorhandling.EntityConflictException;
import com.githubx.githubfilesms.util.errorhandling.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RepositoryServiceImplTest {

    @Mock
    private RepositoryDao repositoryDao;
    @Mock
    private RepositoryMapper repositoryMapper;

    @InjectMocks
    private RepositoryServiceImpl service;

    @Test
    void debeCrearRepositorio() {
        when(repositoryDao.existsByOwnerAndName("o", "r")).thenReturn(false);
        when(repositoryDao.save(any(RepositoryEntity.class)))
                .thenAnswer(invocation -> {
                    RepositoryEntity e = invocation.getArgument(0);
                    return RepositoryEntity.builder()
                            .id(1L)
                            .owner(e.getOwner())
                            .name(e.getName())
                            .description(e.getDescription())
                            .visibility(e.getVisibility())
                            .defaultBranch(e.getDefaultBranch())
                            .build();
                });
        when(repositoryMapper.toResponse(any(RepositoryEntity.class)))
                .thenReturn(new RepositoryResponse(1L, "o", "r", "o/r", null, RepoVisibility.PRIVATE, "main", null, null, null));

        var req = new CreateRepositoryRequest("r", "d", null, "main");
        RepositoryResponse res = service.createRepository("o", req);
        assertEquals("o/r", res.fullName());
    }

    @Test
    void debeLanzarConflictoSiExiste() {
        when(repositoryDao.existsByOwnerAndName("o", "r")).thenReturn(true);
        assertThrows(EntityConflictException.class, () -> service.createRepository("o", new CreateRepositoryRequest("r", null, null, null)));
    }

    @Test
    void debeObtenerRepositorio() {
        when(repositoryDao.findByOwnerAndName("o", "r"))
                .thenReturn(Optional.of(RepositoryEntity.builder()
                        .id(1L)
                        .owner("o")
                        .name("r")
                        .description(null)
                        .visibility(RepoVisibility.PRIVATE)
                        .defaultBranch("main")
                        .build()));
        when(repositoryMapper.toResponse(any(RepositoryEntity.class)))
                .thenReturn(new RepositoryResponse(1L, "o", "r", "o/r", null, RepoVisibility.PRIVATE, "main", null, null, null));

        assertEquals("o/r", service.getRepository("o", "r").fullName());
    }

    @Test
    void debeListarSoloOwner() {
        when(repositoryDao.findAll()).thenReturn(List.of(
                RepositoryEntity.builder().id(1L).owner("o").name("a").description(null).visibility(RepoVisibility.PRIVATE).defaultBranch("main").build(),
                RepositoryEntity.builder().id(2L).owner("x").name("b").description(null).visibility(RepoVisibility.PRIVATE).defaultBranch("main").build()
        ));
        when(repositoryMapper.toResponseList(anyList()))
                .thenReturn(List.of(new RepositoryResponse(1L, "o", "a", "o/a", null, RepoVisibility.PRIVATE, "main", null, null, null)));

        assertEquals(1, service.listRepositories("o").size());
    }

    @Test
    void debeEliminar() {
        RepositoryEntity e = RepositoryEntity.builder()
                .id(1L).owner("o").name("r").description(null)
                .visibility(RepoVisibility.PRIVATE).defaultBranch("main")
                .build();
        when(repositoryDao.findByOwnerAndName("o", "r")).thenReturn(Optional.of(e));
        service.deleteRepository("o", "r");
        verify(repositoryDao).delete(e);
    }

    @Test
    void debeLanzar404EnDelete() {
        when(repositoryDao.findByOwnerAndName("o", "r")).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.deleteRepository("o", "r"));
    }
}
