package com.githubx.githubfilesms.mapper;

import com.githubx.githubfilesms.dto.response.DirectoryEntryResponse;
import com.githubx.githubfilesms.dto.response.FileContentResponse;
import com.githubx.githubfilesms.model.FileEntity;
import com.githubx.githubfilesms.model.RepositoryEntity;
import com.githubx.githubfilesms.model.enums.GitObjectType;
import com.githubx.githubfilesms.model.enums.RepoVisibility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileMapperTest {

    private FileMapper mapper;
    private RepositoryEntity repository;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(FileMapper.class);
        repository = RepositoryEntity.builder()
                .id(1L)
                .owner("testowner")
                .name("testrepo")
                .description("Test repository")
                .visibility(RepoVisibility.PUBLIC)
                .defaultBranch("main")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }

    @Test
    void debeMapearFileEntityAFileContentResponse() {
        FileEntity entity = FileEntity.builder()
                .id(1L)
                .repository(repository)
                .name("README.md")
                .path("README.md")
                .sha("abc123def456")
                .type(GitObjectType.FILE)
                .size(1024L)
                .content("IyBIZWxsbyBXb3JsZA==")
                .encoding("base64")
                .branch("main")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        FileContentResponse result = mapper.toFileContentResponse(entity);

        assertNotNull(result);
        assertEquals("README.md", result.name());
        assertEquals("README.md", result.path());
        assertEquals("abc123def456", result.sha());
        assertEquals(GitObjectType.FILE, result.type());
        assertEquals(1024L, result.size());
        assertEquals("IyBIZWxsbyBXb3JsZA==", result.content());
        assertEquals("base64", result.encoding());
        assertNotNull(result.downloadUrl());
        assertNotNull(result.htmlUrl());
    }

    @Test
    void debeMapearFileEntityADirectoryEntryResponse() {
        FileEntity entity = FileEntity.builder()
                .id(2L)
                .repository(repository)
                .name("src")
                .path("src")
                .sha("folder123")
                .type(GitObjectType.DIRECTORY)
                .size(null)
                .content(null)
                .encoding(null)
                .branch("main")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        DirectoryEntryResponse result = mapper.toDirectoryEntryResponse(entity);

        assertNotNull(result);
        assertEquals("src", result.name());
        assertEquals("src", result.path());
        assertEquals("folder123", result.sha());
        assertEquals(GitObjectType.DIRECTORY, result.type());
        assertNull(result.size());
        assertNotNull(result.downloadUrl());
    }

    @Test
    void debeMapearListaDeFileEntities() {
        FileEntity file1 = FileEntity.builder()
                .id(1L)
                .repository(repository)
                .name("file1.txt")
                .path("file1.txt")
                .sha("sha1")
                .type(GitObjectType.FILE)
                .size(100L)
                .branch("main")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        FileEntity file2 = FileEntity.builder()
                .id(2L)
                .repository(repository)
                .name("folder")
                .path("folder")
                .sha("sha2")
                .type(GitObjectType.DIRECTORY)
                .branch("main")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        List<DirectoryEntryResponse> result = mapper.toDirectoryEntryResponseList(List.of(file1, file2));

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("file1.txt", result.get(0).name());
        assertEquals("folder", result.get(1).name());
    }

    @Test
    void debeMapearListaVacia() {
        List<DirectoryEntryResponse> result = mapper.toDirectoryEntryResponseList(Collections.emptyList());
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void debeGenerarDownloadUrlCorrectamente() {
        FileEntity entity = FileEntity.builder()
                .id(1L)
                .repository(repository)
                .name("deep-file.java")
                .path("src/main/java/deep-file.java")
                .sha("sha123")
                .type(GitObjectType.FILE)
                .size(500L)
                .branch("develop")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        FileContentResponse result = mapper.toFileContentResponse(entity);

        assertTrue(result.downloadUrl().contains("testowner"));
        assertTrue(result.downloadUrl().contains("testrepo"));
        assertTrue(result.downloadUrl().contains("src/main/java/deep-file.java"));
    }

    @Test
    void debeGenerarHtmlUrlCorrectamente() {
        FileEntity entity = FileEntity.builder()
                .id(1L)
                .repository(repository)
                .name("config.json")
                .path("config/config.json")
                .sha("configsha")
                .type(GitObjectType.FILE)
                .size(200L)
                .branch("main")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        FileContentResponse result = mapper.toFileContentResponse(entity);

        assertTrue(result.htmlUrl().contains("testowner"));
        assertTrue(result.htmlUrl().contains("testrepo"));
        assertTrue(result.htmlUrl().contains("config/config.json"));
    }

    @Test
    void debeMapearArchivoConContenidoNull() {
        FileEntity entity = FileEntity.builder()
                .id(1L)
                .repository(repository)
                .name("binary.bin")
                .path("binary.bin")
                .sha("binsha")
                .type(GitObjectType.FILE)
                .size(1000000L)
                .content(null)
                .encoding(null)
                .branch("main")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        FileContentResponse result = mapper.toFileContentResponse(entity);

        assertNotNull(result);
        assertNull(result.content());
        assertNull(result.encoding());
    }
}
