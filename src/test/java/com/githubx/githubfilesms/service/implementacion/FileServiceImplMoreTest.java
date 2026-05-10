package com.githubx.githubfilesms.service.implementacion;

import com.githubx.githubfilesms.dao.FileDao;
import com.githubx.githubfilesms.dao.CommitDao;
import com.githubx.githubfilesms.dao.RepositoryDao;
import com.githubx.githubfilesms.dto.request.CreateFileRequest;
import com.githubx.githubfilesms.dto.request.CreateFolderRequest;
import com.githubx.githubfilesms.dto.request.UpdateFileRequest;
import com.githubx.githubfilesms.dto.response.DirectoryEntryResponse;
import com.githubx.githubfilesms.dto.response.FileContentResponse;
import com.githubx.githubfilesms.dto.response.GetFileContentBodyResponse;
import com.githubx.githubfilesms.mapper.FileMapper;
import com.githubx.githubfilesms.model.FileEntity;
import com.githubx.githubfilesms.model.RepositoryEntity;
import com.githubx.githubfilesms.model.enums.GitObjectType;
import com.githubx.githubfilesms.util.errorhandling.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class FileServiceImplMoreTest {

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
    void debeObtenerArchivo() {
        when(repositoryDao.findByOwnerAndName("o", "r")).thenReturn(Optional.of(repo()));
        when(fileDao.findByRepositoryAndPathAndBranch(any(RepositoryEntity.class), eq("a.txt"), eq("main")))
                .thenReturn(Optional.of(
                        FileEntity.builder()
                                .id(1L)
                                .repository(repo())
                                .name("a.txt")
                                .path("a.txt")
                                .sha("s")
                                .type(GitObjectType.FILE)
                                .branch("main")
                                .content("YQ==")
                                .build()
                ));
        when(fileMapper.toFileContentResponse(any(FileEntity.class)))
                .thenReturn(new FileContentResponse("a.txt", "a.txt", "s", GitObjectType.FILE, 1L, "base64", "YQ==", null, null, "c"));

        var out = fileService.getFileContent("o", "r", "a.txt", null);
        assertEquals("a.txt", out.file().path());
    }

    @Test
    void debeListarHijosDeDirectorio() {
        when(repositoryDao.findByOwnerAndName("o", "r")).thenReturn(Optional.of(repo()));
        when(fileDao.findByRepositoryAndPathAndBranch(any(RepositoryEntity.class), eq("src"), eq("main")))
                .thenReturn(Optional.of(
                        FileEntity.builder()
                                .id(1L)
                                .repository(repo())
                                .name("src")
                                .path("src")
                                .sha("sdir")
                                .type(GitObjectType.DIRECTORY)
                                .branch("main")
                                .build()
                ));
        when(fileDao.findDirectChildrenByRepositoryAndBranchAndParentPath(any(RepositoryEntity.class), eq("main"), eq("src/"), anyString()))
                .thenReturn(List.of(
                        FileEntity.builder()
                                .id(2L)
                                .repository(repo())
                                .name("Main.java")
                                .path("src/Main.java")
                                .sha("fx")
                                .type(GitObjectType.FILE)
                                .branch("main")
                                .build()
                ));
        when(fileMapper.toDirectoryEntryResponseList(anyList()))
                .thenReturn(List.of(new DirectoryEntryResponse("Main.java", "src/Main.java", "fx", GitObjectType.FILE, 1L, null)));

        GetFileContentBodyResponse out = fileService.getFileContent("o", "r", "src", null);
        assertEquals(1, out.entries().size());
    }

    @Test
    void debeBloquearDescargaDeDirectorio() {
        when(repositoryDao.findByOwnerAndName("o", "r")).thenReturn(Optional.of(repo()));
        when(fileDao.findByRepositoryAndPathAndBranch(any(RepositoryEntity.class), eq("src"), eq("main")))
                .thenReturn(Optional.of(
                        FileEntity.builder()
                                .id(1L)
                                .repository(repo())
                                .name("src")
                                .path("src")
                                .sha("s")
                                .type(GitObjectType.DIRECTORY)
                                .branch("main")
                                .build()
                ));

        assertThrows(EntityNotFoundException.class, () -> fileService.getRawFile("o", "r", "src", null));
    }

    @Test
    void debeDecodificarRawCuandoContenidoNull() {
        when(repositoryDao.findByOwnerAndName("o", "r")).thenReturn(Optional.of(repo()));
        when(fileDao.findByRepositoryAndPathAndBranch(any(RepositoryEntity.class), eq("a.txt"), eq("main")))
                .thenReturn(Optional.of(
                        FileEntity.builder()
                                .id(1L)
                                .repository(repo())
                                .name("a.txt")
                                .path("a.txt")
                                .sha("s")
                                .type(GitObjectType.FILE)
                                .branch("main")
                                .content(null)
                                .build()
                ));
        assertEquals(0, fileService.getRawFile("o", "r", "a.txt", null).length);
    }

    @Test
    void debeActualizarArchivo() {
        when(repositoryDao.findByOwnerAndName("o", "r")).thenReturn(Optional.of(repo()));
        when(commitDao.findByRepositoryAndBranchOrderByCommitterDateDesc(any(RepositoryEntity.class), eq("main"), any(Pageable.class)))
                .thenReturn(Page.empty());
        when(commitDao.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        FileEntity existing = FileEntity.builder()
                .id(1L)
                .repository(repo())
                .name("a.txt")
                .path("a.txt")
                .sha("old")
                .type(GitObjectType.FILE)
                .branch("main")
                .content("YQ==")
                .build();
        when(fileDao.findByRepositoryAndPathAndBranch(any(RepositoryEntity.class), eq("a.txt"), eq("main")))
                .thenReturn(Optional.of(existing));
        when(fileDao.save(any(FileEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(fileMapper.toFileContentResponse(any(FileEntity.class)))
                .thenReturn(new FileContentResponse("a.txt", "a.txt", "new", GitObjectType.FILE, 1L, "base64", "eA==", null, null, "c"));

        var out = fileService.updateFile("o", "r", "a.txt",
                new UpdateFileRequest("old", "x".getBytes(), "m", "main", "old.txt", null, null));
        assertEquals("m", out.commit().message());
    }

    @Test
    void debeEliminarArchivo() {
        when(repositoryDao.findByOwnerAndName("o", "r")).thenReturn(Optional.of(repo()));
        when(commitDao.findByRepositoryAndBranchOrderByCommitterDateDesc(any(RepositoryEntity.class), eq("main"), any(Pageable.class)))
                .thenReturn(Page.empty());
        when(commitDao.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        FileEntity existing = FileEntity.builder()
                .id(1L)
                .repository(repo())
                .name("a.txt")
                .path("a.txt")
                .sha("old")
                .type(GitObjectType.FILE)
                .branch("main")
                .content("YQ==")
                .build();
        when(fileDao.findByRepositoryAndPathAndBranch(any(RepositoryEntity.class), eq("a.txt"), eq("main")))
                .thenReturn(Optional.of(existing));

        var out = fileService.deleteFile("o", "r", "a.txt", "old", "m", "main");
        assertEquals("m", out.commit().message());
        verify(fileDao).delete(existing);
    }

    @Test
    void debeCrearCarpeta() {
        when(repositoryDao.findByOwnerAndName("o", "r")).thenReturn(Optional.of(repo()));
        when(commitDao.findByRepositoryAndBranchOrderByCommitterDateDesc(any(RepositoryEntity.class), eq("main"), any(Pageable.class)))
                .thenReturn(Page.empty());
        when(commitDao.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(fileDao.existsByRepositoryAndPathAndBranch(any(RepositoryEntity.class), eq("d/.gitkeep"), eq("main")))
                .thenReturn(false);
        when(fileDao.save(any(FileEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(fileMapper.toFileContentResponse(any(FileEntity.class)))
                .thenReturn(new FileContentResponse(".gitkeep", "d/.gitkeep", "s", GitObjectType.FILE, 0L, "base64", "", null, null, "c"));

        var out = fileService.createFolder("o", "r", new CreateFolderRequest("d", "m", "main"));
        assertEquals("d/.gitkeep", out.content().path());
    }
}
