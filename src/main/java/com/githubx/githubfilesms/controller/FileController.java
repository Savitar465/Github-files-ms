package com.githubx.githubfilesms.controller;

import com.githubx.githubfilesms.dto.request.CreateFileRequest;
import com.githubx.githubfilesms.dto.request.CreateFolderRequest;
import com.githubx.githubfilesms.dto.request.UpdateFileRequest;
import com.githubx.githubfilesms.dto.response.DeleteFileResponse;
import com.githubx.githubfilesms.dto.response.FileOperationResponse;
import com.githubx.githubfilesms.dto.response.GetFileContentBodyResponse;
import com.githubx.githubfilesms.service.contratos.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/repos/{owner}/{repo}")
@RequiredArgsConstructor
@Tag(name = "Files", description = "Operaciones de archivos del repositorio")
public class FileController {

    private final FileService fileService;

    @GetMapping("/contents/**")
    @Operation(summary = "Obtener contenido de archivo o directorio")
    public ResponseEntity<GetFileContentBodyResponse> getFileContent(
            @PathVariable String owner,
            @PathVariable String repo,
            @Parameter(description = "Branch, tag o commit SHA") @RequestParam(required = false) String ref,
            HttpServletRequest request) {

        String filePath = extractFilePath(request, "/contents/");
        GetFileContentBodyResponse response = fileService.getFileContent(owner, repo, filePath, ref);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/contents")
    @Operation(summary = "Listar contenido del repositorio")
    public ResponseEntity<GetFileContentBodyResponse> getRepositoryContents(
            @PathVariable String owner,
            @PathVariable String repo,
            @Parameter(description = "Ruta relativa") @RequestParam(required = false) String path,
            @Parameter(description = "Branch, tag o commit SHA") @RequestParam(required = false) String ref) {

        GetFileContentBodyResponse response = fileService.getRepositoryContents(owner, repo, path, ref);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/contents/**")
    @Operation(summary = "Crear archivo")
    public ResponseEntity<FileOperationResponse> createFile(
            @PathVariable String owner,
            @PathVariable String repo,
            @Valid @RequestBody CreateFileRequest request,
            HttpServletRequest httpRequest) {

        String filePath = extractFilePath(httpRequest, "/contents/");
        FileOperationResponse response = fileService.createFile(owner, repo, filePath, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/contents/**")
    @Operation(summary = "Actualizar archivo")
    public ResponseEntity<FileOperationResponse> updateFile(
            @PathVariable String owner,
            @PathVariable String repo,
            @Valid @RequestBody UpdateFileRequest request,
            HttpServletRequest httpRequest) {

        String filePath = extractFilePath(httpRequest, "/contents/");
        FileOperationResponse response = fileService.updateFile(owner, repo, filePath, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/contents/**")
    @Operation(summary = "Eliminar archivo")
    public ResponseEntity<DeleteFileResponse> deleteFile(
            @PathVariable String owner,
            @PathVariable String repo,
            @Parameter(description = "SHA del archivo", required = true) @RequestParam String sha,
            @Parameter(description = "Mensaje del commit", required = true) @RequestParam String message,
            @Parameter(description = "Branch") @RequestParam(required = false) String branch,
            HttpServletRequest request) {

        String filePath = extractFilePath(request, "/contents/");
        DeleteFileResponse response = fileService.deleteFile(owner, repo, filePath, sha, message, branch);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/folders")
    @Operation(summary = "Crear carpeta")
    public ResponseEntity<FileOperationResponse> createFolder(
            @PathVariable String owner,
            @PathVariable String repo,
            @Valid @RequestBody CreateFolderRequest request) {

        FileOperationResponse response = fileService.createFolder(owner, repo, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/download")
    @Operation(summary = "Descargar archivo raw")
    public ResponseEntity<byte[]> getRawFile(
            @PathVariable String owner,
            @PathVariable String repo,
            @Parameter(description = "Ruta del archivo", required = true) @RequestParam String path,
            @Parameter(description = "Branch, tag o commit SHA") @RequestParam(required = false) String ref) {

        byte[] content = fileService.getRawFile(owner, repo, path, ref);

        String fileName = path.contains("/") ? path.substring(path.lastIndexOf('/') + 1) : path;
        String contentDisposition = "attachment; filename=\"" + fileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(content);
    }

    private String extractFilePath(HttpServletRequest request, String prefix) {
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String fullPath = uri.substring(contextPath.length());

        int index = fullPath.indexOf(prefix);
        if (index >= 0) {
            return fullPath.substring(index + prefix.length());
        }
        return "";
    }
}
