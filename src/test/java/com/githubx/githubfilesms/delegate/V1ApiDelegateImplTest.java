package com.githubx.githubfilesms.delegate;

import com.githubx.githubfilesms.dto.request.CreateFileRequest;
import com.githubx.githubfilesms.dto.request.CreateFolderRequest;
import com.githubx.githubfilesms.dto.request.UpdateFileRequest;
import com.githubx.githubfilesms.dto.response.*;
import com.githubx.githubfilesms.mapper.SmithyDtoMapper;
import com.githubx.githubfilesms.model.enums.GitObjectType;
import com.githubx.githubfilesms.service.contratos.CommitService;
import com.githubx.githubfilesms.service.contratos.FileService;
import com.smithy.g.files.server.files.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class V1ApiDelegateImplTest {

    @Mock
    private FileService fileService;

    @Mock
    private CommitService commitService;

    @Mock
    private SmithyDtoMapper smithyDtoMapper;

    @InjectMocks
    private V1ApiDelegateImpl v1ApiDelegate;

    private FileContentResponse fileContentResponse;
    private CommitSummaryResponse commitSummaryResponse;
    private CommitSignatureResponse authorSignature;
    private CommitSignatureResponse committerSignature;

    @BeforeEach
    void setUp() {
        authorSignature = new CommitSignatureResponse("Author", "author@test.com", "2026-01-01T00:00:00Z");
        committerSignature = new CommitSignatureResponse("Committer", "committer@test.com", "2026-01-01T00:00:00Z");

        fileContentResponse = new FileContentResponse(
                "README.md", "README.md", "abc123", GitObjectType.FILE,
                100L, "base64", "IyBIZWxsbw==", "/download/url", "/html/url", "commit123");

        commitSummaryResponse = new CommitSummaryResponse(
                "def456", "Initial commit", authorSignature, committerSignature, "/commits/def456");
    }

    @Test
    void debeObtenerContenidoDeArchivo() {
        GetFileContentBodyResponse serviceResponse = GetFileContentBodyResponse.ofFile(fileContentResponse);
        GetFileContentBody body = new GetFileContentBody();

        when(fileService.getFileContent("owner", "repo", "README.md", "main"))
                .thenReturn(serviceResponse);
        when(smithyDtoMapper.toGetFileContentBody(serviceResponse)).thenReturn(body);

        ResponseEntity<GetFileContentBody> result = v1ApiDelegate.getFileContent(
                "owner", "repo", "README.md", "main");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        verify(fileService).getFileContent("owner", "repo", "README.md", "main");
    }

    @Test
    void debeObtenerContenidosDeRepositorio() {
        List<DirectoryEntryResponse> entries = List.of(
                new DirectoryEntryResponse("file.txt", "file.txt", "sha1", GitObjectType.FILE, 50L, "/download"));
        GetFileContentBodyResponse serviceResponse = GetFileContentBodyResponse.ofDirectory(entries);
        GetFileContentBody body = new GetFileContentBody();

        when(fileService.getRepositoryContents("owner", "repo", "src", "main"))
                .thenReturn(serviceResponse);
        when(smithyDtoMapper.toGetFileContentBody(serviceResponse)).thenReturn(body);

        ResponseEntity<GetFileContentBody> result = v1ApiDelegate.getRepositoryContents(
                "owner", "repo", "src", "main");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(fileService).getRepositoryContents("owner", "repo", "src", "main");
    }

    @Test
    void debeObtenerArchivoRaw() {
        byte[] content = "Hello World".getBytes();
        when(fileService.getRawFile("owner", "repo", "src/main.java", "main"))
                .thenReturn(content);

        ResponseEntity<byte[]> result = v1ApiDelegate.getRawFile(
                "owner", "repo", "src/main.java", "main");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertArrayEquals(content, result.getBody());
        assertEquals(MediaType.APPLICATION_OCTET_STREAM, result.getHeaders().getContentType());
        assertTrue(result.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION).contains("main.java"));
    }

    @Test
    void debeObtenerArchivoRawSinPath() {
        byte[] content = "Content".getBytes();
        when(fileService.getRawFile("owner", "repo", "README.md", "main"))
                .thenReturn(content);

        ResponseEntity<byte[]> result = v1ApiDelegate.getRawFile(
                "owner", "repo", "README.md", "main");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertTrue(result.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION).contains("README.md"));
    }

    @Test
    void debeCrearArchivo() {
        CreateFileBody createBody = new CreateFileBody();
        createBody.setContent("SGVsbG8=".getBytes());
        createBody.setMessage("Add file");
        createBody.setBranch("main");

        CreateFileRequest request = new CreateFileRequest("SGVsbG8=".getBytes(), "Add file", "main", null, null);
        com.githubx.githubfilesms.dto.response.FileOperationResponse serviceResponse =
                new com.githubx.githubfilesms.dto.response.FileOperationResponse(fileContentResponse, commitSummaryResponse);
        com.smithy.g.files.server.files.model.FileOperationResponse smithyResponse =
                new com.smithy.g.files.server.files.model.FileOperationResponse();

        when(smithyDtoMapper.toCreateFileRequest(createBody)).thenReturn(request);
        when(fileService.createFile("owner", "repo", "newfile.txt", request)).thenReturn(serviceResponse);
        when(smithyDtoMapper.toFileOperationResponse(serviceResponse)).thenReturn(smithyResponse);

        ResponseEntity<com.smithy.g.files.server.files.model.FileOperationResponse> result =
                v1ApiDelegate.createFile("owner", "repo", "newfile.txt", createBody);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());
        verify(fileService).createFile("owner", "repo", "newfile.txt", request);
    }

    @Test
    void debeActualizarArchivo() {
        UpdateFileBody updateBody = new UpdateFileBody();
        updateBody.setContent("VXBkYXRlZA==".getBytes());
        updateBody.setMessage("Update file");
        updateBody.setSha("abc123");
        updateBody.setBranch("main");

        UpdateFileRequest request = new UpdateFileRequest("abc123", "VXBkYXRlZA==".getBytes(), "Update file", "main", null, null, null);
        com.githubx.githubfilesms.dto.response.FileOperationResponse serviceResponse =
                new com.githubx.githubfilesms.dto.response.FileOperationResponse(fileContentResponse, commitSummaryResponse);
        com.smithy.g.files.server.files.model.FileOperationResponse smithyResponse =
                new com.smithy.g.files.server.files.model.FileOperationResponse();

        when(smithyDtoMapper.toUpdateFileRequest(updateBody)).thenReturn(request);
        when(fileService.updateFile("owner", "repo", "file.txt", request)).thenReturn(serviceResponse);
        when(smithyDtoMapper.toFileOperationResponse(serviceResponse)).thenReturn(smithyResponse);

        ResponseEntity<com.smithy.g.files.server.files.model.FileOperationResponse> result =
                v1ApiDelegate.updateFile("owner", "repo", "file.txt", updateBody);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(fileService).updateFile("owner", "repo", "file.txt", request);
    }

    @Test
    void debeEliminarArchivo() {
        DeleteFileResponse serviceResponse = new DeleteFileResponse(commitSummaryResponse);
        DeleteFileResponseBody smithyResponse = new DeleteFileResponseBody();

        when(fileService.deleteFile("owner", "repo", "file.txt", "sha123", "Delete file", "main"))
                .thenReturn(serviceResponse);
        when(smithyDtoMapper.toDeleteFileResponseBody(serviceResponse)).thenReturn(smithyResponse);

        ResponseEntity<DeleteFileResponseBody> result = v1ApiDelegate.deleteFile(
                "owner", "repo", "file.txt", "sha123", "Delete file", "main");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(fileService).deleteFile("owner", "repo", "file.txt", "sha123", "Delete file", "main");
    }

    @Test
    void debeCrearCarpeta() {
        CreateFolderBody createBody = new CreateFolderBody();
        createBody.setPath("src/new-folder");
        createBody.setMessage("Create folder");
        createBody.setBranch("main");

        CreateFolderRequest request = new CreateFolderRequest("src/new-folder", "Create folder", "main");
        com.githubx.githubfilesms.dto.response.FileOperationResponse serviceResponse =
                new com.githubx.githubfilesms.dto.response.FileOperationResponse(fileContentResponse, commitSummaryResponse);
        com.smithy.g.files.server.files.model.FileOperationResponse smithyResponse =
                new com.smithy.g.files.server.files.model.FileOperationResponse();

        when(smithyDtoMapper.toCreateFolderRequest(createBody)).thenReturn(request);
        when(fileService.createFolder("owner", "repo", request)).thenReturn(serviceResponse);
        when(smithyDtoMapper.toFileOperationResponse(serviceResponse)).thenReturn(smithyResponse);

        ResponseEntity<com.smithy.g.files.server.files.model.FileOperationResponse> result =
                v1ApiDelegate.createFolder("owner", "repo", createBody);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        verify(fileService).createFolder("owner", "repo", request);
    }

    @Test
    void debeListarCommits() {
        ListCommitsResponse serviceResponse = new ListCommitsResponse(
                Collections.emptyList(),
                new com.githubx.githubfilesms.dto.response.PaginationMeta(0, 30, 0L, 0));
        ListCommitsBody smithyResponse = new ListCommitsBody();

        when(commitService.listCommits("owner", "repo", "main", null, 1, 30))
                .thenReturn(serviceResponse);
        when(smithyDtoMapper.toListCommitsBody(serviceResponse)).thenReturn(smithyResponse);

        ResponseEntity<ListCommitsBody> result = v1ApiDelegate.listCommits(
                "owner", "repo", "main", null, BigDecimal.ONE, BigDecimal.valueOf(30));

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(commitService).listCommits("owner", "repo", "main", null, 1, 30);
    }

    @Test
    void debeListarCommitsConValoresPorDefecto() {
        ListCommitsResponse serviceResponse = new ListCommitsResponse(
                Collections.emptyList(),
                new com.githubx.githubfilesms.dto.response.PaginationMeta(0, 30, 0L, 0));
        ListCommitsBody smithyResponse = new ListCommitsBody();

        when(commitService.listCommits("owner", "repo", null, null, 0, 30))
                .thenReturn(serviceResponse);
        when(smithyDtoMapper.toListCommitsBody(serviceResponse)).thenReturn(smithyResponse);

        ResponseEntity<ListCommitsBody> result = v1ApiDelegate.listCommits(
                "owner", "repo", null, null, null, null);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(commitService).listCommits("owner", "repo", null, null, 0, 30);
    }

    @Test
    void debeObtenerCommit() {
        CommitResponse commitResponse = new CommitResponse(
                "sha123", "Commit message", authorSignature, committerSignature,
                "/commits/sha123", Collections.emptyList());
        GetCommitResponse serviceResponse = new GetCommitResponse(commitResponse, Collections.emptyList());
        GetCommitBody smithyResponse = new GetCommitBody();

        when(commitService.getCommit("owner", "repo", "sha123")).thenReturn(serviceResponse);
        when(smithyDtoMapper.toGetCommitBody(serviceResponse)).thenReturn(smithyResponse);

        ResponseEntity<GetCommitBody> result = v1ApiDelegate.getCommit("owner", "repo", "sha123");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(commitService).getCommit("owner", "repo", "sha123");
    }

    @Test
    void debeObtenerDiffDeCommit() {
        String diff = "diff --git a/file.txt b/file.txt\n+new line";
        when(commitService.getCommitDiff("owner", "repo", "sha123")).thenReturn(diff);

        ResponseEntity<String> result = v1ApiDelegate.getCommitDiff("owner", "repo", "sha123");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(diff, result.getBody());
        assertEquals(MediaType.TEXT_PLAIN, result.getHeaders().getContentType());
    }

    @Test
    void debeCompararCommits() {
        CompareResponse serviceResponse = new CompareResponse(
                Collections.emptyList(), 5, Collections.emptyList(), 3, 2);
        CompareDTO smithyResponse = new CompareDTO();

        when(commitService.compareCommits("owner", "repo", "main", "feature"))
                .thenReturn(serviceResponse);
        when(smithyDtoMapper.toCompareDTO(serviceResponse)).thenReturn(smithyResponse);

        ResponseEntity<CompareDTO> result = v1ApiDelegate.compareCommits(
                "owner", "repo", "main", "feature");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(commitService).compareCommits("owner", "repo", "main", "feature");
    }
}
