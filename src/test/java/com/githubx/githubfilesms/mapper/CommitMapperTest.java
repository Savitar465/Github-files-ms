package com.githubx.githubfilesms.mapper;

import com.githubx.githubfilesms.dto.response.CommitFileResponse;
import com.githubx.githubfilesms.dto.response.CommitParentResponse;
import com.githubx.githubfilesms.dto.response.CommitResponse;
import com.githubx.githubfilesms.dto.response.CommitSignatureResponse;
import com.githubx.githubfilesms.model.CommitEntity;
import com.githubx.githubfilesms.model.CommitFileEntity;
import com.githubx.githubfilesms.model.RepositoryEntity;
import com.githubx.githubfilesms.model.enums.RepoVisibility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommitMapperTest {

    private CommitMapper mapper;
    private RepositoryEntity repository;
    private Instant now;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(CommitMapper.class);
        now = Instant.now();
        repository = RepositoryEntity.builder()
                .id(1L)
                .owner("testowner")
                .name("testrepo")
                .description("Test repository")
                .visibility(RepoVisibility.PUBLIC)
                .defaultBranch("main")
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    @Test
    void debeMapearCommitEntityACommitResponse() {
        CommitEntity entity = CommitEntity.builder()
                .id(1L)
                .repository(repository)
                .sha("abc123def456")
                .message("Initial commit")
                .authorName("John Doe")
                .authorEmail("john@example.com")
                .authorDate(now)
                .committerName("Jane Doe")
                .committerEmail("jane@example.com")
                .committerDate(now)
                .parentSha("parent123")
                .branch("main")
                .files(Collections.emptyList())
                .createdAt(now)
                .build();

        CommitResponse result = mapper.toCommitResponse(entity);

        assertNotNull(result);
        assertEquals("abc123def456", result.sha());
        assertEquals("Initial commit", result.message());
        assertNotNull(result.author());
        assertEquals("John Doe", result.author().name());
        assertEquals("john@example.com", result.author().email());
        assertNotNull(result.committer());
        assertEquals("Jane Doe", result.committer().name());
        assertEquals("jane@example.com", result.committer().email());
        assertNotNull(result.htmlUrl());
        assertTrue(result.htmlUrl().contains("testowner"));
        assertTrue(result.htmlUrl().contains("testrepo"));
    }

    @Test
    void debeMapearCommitEntityConParents() {
        CommitEntity entity = CommitEntity.builder()
                .id(1L)
                .repository(repository)
                .sha("childsha")
                .message("Child commit")
                .authorName("Author")
                .authorEmail("author@test.com")
                .authorDate(now)
                .committerName("Committer")
                .committerEmail("committer@test.com")
                .committerDate(now)
                .parentSha("parentsha123")
                .branch("main")
                .files(Collections.emptyList())
                .createdAt(now)
                .build();

        CommitResponse result = mapper.toCommitResponse(entity);

        assertNotNull(result.parents());
        assertEquals(1, result.parents().size());
        assertEquals("parentsha123", result.parents().get(0).sha());
        assertTrue(result.parents().get(0).url().contains("parentsha123"));
    }

    @Test
    void debeMapearCommitEntitySinParent() {
        CommitEntity entity = CommitEntity.builder()
                .id(1L)
                .repository(repository)
                .sha("rootsha")
                .message("Root commit")
                .authorName("Author")
                .authorEmail("author@test.com")
                .authorDate(now)
                .committerName("Committer")
                .committerEmail("committer@test.com")
                .committerDate(now)
                .parentSha(null)
                .branch("main")
                .files(Collections.emptyList())
                .createdAt(now)
                .build();

        CommitResponse result = mapper.toCommitResponse(entity);

        assertNotNull(result.parents());
        assertTrue(result.parents().isEmpty());
    }

    @Test
    void debeMapearListaDeCommitEntities() {
        CommitEntity commit1 = CommitEntity.builder()
                .id(1L)
                .repository(repository)
                .sha("sha1")
                .message("First commit")
                .authorName("Author")
                .authorEmail("author@test.com")
                .authorDate(now)
                .committerName("Committer")
                .committerEmail("committer@test.com")
                .committerDate(now)
                .files(Collections.emptyList())
                .createdAt(now)
                .build();

        CommitEntity commit2 = CommitEntity.builder()
                .id(2L)
                .repository(repository)
                .sha("sha2")
                .message("Second commit")
                .authorName("Author")
                .authorEmail("author@test.com")
                .authorDate(now)
                .committerName("Committer")
                .committerEmail("committer@test.com")
                .committerDate(now)
                .parentSha("sha1")
                .files(Collections.emptyList())
                .createdAt(now)
                .build();

        List<CommitResponse> result = mapper.toCommitResponseList(List.of(commit1, commit2));

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("First commit", result.get(0).message());
        assertEquals("Second commit", result.get(1).message());
    }

    @Test
    void debeMapearCommitFileEntity() {
        CommitFileEntity entity = CommitFileEntity.builder()
                .id(1L)
                .sha("filesha")
                .filename("src/Main.java")
                .status("modified")
                .additions(10)
                .deletions(5)
                .changes(15)
                .patch("@@ -1,5 +1,10 @@")
                .build();

        CommitFileResponse result = mapper.toCommitFileResponse(entity);

        assertNotNull(result);
        assertEquals("filesha", result.sha());
        assertEquals("src/Main.java", result.filename());
        assertEquals("modified", result.status());
        assertEquals(10, result.additions());
        assertEquals(5, result.deletions());
        assertEquals(15, result.changes());
        assertEquals("@@ -1,5 +1,10 @@", result.patch());
    }

    @Test
    void debeMapearListaDeCommitFileEntities() {
        CommitFileEntity file1 = CommitFileEntity.builder()
                .id(1L)
                .sha("sha1")
                .filename("file1.txt")
                .status("added")
                .additions(50)
                .deletions(0)
                .changes(50)
                .build();

        CommitFileEntity file2 = CommitFileEntity.builder()
                .id(2L)
                .sha("sha2")
                .filename("file2.txt")
                .status("deleted")
                .additions(0)
                .deletions(30)
                .changes(30)
                .build();

        List<CommitFileResponse> result = mapper.toCommitFileResponseList(List.of(file1, file2));

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("added", result.get(0).status());
        assertEquals("deleted", result.get(1).status());
    }

    @Test
    void debeCrearAuthorSignatureCorrectamente() {
        CommitEntity entity = CommitEntity.builder()
                .authorName("John Author")
                .authorEmail("john.author@example.com")
                .authorDate(Instant.parse("2026-06-15T10:30:00Z"))
                .committerName("Jane Committer")
                .committerEmail("jane@test.com")
                .committerDate(now)
                .build();

        CommitSignatureResponse result = mapper.toAuthorSignature(entity);

        assertNotNull(result);
        assertEquals("John Author", result.name());
        assertEquals("john.author@example.com", result.email());
        assertEquals("2026-06-15T10:30:00Z", result.date());
    }

    @Test
    void debeCrearCommitterSignatureCorrectamente() {
        CommitEntity entity = CommitEntity.builder()
                .authorName("John")
                .authorEmail("john@test.com")
                .authorDate(now)
                .committerName("Jane Committer")
                .committerEmail("jane.committer@example.com")
                .committerDate(Instant.parse("2026-07-20T14:45:00Z"))
                .build();

        CommitSignatureResponse result = mapper.toCommitterSignature(entity);

        assertNotNull(result);
        assertEquals("Jane Committer", result.name());
        assertEquals("jane.committer@example.com", result.email());
        assertEquals("2026-07-20T14:45:00Z", result.date());
    }

    @Test
    void debeCrearParentsCorrectamente() {
        CommitEntity entity = CommitEntity.builder()
                .repository(repository)
                .sha("childsha")
                .parentSha("parentsha")
                .build();

        List<CommitParentResponse> result = mapper.toParents(entity);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("parentsha", result.get(0).sha());
        assertTrue(result.get(0).url().contains("/v1/repos/testowner/testrepo/commits/parentsha"));
    }

    @Test
    void debeFormatearInstantCorrectamente() {
        Instant instant = Instant.parse("2026-03-15T08:30:00Z");
        String result = mapper.formatInstant(instant);
        assertEquals("2026-03-15T08:30:00Z", result);
    }

    @Test
    void debeRetornarNullParaInstantNull() {
        assertNull(mapper.formatInstant(null));
    }

    @Test
    void debeMapearListaVaciaDeCommits() {
        List<CommitResponse> result = mapper.toCommitResponseList(Collections.emptyList());
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void debeMapearListaVaciaDeCommitFiles() {
        List<CommitFileResponse> result = mapper.toCommitFileResponseList(Collections.emptyList());
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
