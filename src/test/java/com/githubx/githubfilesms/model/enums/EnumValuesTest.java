package com.githubx.githubfilesms.model.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnumValuesTest {

    @Test
    void gitObjectTypeFromValue() {
        assertEquals(GitObjectType.FILE, GitObjectType.fromValue("file"));
        assertEquals(GitObjectType.DIRECTORY, GitObjectType.fromValue("dir"));
        assertThrows(IllegalArgumentException.class, () -> GitObjectType.fromValue("nope"));
    }

    @Test
    void repoVisibilityFromValue() {
        assertEquals(RepoVisibility.PUBLIC, RepoVisibility.fromValue("public"));
        assertEquals(RepoVisibility.PRIVATE, RepoVisibility.fromValue("PRIVATE"));
        assertThrows(IllegalArgumentException.class, () -> RepoVisibility.fromValue("nope"));
    }
}
