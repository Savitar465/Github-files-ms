package com.smithy.g.files.server.files.api;

import com.smithy.g.files.server.files.model.CreateFileBody;
import com.smithy.g.files.server.files.model.DeleteFileResponseBody;
import com.smithy.g.files.server.files.model.FileOperationResponse;
import com.smithy.g.files.server.files.model.GetFileContentBody;
import com.smithy.g.files.server.files.model.UpdateFileBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Optional;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.5.0")
@Controller
@RequestMapping("${openapi.miniGitHubFiles.base-path:}")
public class V1ApiController implements V1Api {

    private final V1ApiDelegate delegate;

    public V1ApiController(@Autowired(required = false) V1ApiDelegate delegate) {
        this.delegate = Optional.ofNullable(delegate).orElse(new V1ApiDelegate() {});
    }

    @Override
    public V1ApiDelegate getDelegate() {
        return delegate;
    }

    // Custom implementations for methods with greedy path (filePath+) to handle path extraction properly

    @GetMapping(value = "/v1/repos/{owner}/{repo}/contents/**", produces = "application/json")
    public ResponseEntity<GetFileContentBody> getFileContentWithPath(
            @Pattern(regexp = "^[a-zA-Z0-9_-]+$") @Size(min = 3, max = 50) @PathVariable("owner") String owner,
            @Pattern(regexp = "^[a-zA-Z0-9._-]+$") @Size(min = 1, max = 150) @PathVariable("repo") String repo,
            @RequestParam(value = "ref", required = false) String ref,
            HttpServletRequest request
    ) {
        String filePath = extractFilePath(request, owner, repo);
        return getDelegate().getFileContent(owner, repo, filePath, ref);
    }

    @PutMapping(value = "/v1/repos/{owner}/{repo}/contents/**", produces = "application/json", consumes = "application/json")
    public ResponseEntity<FileOperationResponse> createFileWithPath(
            @Pattern(regexp = "^[a-zA-Z0-9_-]+$") @Size(min = 3, max = 50) @PathVariable("owner") String owner,
            @Pattern(regexp = "^[a-zA-Z0-9._-]+$") @Size(min = 1, max = 150) @PathVariable("repo") String repo,
            @Valid @RequestBody CreateFileBody createFileBody,
            HttpServletRequest request
    ) {
        String filePath = extractFilePath(request, owner, repo);
        return getDelegate().createFile(owner, repo, filePath, createFileBody);
    }

    @PatchMapping(value = "/v1/repos/{owner}/{repo}/contents/**", produces = "application/json", consumes = "application/json")
    public ResponseEntity<FileOperationResponse> updateFileWithPath(
            @Pattern(regexp = "^[a-zA-Z0-9_-]+$") @Size(min = 3, max = 50) @PathVariable("owner") String owner,
            @Pattern(regexp = "^[a-zA-Z0-9._-]+$") @Size(min = 1, max = 150) @PathVariable("repo") String repo,
            @Valid @RequestBody UpdateFileBody updateFileBody,
            HttpServletRequest request
    ) {
        String filePath = extractFilePath(request, owner, repo);
        return getDelegate().updateFile(owner, repo, filePath, updateFileBody);
    }

    @DeleteMapping(value = "/v1/repos/{owner}/{repo}/contents/**", produces = "application/json")
    public ResponseEntity<DeleteFileResponseBody> deleteFileWithPath(
            @Pattern(regexp = "^[a-zA-Z0-9_-]+$") @Size(min = 3, max = 50) @PathVariable("owner") String owner,
            @Pattern(regexp = "^[a-zA-Z0-9._-]+$") @Size(min = 1, max = 150) @PathVariable("repo") String repo,
            @NotNull @RequestParam("sha") String sha,
            @NotNull @Size(min = 1, max = 500) @RequestParam("message") String message,
            @RequestParam(value = "branch", required = false) String branch,
            HttpServletRequest request
    ) {
        String filePath = extractFilePath(request, owner, repo);
        return getDelegate().deleteFile(owner, repo, filePath, sha, message, branch);
    }

    private String extractFilePath(HttpServletRequest request, String owner, String repo) {
        String fullPath = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        if (fullPath == null) {
            fullPath = request.getRequestURI();
        }
        String prefix = "/v1/repos/" + owner + "/" + repo + "/contents/";
        if (fullPath.startsWith(prefix)) {
            return fullPath.substring(prefix.length());
        }
        return fullPath;
    }
}
