package com.githubx.githubfilesms.model;

import com.githubx.githubfilesms.model.enums.GitObjectType;
import com.githubx.githubfilesms.model.enums.RepoVisibility;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class EntityTest {

    // ── RepositoryEntity tests ─────────────────────────────────────────

    @Test
    void debeCrearRepositoryEntityConBuilder() {
        Instant now = Instant.now();
        RepositoryEntity entity = RepositoryEntity.builder()
                .id(1L)
                .owner("testowner")
                .name("testrepo")
                .description("Description")
                .visibility(RepoVisibility.PUBLIC)
                .defaultBranch("main")
                .createdAt(now)
                .updatedAt(now)
                .build();

        assertEquals(1L, entity.getId());
        assertEquals("testowner", entity.getOwner());
        assertEquals("testrepo", entity.getName());
        assertEquals("Description", entity.getDescription());
        assertEquals(RepoVisibility.PUBLIC, entity.getVisibility());
        assertEquals("main", entity.getDefaultBranch());
    }

    @Test
    void debeCrearRepositoryEntityConNoArgsConstructor() {
        RepositoryEntity entity = new RepositoryEntity();
        assertNull(entity.getId());
        assertNull(entity.getOwner());
    }

    @Test
    void debeSetearValoresEnRepositoryEntity() {
        RepositoryEntity entity = new RepositoryEntity();
        entity.setId(1L);
        entity.setOwner("owner");
        entity.setName("name");
        entity.setVisibility(RepoVisibility.PRIVATE);

        assertEquals(1L, entity.getId());
        assertEquals("owner", entity.getOwner());
        assertEquals("name", entity.getName());
        assertEquals(RepoVisibility.PRIVATE, entity.getVisibility());
    }

    // ── FileEntity tests ───────────────────────────────────────────────

    @Test
    void debeCrearFileEntityConBuilder() {
        RepositoryEntity repo = RepositoryEntity.builder()
                .owner("owner")
                .name("repo")
                .build();

        FileEntity entity = FileEntity.builder()
                .id(1L)
                .repository(repo)
                .name("file.txt")
                .path("src/file.txt")
                .sha("sha123")
                .type(GitObjectType.FILE)
                .size(100L)
                .content("content")
                .encoding("base64")
                .branch("main")
                .build();

        assertEquals(1L, entity.getId());
        assertEquals("file.txt", entity.getName());
        assertEquals("src/file.txt", entity.getPath());
        assertEquals(GitObjectType.FILE, entity.getType());
        assertEquals(100L, entity.getSize());
    }

    @Test
    void debeGenerarDownloadUrl() {
        RepositoryEntity repo = RepositoryEntity.builder()
                .owner("testowner")
                .name("testrepo")
                .build();

        FileEntity entity = FileEntity.builder()
                .repository(repo)
                .path("src/main.java")
                .branch("develop")
                .build();

        String url = entity.getDownloadUrl();
        assertTrue(url.contains("testowner"));
        assertTrue(url.contains("testrepo"));
        assertTrue(url.contains("src/main.java"));
        assertTrue(url.contains("develop"));
    }

    @Test
    void debeGenerarHtmlUrl() {
        RepositoryEntity repo = RepositoryEntity.builder()
                .owner("testowner")
                .name("testrepo")
                .build();

        FileEntity entity = FileEntity.builder()
                .repository(repo)
                .path("README.md")
                .branch("main")
                .build();

        String url = entity.getHtmlUrl();
        assertTrue(url.contains("testowner"));
        assertTrue(url.contains("testrepo"));
        assertTrue(url.contains("README.md"));
    }

    // ── CommitEntity tests ─────────────────────────────────────────────

    @Test
    void debeCrearCommitEntityConBuilder() {
        Instant now = Instant.now();
        RepositoryEntity repo = RepositoryEntity.builder()
                .owner("owner")
                .name("repo")
                .build();

        CommitEntity entity = CommitEntity.builder()
                .id(1L)
                .repository(repo)
                .sha("commitsha123")
                .message("Initial commit")
                .authorName("John Doe")
                .authorEmail("john@example.com")
                .authorDate(now)
                .committerName("Jane Doe")
                .committerEmail("jane@example.com")
                .committerDate(now)
                .parentSha("parentsha")
                .branch("main")
                .build();

        assertEquals(1L, entity.getId());
        assertEquals("commitsha123", entity.getSha());
        assertEquals("Initial commit", entity.getMessage());
        assertEquals("John Doe", entity.getAuthorName());
        assertEquals("jane@example.com", entity.getCommitterEmail());
        assertEquals("parentsha", entity.getParentSha());
    }

    @Test
    void debeGenerarHtmlUrlParaCommit() {
        RepositoryEntity repo = RepositoryEntity.builder()
                .owner("testowner")
                .name("testrepo")
                .build();

        CommitEntity entity = CommitEntity.builder()
                .repository(repo)
                .sha("abc123def456")
                .build();

        String url = entity.getHtmlUrl();
        assertEquals("/v1/repos/testowner/testrepo/commits/abc123def456", url);
    }

    // ── CommitFileEntity tests ─────────────────────────────────────────

    @Test
    void debeCrearCommitFileEntityConBuilder() {
        CommitFileEntity entity = CommitFileEntity.builder()
                .id(1L)
                .sha("filesha")
                .filename("Main.java")
                .status("modified")
                .additions(10)
                .deletions(5)
                .changes(15)
                .patch("@@ -1,5 +1,10 @@")
                .build();

        assertEquals(1L, entity.getId());
        assertEquals("filesha", entity.getSha());
        assertEquals("Main.java", entity.getFilename());
        assertEquals("modified", entity.getStatus());
        assertEquals(10, entity.getAdditions());
        assertEquals(5, entity.getDeletions());
        assertEquals(15, entity.getChanges());
    }

    @Test
    void debeCrearCommitFileEntityConNoArgsConstructor() {
        CommitFileEntity entity = new CommitFileEntity();
        assertNull(entity.getId());
        assertNull(entity.getSha());
    }

    // ── Enum tests ─────────────────────────────────────────────────────

    @Test
    void debeContenerTodosLosGitObjectTypes() {
        assertEquals(2, GitObjectType.values().length);
        assertNotNull(GitObjectType.FILE);
        assertNotNull(GitObjectType.DIRECTORY);
    }

    @Test
    void debeContenerTodosLosRepoVisibility() {
        assertEquals(2, RepoVisibility.values().length);
        assertNotNull(RepoVisibility.PUBLIC);
        assertNotNull(RepoVisibility.PRIVATE);
    }

    @Test
    void debeConvertirGitObjectTypeDeString() {
        assertEquals(GitObjectType.FILE, GitObjectType.valueOf("FILE"));
        assertEquals(GitObjectType.DIRECTORY, GitObjectType.valueOf("DIRECTORY"));
    }

    @Test
    void debeConvertirRepoVisibilityDeString() {
        assertEquals(RepoVisibility.PUBLIC, RepoVisibility.valueOf("PUBLIC"));
        assertEquals(RepoVisibility.PRIVATE, RepoVisibility.valueOf("PRIVATE"));
    }
}
