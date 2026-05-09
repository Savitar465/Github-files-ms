package com.githubx.githubfilesms.delegate;

import com.githubx.githubfilesms.dto.request.CreateFileRequest;
import com.githubx.githubfilesms.dto.request.CreateFolderRequest;
import com.githubx.githubfilesms.dto.request.UpdateFileRequest;
import com.githubx.githubfilesms.dto.response.CompareResponse;
import com.githubx.githubfilesms.dto.response.DeleteFileResponse;
import com.githubx.githubfilesms.dto.response.GetCommitResponse;
import com.githubx.githubfilesms.dto.response.GetFileContentBodyResponse;
import com.githubx.githubfilesms.dto.response.ListCommitsResponse;
import com.githubx.githubfilesms.mapper.SmithyDtoMapper;
import com.githubx.githubfilesms.service.contratos.CommitService;
import com.githubx.githubfilesms.service.contratos.FileService;
import com.smithy.g.files.server.files.api.V1ApiDelegate;
import com.smithy.g.files.server.files.model.CompareDTO;
import com.smithy.g.files.server.files.model.CreateFileBody;
import com.smithy.g.files.server.files.model.CreateFolderBody;
import com.smithy.g.files.server.files.model.DeleteFileResponseBody;
import com.smithy.g.files.server.files.model.GetCommitBody;
import com.smithy.g.files.server.files.model.GetFileContentBody;
import com.smithy.g.files.server.files.model.ListCommitsBody;
import com.smithy.g.files.server.files.model.UpdateFileBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class V1ApiDelegateImpl implements V1ApiDelegate {

    private final FileService fileService;
    private final CommitService commitService;
    private final SmithyDtoMapper smithyDtoMapper;

    @Override
    public ResponseEntity<GetFileContentBody> getFileContent(
            String owner,
            String repo,
            String filePathPlus,
            String ref) {
        GetFileContentBodyResponse response = fileService.getFileContent(owner, repo, filePathPlus, ref);
        return ResponseEntity.ok(smithyDtoMapper.toGetFileContentBody(response));
    }

    @Override
    public ResponseEntity<GetFileContentBody> getRepositoryContents(
            String owner,
            String repo,
            String path,
            String ref) {
        GetFileContentBodyResponse response = fileService.getRepositoryContents(owner, repo, path, ref);
        return ResponseEntity.ok(smithyDtoMapper.toGetFileContentBody(response));
    }

    @Override
    public ResponseEntity<byte[]> getRawFile(
            String owner,
            String repo,
            String path,
            String ref) {
        byte[] content = fileService.getRawFile(owner, repo, path, ref);
        String fileName = path.contains("/") ? path.substring(path.lastIndexOf('/') + 1) : path;
        String contentDisposition = "attachment; filename=\"" + fileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(content);
    }

    @Override
    public ResponseEntity<com.smithy.g.files.server.files.model.FileOperationResponse> createFile(
            String owner,
            String repo,
            String filePathPlus,
            CreateFileBody createFileBody) {
        CreateFileRequest request = smithyDtoMapper.toCreateFileRequest(createFileBody);
        com.githubx.githubfilesms.dto.response.FileOperationResponse response =
                fileService.createFile(owner, repo, filePathPlus, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(smithyDtoMapper.toFileOperationResponse(response));
    }

    @Override
    public ResponseEntity<com.smithy.g.files.server.files.model.FileOperationResponse> updateFile(
            String owner,
            String repo,
            String filePathPlus,
            UpdateFileBody updateFileBody) {
        UpdateFileRequest request = smithyDtoMapper.toUpdateFileRequest(updateFileBody);
        com.githubx.githubfilesms.dto.response.FileOperationResponse response =
                fileService.updateFile(owner, repo, filePathPlus, request);
        return ResponseEntity.ok(smithyDtoMapper.toFileOperationResponse(response));
    }

    @Override
    public ResponseEntity<DeleteFileResponseBody> deleteFile(
            String owner,
            String repo,
            String filePathPlus,
            String sha,
            String message,
            String branch) {
        DeleteFileResponse response = fileService.deleteFile(owner, repo, filePathPlus, sha, message, branch);
        return ResponseEntity.ok(smithyDtoMapper.toDeleteFileResponseBody(response));
    }

    @Override
    public ResponseEntity<com.smithy.g.files.server.files.model.FileOperationResponse> createFolder(
            String owner,
            String repo,
            CreateFolderBody createFolderBody) {
        CreateFolderRequest request = smithyDtoMapper.toCreateFolderRequest(createFolderBody);
        com.githubx.githubfilesms.dto.response.FileOperationResponse response =
                fileService.createFolder(owner, repo, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(smithyDtoMapper.toFileOperationResponse(response));
    }

    @Override
    public ResponseEntity<ListCommitsBody> listCommits(
            String owner,
            String repo,
            String sha,
            String path,
            BigDecimal page,
            BigDecimal perPage) {
        int pageInt = page != null ? page.intValue() : 0;
        int perPageInt = perPage != null ? perPage.intValue() : 30;
        ListCommitsResponse response = commitService.listCommits(owner, repo, sha, path, pageInt, perPageInt);
        return ResponseEntity.ok(smithyDtoMapper.toListCommitsBody(response));
    }

    @Override
    public ResponseEntity<GetCommitBody> getCommit(
            String owner,
            String repo,
            String sha) {
        GetCommitResponse response = commitService.getCommit(owner, repo, sha);
        return ResponseEntity.ok(smithyDtoMapper.toGetCommitBody(response));
    }

    @Override
    public ResponseEntity<String> getCommitDiff(
            String owner,
            String repo,
            String sha) {
        String diff = commitService.getCommitDiff(owner, repo, sha);
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(diff);
    }

    @Override
    public ResponseEntity<CompareDTO> compareCommits(
            String owner,
            String repo,
            String baseBranch,
            String headBranch) {
        CompareResponse response = commitService.compareCommits(owner, repo, baseBranch, headBranch);
        return ResponseEntity.ok(smithyDtoMapper.toCompareDTO(response));
    }
}
