package com.githubx.githubfilesms.controller;

import com.githubx.githubfilesms.dto.response.CompareResponse;
import com.githubx.githubfilesms.dto.response.GetCommitResponse;
import com.githubx.githubfilesms.dto.response.ListCommitsResponse;
import com.githubx.githubfilesms.service.contratos.CommitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/repos/{owner}/{repo}")
@RequiredArgsConstructor
@Tag(name = "Commits", description = "Operaciones de commits del repositorio")
public class CommitController {

    private final CommitService commitService;

    @GetMapping("/commits")
    @Operation(summary = "Listar historial de commits")
    public ResponseEntity<ListCommitsResponse> listCommits(
            @PathVariable String owner,
            @PathVariable String repo,
            @Parameter(description = "Branch o SHA desde donde empezar") @RequestParam(required = false) String sha,
            @Parameter(description = "Filtrar por path de archivo") @RequestParam(required = false) String path,
            @Parameter(description = "Numero de pagina") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Elementos por pagina") @RequestParam(defaultValue = "30") int perPage) {

        ListCommitsResponse response = commitService.listCommits(owner, repo, sha, path, page, perPage);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/commits/{sha}")
    @Operation(summary = "Obtener detalle de un commit")
    public ResponseEntity<GetCommitResponse> getCommit(
            @PathVariable String owner,
            @PathVariable String repo,
            @PathVariable String sha) {

        GetCommitResponse response = commitService.getCommit(owner, repo, sha);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/commits/{sha}/diff", produces = MediaType.TEXT_PLAIN_VALUE)
    @Operation(summary = "Obtener diff de un commit")
    public ResponseEntity<String> getCommitDiff(
            @PathVariable String owner,
            @PathVariable String repo,
            @PathVariable String sha) {

        String diff = commitService.getCommitDiff(owner, repo, sha);
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(diff);
    }

    @GetMapping("/compare/{baseBranch}/{headBranch}")
    @Operation(summary = "Comparar dos branches")
    public ResponseEntity<CompareResponse> compareCommits(
            @PathVariable String owner,
            @PathVariable String repo,
            @PathVariable String baseBranch,
            @PathVariable String headBranch) {

        CompareResponse response = commitService.compareCommits(owner, repo, baseBranch, headBranch);
        return ResponseEntity.ok(response);
    }
}
