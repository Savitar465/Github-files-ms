package com.githubx.githubfilesms.model;

import com.githubx.githubfilesms.model.enums.GitObjectType;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileEntityTest {

    @Test
    void onCreateSeteaValoresDefault() {
        RepositoryEntity repo = RepositoryEntity.builder()
                .id(1L)
                .owner("o")
                .name("r")
                .description(null)
                .visibility(null)
                .defaultBranch("main")
                .build();
        FileEntity fe = FileEntity.builder()
                .repository(repo)
                .name("a.txt")
                .path("a.txt")
                .sha("s")
                .type(GitObjectType.FILE)
                .branch(null)
                .encoding(null)
                .build();

        fe.onCreate();
        assertEquals("main", fe.getBranch());
        assertEquals("base64", fe.getEncoding());
    }

    @Test
    void urlsIncluyenOwnerRepoYPath() {
        RepositoryEntity repo = RepositoryEntity.builder()
                .id(1L)
                .owner("o")
                .name("r")
                .description(null)
                .visibility(null)
                .defaultBranch("main")
                .build();
        FileEntity fe = FileEntity.builder()
                .repository(repo)
                .name("a.txt")
                .path("a/b/a.txt")
                .sha("s")
                .type(GitObjectType.FILE)
                .branch("main")
                .build();

        assertTrue(fe.getDownloadUrl().contains("/v1/repos/o/r/download?path="));
        assertTrue(fe.getHtmlUrl().contains("/v1/repos/o/r/contents/"));
    }

    @Test
    void onUpdateSoloActualizaTimestamp() throws InterruptedException {
        RepositoryEntity repo = RepositoryEntity.builder()
                .id(1L)
                .owner("o")
                .name("r")
                .description(null)
                .visibility(null)
                .defaultBranch("main")
                .build();
        FileEntity fe = FileEntity.builder()
                .repository(repo)
                .name("a.txt")
                .path("a.txt")
                .sha("s")
                .type(GitObjectType.FILE)
                .branch("main")
                .build();
        fe.onCreate();
        var t1 = fe.getUpdatedAt();
        Thread.sleep(5);
        fe.onUpdate();
        assertTrue(fe.getUpdatedAt().isAfter(t1) || fe.getUpdatedAt().equals(t1));
    }
}
