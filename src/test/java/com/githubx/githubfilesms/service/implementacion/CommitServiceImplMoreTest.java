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
class CommitServiceImplMoreTest {

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
    void debeListarCommitsPorRama() {
        when(repositoryDao.findByOwnerAndName("o", "r")).thenReturn(Optional.of(repo()));
        when(commitDao.findByRepositoryAndBranchOrderByCommitterDateDesc(any(RepositoryEntity.class), eq("feature"), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(
                        CommitEntity.builder()
                                .id(1L)
                                .repository(repo())
                                .sha("a")
                                .message("m")
                                .authorName("a")
                                .authorEmail("a@a.com")
                                .authorDate(Instant.parse("2026-01-01T00:00:00Z"))
                                .committerName("a")
                                .committerEmail("a@a.com")
                                .committerDate(Instant.parse("2026-01-01T00:00:00Z"))
                                .build()
                )));
        when(commitMapper.toCommitResponseList(anyList()))
                .thenReturn(List.of(new CommitResponse("a", "m", null, null, null, List.of())));

        ListCommitsResponse out = commitService.listCommits("o", "r", "feature", null, 0, 20);
        assertEquals(1, out.commits().size());
    }

    @Test
    void debeListarCommitsSinFiltros() {
        when(repositoryDao.findByOwnerAndName("o", "r")).thenReturn(Optional.of(repo()));
        when(commitDao.findByRepositoryOrderByCommitterDateDesc(any(RepositoryEntity.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(
                        CommitEntity.builder()
                                .id(1L)
                                .repository(repo())
                                .sha("a")
                                .message("m")
                                .authorName("a")
                                .authorEmail("a@a.com")
                                .authorDate(Instant.parse("2026-01-01T00:00:00Z"))
                                .committerName("a")
                                .committerEmail("a@a.com")
                                .committerDate(Instant.parse("2026-01-01T00:00:00Z"))
                                .build()
                )));
        when(commitMapper.toCommitResponseList(anyList()))
                .thenReturn(List.of(new CommitResponse("a", "m", null, null, null, List.of())));

        ListCommitsResponse out = commitService.listCommits("o", "r", null, null, 0, 20);
        assertEquals(1, out.commits().size());
    }

    @Test
    void debeCompararBranches() {
        when(repositoryDao.findByOwnerAndName("o", "r")).thenReturn(Optional.of(repo()));

        CommitEntity c1 = CommitEntity.builder()
                .id(1L)
                .repository(repo())
                .sha("h1")
                .message("m")
                .authorName("a")
                .authorEmail("a@a.com")
                .authorDate(Instant.parse("2026-01-01T00:00:00Z"))
                .committerName("a")
                .committerEmail("a@a.com")
                .committerDate(Instant.parse("2026-01-01T00:00:00Z"))
                .branch("feature")
                .build();
        c1.setFiles(List.of(CommitFileEntity.builder()
                .id(1L)
                .commit(c1)
                .filename("f.txt")
                .status("added")
                .build()));

        when(commitDao.findByRepositoryAndBranchOrderByDateDesc(any(RepositoryEntity.class), eq("feature")))
                .thenReturn(List.of(c1));
        when(commitDao.findByRepositoryAndBranchOrderByDateDesc(any(RepositoryEntity.class), eq("main")))
                .thenReturn(List.of());
        when(commitMapper.toCommitResponseList(anyList()))
                .thenReturn(List.of(new CommitResponse("h1", "m", null, null, null, List.of())));
        when(commitMapper.toCommitFileResponseList(anyList()))
                .thenReturn(List.of(new CommitFileResponse("f.txt", "added", 1, 0, 1, "p")));
        when(commitDao.countAheadBy(any(RepositoryEntity.class), eq("main"), eq("feature"))).thenReturn(1);
        when(commitDao.countBehindBy(any(RepositoryEntity.class), eq("main"), eq("feature"))).thenReturn(0);

        var out = commitService.compareCommits("o", "r", "main", "feature");
        assertEquals(1, out.commits().size());
        assertEquals(1, out.files().size());
        assertEquals(1, out.aheadBy());
        assertEquals(0, out.behindBy());
    }

    @Test
    void debeObtenerCommitConArchivos() {
        when(repositoryDao.findByOwnerAndName("o", "r")).thenReturn(Optional.of(repo()));

        CommitFileEntity f = CommitFileEntity.builder()
                .id(1L)
                .filename("a.txt")
                .status("added")
                .patch("p")
                .build();
        CommitEntity c = CommitEntity.builder()
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
        f.setCommit(c);
        c.setFiles(List.of(f));
        c.setParentSha("p0");

        when(commitDao.findByRepositoryAndSha(any(RepositoryEntity.class), eq("abc"))).thenReturn(Optional.of(c));
        when(commitMapper.toCommitResponse(any(CommitEntity.class)))
                .thenReturn(new CommitResponse("abc", "m", null, null, null, List.of(
                        // parents will be provided by CommitMapper, pero aqui forzamos respuesta simple
                )));
        when(commitMapper.toCommitFileResponseList(anyList()))
                .thenReturn(List.of(new CommitFileResponse("a.txt", "added", 1, 0, 1, "p")));

        var out = commitService.getCommit("o", "r", "abc");
        assertEquals("abc", out.commit().sha());
        assertEquals(1, out.files().size());
    }

    @Test
    void diffContieneBloques() {
        when(repositoryDao.findByOwnerAndName("o", "r")).thenReturn(Optional.of(repo()));
        CommitFileEntity f = CommitFileEntity.builder()
                .id(1L)
                .filename("a.txt")
                .status("added")
                .patch("+++")
                .build();
        CommitEntity c = CommitEntity.builder()
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
        f.setCommit(c);
        c.setFiles(List.of(f));
        when(commitDao.findByRepositoryAndSha(any(RepositoryEntity.class), eq("abc"))).thenReturn(Optional.of(c));

        String diff = commitService.getCommitDiff("o", "r", "abc");
        assertTrue(diff.contains("commit abc"));
        assertTrue(diff.contains("diff --git"));
    }
}
