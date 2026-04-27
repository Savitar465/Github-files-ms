package com.githubx.githubfilesms.service.implementacion;

import com.githubx.githubfilesms.dao.CommitDao;
import com.githubx.githubfilesms.dao.RepositoryDao;
import com.githubx.githubfilesms.dto.response.CommitFileResponse;
import com.githubx.githubfilesms.dto.response.CommitResponse;
import com.githubx.githubfilesms.dto.response.ListCommitsResponse;
import com.githubx.githubfilesms.mapper.CommitMapper;
import com.githubx.githubfilesms.model.CommitEntity;
import com.githubx.githubfilesms.model.CommitFileEntity;
import com.githubx.githubfilesms.model.RepositoryEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommitServiceTest {

    @Mock
    private CommitDao commitDao;
    @Mock
    private RepositoryDao repositoryDao;
    @Mock
    private CommitMapper commitMapper;

    @InjectMocks
    private CommitServiceImpl commitService;

    private RepositoryEntity repo() {
        return RepositoryEntity.builder()
                .id(1L)
                .owner("o")
                .name("r")
                .defaultBranch("main")
                .build();
    }

    @Test
    void debeListarPorPathYUsarQueryEspecifica() {
        when(repositoryDao.findByOwnerAndName("o", "r")).thenReturn(Optional.of(repo()));

        CommitEntity commit = CommitEntity.builder()
                .id(1L)
                .repository(repo())
                .sha("abc")
                .message("m")
                .authorName("a")
                .authorEmail("a@a.com")
                .authorDate(Instant.parse("2026-01-01T00:00:00Z"))
                .committerName("a")
                .committerEmail("a@a.com")
                .committerDate(Instant.parse("2026-01-01T00:00:00Z"))
                .parentSha(null)
                .branch("main")
                .build();

        Page<CommitEntity> page = new PageImpl<>(List.of(commit));
        when(commitDao.findByRepositoryAndFilePath(any(RepositoryEntity.class), eq("src/"), any(Pageable.class)))
                .thenReturn(page);

        when(commitMapper.toCommitResponseList(anyList()))
                .thenReturn(List.of(new CommitResponse("abc", "m", null, null, null, List.of())));

        ListCommitsResponse out = commitService.listCommits("o", "r", null, "src/", 0, 30);

        assertEquals(1, out.commits().size());
        assertEquals(1, out.pagination().total());
        verify(commitDao).findByRepositoryAndFilePath(any(RepositoryEntity.class), eq("src/"), any(Pageable.class));
    }

    @Test
    void debeRenderizarDiffBasico() {
        when(repositoryDao.findByOwnerAndName("o", "r")).thenReturn(Optional.of(repo()));

        CommitFileEntity f = CommitFileEntity.builder()
                .id(1L)
                .filename("README.md")
                .status("added")
                .patch("+line")
                .build();

        CommitEntity commit = CommitEntity.builder()
                .id(1L)
                .repository(repo())
                .sha("abc")
                .message("m")
                .authorName("a")
                .authorEmail("a@a.com")
                .authorDate(Instant.parse("2026-01-01T00:00:00Z"))
                .committerName("a")
                .committerEmail("a@a.com")
                .committerDate(Instant.parse("2026-01-01T00:00:00Z"))
                .build();
        f.setCommit(commit);
        commit.setFiles(List.of(f));

        when(commitDao.findByRepositoryAndSha(any(RepositoryEntity.class), eq("abc"))).thenReturn(Optional.of(commit));

        String diff = commitService.getCommitDiff("o", "r", "abc");
        assertTrue(diff.contains("commit abc"));
        assertTrue(diff.contains("diff --git a/README.md"));
        assertTrue(diff.contains("+line"));
    }

    @Test
    void debeObtenerCommitConArchivosMapeados() {
        when(repositoryDao.findByOwnerAndName("o", "r")).thenReturn(Optional.of(repo()));

        CommitFileEntity f = CommitFileEntity.builder()
                .id(1L)
                .filename("README.md")
                .status("added")
                .patch("p")
                .build();
        CommitEntity commit = CommitEntity.builder()
                .id(1L)
                .repository(repo())
                .sha("abc")
                .message("m")
                .authorName("a")
                .authorEmail("a@a.com")
                .authorDate(Instant.parse("2026-01-01T00:00:00Z"))
                .committerName("a")
                .committerEmail("a@a.com")
                .committerDate(Instant.parse("2026-01-01T00:00:00Z"))
                .build();
        f.setCommit(commit);
        commit.setFiles(List.of(f));

        when(commitDao.findByRepositoryAndSha(any(RepositoryEntity.class), eq("abc"))).thenReturn(Optional.of(commit));
        when(commitMapper.toCommitResponse(any(CommitEntity.class)))
                .thenReturn(new CommitResponse("abc", "m", null, null, null, List.of()));
        when(commitMapper.toCommitFileResponseList(anyList()))
                .thenReturn(List.of(new CommitFileResponse("README.md", "added", 1, 0, 1, "p")));

        var out = commitService.getCommit("o", "r", "abc");
        assertEquals("abc", out.commit().sha());
        assertEquals(1, out.files().size());
    }
}
