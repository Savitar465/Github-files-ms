package com.githubx.githubfilesms.controller;

import com.githubx.githubfilesms.dto.response.*;
import com.githubx.githubfilesms.service.contratos.CommitService;
import com.githubx.githubfilesms.util.errorhandling.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CommitControllerTest {

    @Mock
    private CommitService commitService;

    @InjectMocks
    private CommitController commitController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(commitController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void debeListarCommits() throws Exception {
        when(commitService.listCommits("o", "r", "sha1", "path1", 0, 30))
                .thenReturn(new ListCommitsResponse(
                        List.of(new CommitResponse("abc", "m",
                                new CommitSignatureResponse("a", "a@a.com", "now"),
                                new CommitSignatureResponse("a", "a@a.com", "now"),
                                null, List.of())),
                        PaginationMeta.of(0, 30, 1)
                ));

        mockMvc.perform(get("/v1/repos/o/r/commits")
                        .param("sha", "sha1")
                        .param("path", "path1")
                        .param("page", "0")
                        .param("perPage", "30"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.commits[0].sha").value("abc"))
                .andExpect(jsonPath("$.pagination.total").value(1));

        verify(commitService).listCommits("o", "r", "sha1", "path1", 0, 30);
    }

    @Test
    void debeObtenerCommit() throws Exception {
        when(commitService.getCommit("o", "r", "abc"))
                .thenReturn(new GetCommitResponse(
                        new CommitResponse("abc", "m",
                                new CommitSignatureResponse("a", "a@a.com", "now"),
                                new CommitSignatureResponse("a", "a@a.com", "now"),
                                null, List.of()),
                        List.of(new CommitFileResponse("f.txt", "added", 1, 0, 1, "patch"))
                ));

        mockMvc.perform(get("/v1/repos/o/r/commits/abc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.commit.sha").value("abc"))
                .andExpect(jsonPath("$.files[0].filename").value("f.txt"));
    }

    @Test
    void debeObtenerDiff() throws Exception {
        when(commitService.getCommitDiff("o", "r", "abc"))
                .thenReturn("diff --git a/x b/x\n");

        mockMvc.perform(get("/v1/repos/o/r/commits/abc/diff"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    if (!result.getResponse().getContentAsString().contains("diff --git")) {
                        throw new AssertionError("Se esperaba texto diff");
                    }
                });
    }

    @Test
    void debeComparar() throws Exception {
        when(commitService.compareCommits("o", "r", "main", "feature"))
                .thenReturn(new CompareResponse(List.of(), 0, List.of(), 1, 1));

        mockMvc.perform(get("/v1/repos/o/r/compare/main/feature"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.aheadBy").value(1));
    }
}
