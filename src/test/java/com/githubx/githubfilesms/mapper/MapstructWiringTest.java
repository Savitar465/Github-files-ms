package com.githubx.githubfilesms.mapper;

import com.githubx.githubfilesms.dto.request.CreateRepositoryRequest;
import com.githubx.githubfilesms.model.CommitEntity;
import com.githubx.githubfilesms.model.FileEntity;
import com.githubx.githubfilesms.model.RepositoryEntity;
import com.githubx.githubfilesms.model.enums.GitObjectType;
import com.githubx.githubfilesms.model.enums.RepoVisibility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {FileMapperImpl.class, CommitMapperImpl.class, RepositoryMapperImpl.class})
class MapstructWiringTest {

    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private CommitMapper commitMapper;
    @Autowired
    private RepositoryMapper repositoryMapper;

    @Test
    void fileMapperMapeaUrls() {
        RepositoryEntity repo = RepositoryEntity.builder()
                .id(1L)
                .owner("o")
                .name("r")
                .description(null)
                .visibility(RepoVisibility.PRIVATE)
                .defaultBranch("main")
                .build();

        FileEntity fe = FileEntity.builder()
                .id(1L)
                .repository(repo)
                .name("a.txt")
                .path("a/b/a.txt")
                .sha("sha")
                .type(GitObjectType.FILE)
                .size(1L)
                .content("YQ==")
                .encoding("base64")
                .branch("main")
                .lastCommitSha("c")
                .build();

        var resp = fileMapper.toFileContentResponse(fe);
        assertNotNull(resp.downloadUrl());
        assertNotNull(resp.htmlUrl());
    }

    @Test
    void commitMapperMapeaPadresYFirmas() {
        RepositoryEntity repo = RepositoryEntity.builder()
                .id(1L)
                .owner("o")
                .name("r")
                .description(null)
                .visibility(RepoVisibility.PRIVATE)
                .defaultBranch("main")
                .build();
        CommitEntity c = CommitEntity.builder()
                .id(1L)
                .repository(repo)
                .sha("s")
                .message("m")
                .authorName("a")
                .authorEmail("a@a.com")
                .authorDate(Instant.parse("2026-01-01T00:00:00Z"))
                .committerName("b")
                .committerEmail("b@b.com")
                .committerDate(Instant.parse("2026-01-02T00:00:00Z"))
                .parentSha("p0")
                .branch("main")
                .build();
        c.setFiles(List.of());

        var r = commitMapper.toCommitResponse(c);
        assertEquals("s", r.sha());
        assertEquals(1, r.parents().size());
        assertEquals("p0", r.parents().get(0).sha());
    }

    @Test
    void repositoryMapperCreaEntidad() {
        var e = repositoryMapper.toEntity(new CreateRepositoryRequest("n", "d", RepoVisibility.PUBLIC, "dev"));
        assertEquals("n", e.getName());
    }
}
