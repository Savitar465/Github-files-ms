package com.githubx.githubfilesms.service.implementacion;

import com.githubx.githubfilesms.dao.FileDao;
import com.githubx.githubfilesms.dao.CommitDao;
import com.githubx.githubfilesms.dao.RepositoryDao;
import com.githubx.githubfilesms.dto.request.CreateFileRequest;
import com.githubx.githubfilesms.dto.response.DirectoryEntryResponse;
import com.githubx.githubfilesms.dto.response.FileContentResponse;
import com.githubx.githubfilesms.dto.response.GetFileContentBodyResponse;
import com.githubx.githubfilesms.mapper.FileMapper;
import com.githubx.githubfilesms.model.FileEntity;
import com.githubx.githubfilesms.model.RepositoryEntity;
import com.githubx.githubfilesms.model.enums.GitObjectType;
import com.githubx.githubfilesms.util.errorhandling.EntityConflictException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileServiceTest {

    @Mock
    private FileDao fileDao;
    @Mock
    private CommitDao commitDao;
    @Mock
    private RepositoryDao repositoryDao;
    @Mock
    private FileMapper fileMapper;

    @InjectMocks
    private FileServiceImpl fileService;

    private RepositoryEntity repo() {
        return RepositoryEntity.builder()
                .id(1L)
                .owner("o")
                .name("r")
                .defaultBranch("main")
                .build();
    }

    @Test
    void debeListarRaiz() {
        when(repositoryDao.findByOwnerAndName("o", "r")).thenReturn(Optional.of(repo()));
        when(fileDao.findRootEntriesByRepositoryAndBranch(any(RepositoryEntity.class), eq("main")))
                .thenReturn(List.of(
                        FileEntity.builder()
                                .id(1L)
                                .name("README.md")
                                .path("README.md")
                                .sha("sha1")
                                .type(GitObjectType.FILE)
                                .branch("main")
                                .build()
                ));
        when(fileMapper.toDirectoryEntryResponseList(anyList()))
                .thenReturn(List.of(new DirectoryEntryResponse("README.md", "README.md", "sha1", GitObjectType.FILE, 1L, null)));

        GetFileContentBodyResponse out = fileService.getRepositoryContents("o", "r", "", null);

        assertNotNull(out.entries());
        assertEquals(1, out.entries().size());
        assertEquals("README.md", out.entries().get(0).name());
    }

    @Test
    void debeCrearArchivoLanzaConflictSiExiste() {
        when(repositoryDao.findByOwnerAndName("o", "r")).thenReturn(Optional.of(repo()));
        when(fileDao.existsByRepositoryAndPathAndBranch(any(RepositoryEntity.class), eq("a.txt"), eq("main")))
                .thenReturn(true);

        assertThrows(EntityConflictException.class, () -> fileService.createFile("o", "r", "a.txt",
                new CreateFileRequest("hola".getBytes(), "m", "main", null, null)));
    }

    @Test
    void debeCrearArchivoYDelegarAMapper() {
        when(repositoryDao.findByOwnerAndName("o", "r")).thenReturn(Optional.of(repo()));
        when(commitDao.findByRepositoryAndBranchOrderByCommitterDateDesc(any(RepositoryEntity.class), eq("main"), any(Pageable.class)))
                .thenReturn(Page.empty());
        when(commitDao.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(fileDao.existsByRepositoryAndPathAndBranch(any(RepositoryEntity.class), eq("a.txt"), eq("main")))
                .thenReturn(false);
        when(fileDao.save(any(FileEntity.class))).thenAnswer(invocation -> {
            FileEntity f = invocation.getArgument(0);
            if (f.getId() == null) {
                f = FileEntity.builder()
                        .id(99L)
                        .repository(f.getRepository())
                        .name(f.getName())
                        .path(f.getPath())
                        .sha(f.getSha())
                        .type(f.getType())
                        .size(f.getSize())
                        .content(f.getContent())
                        .encoding(f.getEncoding())
                        .branch(f.getBranch())
                        .lastCommitSha(f.getLastCommitSha())
                        .build();
            }
            return f;
        });
        when(fileMapper.toFileContentResponse(any(FileEntity.class)))
                .thenReturn(new FileContentResponse("a.txt", "a.txt", "shax", GitObjectType.FILE, 4L, "base64", "aG9sYQ==",
                        null, null, "csha"));

        var out = fileService.createFile("o", "r", "a.txt",
                new CreateFileRequest("hola".getBytes(), "m", "main", null, null));

        assertNotNull(out.commit());
        assertEquals("m", out.commit().message());
        assertEquals("a.txt", out.content().path());
    }
}
