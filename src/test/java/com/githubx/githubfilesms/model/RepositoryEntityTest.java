package com.githubx.githubfilesms.model;

import com.githubx.githubfilesms.model.enums.RepoVisibility;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RepositoryEntityTest {

    @Test
    void onCreateSeteaDefaults() {
        RepositoryEntity e = RepositoryEntity.builder()
                .owner("o")
                .name("r")
                .description(null)
                .visibility(null)
                .defaultBranch(null)
                .build();
        e.onCreate();
        assertEquals("main", e.getDefaultBranch());
        assertEquals(RepoVisibility.PRIVATE, e.getVisibility());
        assertNotNull(e.getCreatedAt());
    }
}
