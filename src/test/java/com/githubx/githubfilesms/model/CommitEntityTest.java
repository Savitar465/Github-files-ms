package com.githubx.githubfilesms.model;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommitEntityTest {

    @Test
    void onCreateSeteaFechasSiFaltan() {
        RepositoryEntity repo = RepositoryEntity.builder()
                .id(1L)
                .owner("o")
                .name("r")
                .description(null)
                .visibility(null)
                .defaultBranch("main")
                .build();

        CommitEntity c = CommitEntity.builder()
                .repository(repo)
                .sha("s")
                .message("m")
                .authorName("a")
                .authorEmail("a@a.com")
                .authorDate(null)
                .committerName("a")
                .committerEmail("a@a.com")
                .committerDate(null)
                .build();

        c.onCreate();
        assertNotNull(c.getAuthorDate());
        assertNotNull(c.getCommitterDate());
    }

    @Test
    void htmlUrlApuntaACommits() {
        RepositoryEntity repo = RepositoryEntity.builder()
                .id(1L)
                .owner("o")
                .name("r")
                .description(null)
                .visibility(null)
                .defaultBranch("main")
                .build();
        CommitEntity c = CommitEntity.builder()
                .repository(repo)
                .sha("abc")
                .message("m")
                .authorName("a")
                .authorEmail("a@a.com")
                .authorDate(Instant.parse("2026-01-01T00:00:00Z"))
                .committerName("a")
                .committerEmail("a@a.com")
                .committerDate(Instant.parse("2026-01-01T00:00:00Z"))
                .build();

        assertTrue(c.getHtmlUrl().contains("/v1/repos/o/r/commits/abc"));
    }
}
