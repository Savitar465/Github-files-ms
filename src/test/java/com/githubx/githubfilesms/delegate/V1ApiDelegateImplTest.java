package com.githubx.githubfilesms.delegate;

import com.githubx.githubfilesms.dto.request.CreateFileRequest;
import com.githubx.githubfilesms.dto.request.CreateFolderRequest;
import com.githubx.githubfilesms.dto.request.UpdateFileRequest;
import com.githubx.githubfilesms.dto.response.CommitFileResponse;
import com.githubx.githubfilesms.dto.response.CommitParentResponse;
import com.githubx.githubfilesms.dto.response.CommitResponse;
import com.githubx.githubfilesms.dto.response.CommitSignatureResponse;
import com.githubx.githubfilesms.dto.response.CommitSummaryResponse;
import com.githubx.githubfilesms.dto.response.CompareResponse;
import com.githubx.githubfilesms.dto.response.DeleteFileResponse;
import com.githubx.githubfilesms.dto.response.FileContentResponse;
import com.githubx.githubfilesms.dto.response.GetCommitResponse;
import com.githubx.githubfilesms.dto.response.GetFileContentBodyResponse;
import com.githubx.githubfilesms.dto.response.ListCommitsResponse;
import com.githubx.githubfilesms.dto.response.PaginationMeta;
import com.githubx.githubfilesms.mapper.SmithyDtoMapper;
import com.githubx.githubfilesms.service.contratos.CommitService;
import com.githubx.githubfilesms.service.contratos.FileService;
import com.smithy.g.files.server.files.model.CompareDTO;
import com.smithy.g.files.server.files.model.CreateFileBody;
import com.smithy.g.files.server.files.model.CreateFolderBody;
import com.smithy.g.files.server.files.model.DeleteFileResponseBody;
import com.smithy.g.files.server.files.model.GetCommitBody;
import com.smithy.g.files.server.files.model.GetFileContentBody;
import com.smithy.g.files.server.files.model.ListCommitsBody;
import com.smithy.g.files.server.files.model.UpdateFileBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class V1ApiDelegateImplTest {

    @Mock
    private FileService fileService;

    @Mock
    private CommitService commitService;

    @Mock
    private SmithyDtoMapper smithyDtoMapper;

    private V1ApiDelegateImpl delegate;

    @BeforeEach
    void setUp() {
        delegate = new V1ApiDelegateImpl(fileService, commitService, smithyDtoMapper);
    }

    @Test
    void debeObtenerContenidoArchivo() {
        GetFileContentBodyResponse serviceResponse = GetFileContentBodyResponse.ofFile(
                new FileContentResponse("c.txt", "a/b/c.txt", "sha1", null, 1L, "base64", "YQ==",
                        null, null, "lcsha"));
        GetFileContentBody mappedResponse = new GetFileContentBody();

        when(fileService.getFileContent("owner", "repo", "a/b/c.txt", null))
                .thenReturn(serviceResponse);
        when(smithyDtoMapper.toGetFileContentBody(serviceResponse))
                .thenReturn(mappedResponse);

        ResponseEntity<GetFileContentBody> result = delegate.getFileContent("owner", "repo", "a/b/c.txt", null);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(mappedResponse);
        verify(fileService).getFileContent("owner", "repo", "a/b/c.txt", null);
    }

    @Test
    void debeCrearArchivo() {
        CreateFileBody createFileBody = new CreateFileBody("hola".getBytes(), "msg");
        createFileBody.setBranch("main");
        CreateFileRequest internalRequest = new CreateFileRequest("hola".getBytes(), "msg", "main", null, null);
        com.githubx.githubfilesms.dto.response.FileOperationResponse serviceResponse =
                new com.githubx.githubfilesms.dto.response.FileOperationResponse(
                        new FileContentResponse("p.txt", "p.txt", "sha1", null, 4L, "base64", "aQ==", null, null, "c"),
                        new CommitSummaryResponse("c", "msg",
                                new CommitSignatureResponse("a", "a@a.com", "now"),
                                new CommitSignatureResponse("a", "a@a.com", "now"),
                                null)
                );
        com.smithy.g.files.server.files.model.FileOperationResponse mappedResponse =
                new com.smithy.g.files.server.files.model.FileOperationResponse();

        when(smithyDtoMapper.toCreateFileRequest(createFileBody)).thenReturn(internalRequest);
        when(fileService.createFile(eq("o"), eq("r"), eq("p.txt"), any(CreateFileRequest.class)))
                .thenReturn(serviceResponse);
        when(smithyDtoMapper.toFileOperationResponse(serviceResponse)).thenReturn(mappedResponse);

        ResponseEntity<com.smithy.g.files.server.files.model.FileOperationResponse> result =
                delegate.createFile("o", "r", "p.txt", createFileBody);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(mappedResponse);
    }

    @Test
    void debeCrearCarpeta() {
        CreateFolderBody createFolderBody = new CreateFolderBody("dir", "msg");
        createFolderBody.setBranch("main");
        CreateFolderRequest internalRequest = new CreateFolderRequest("dir", "msg", "main");
        com.githubx.githubfilesms.dto.response.FileOperationResponse serviceResponse =
                new com.githubx.githubfilesms.dto.response.FileOperationResponse(
                        new FileContentResponse(".gitkeep", "dir/.gitkeep", "sha1", null, 0L, "base64", "", null, null, "c"),
                        new CommitSummaryResponse("c", "msg",
                                new CommitSignatureResponse("a", "a@a.com", "now"),
                                new CommitSignatureResponse("a", "a@a.com", "now"),
                                null)
                );
        com.smithy.g.files.server.files.model.FileOperationResponse mappedResponse =
                new com.smithy.g.files.server.files.model.FileOperationResponse();

        when(smithyDtoMapper.toCreateFolderRequest(createFolderBody)).thenReturn(internalRequest);
        when(fileService.createFolder(eq("o"), eq("r"), any(CreateFolderRequest.class)))
                .thenReturn(serviceResponse);
        when(smithyDtoMapper.toFileOperationResponse(serviceResponse)).thenReturn(mappedResponse);

        ResponseEntity<com.smithy.g.files.server.files.model.FileOperationResponse> result =
                delegate.createFolder("o", "r", createFolderBody);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void debeActualizarArchivo() {
        UpdateFileBody updateFileBody = new UpdateFileBody("oldsha", "x".getBytes(), "m");
        updateFileBody.setBranch("main");
        UpdateFileRequest internalRequest = new UpdateFileRequest("oldsha", "x".getBytes(), "m", "main", null, null, null);
        com.githubx.githubfilesms.dto.response.FileOperationResponse serviceResponse =
                new com.githubx.githubfilesms.dto.response.FileOperationResponse(
                        new FileContentResponse("a.txt", "a.txt", "newsha", null, 1L, "base64", "eA==", null, null, "c"),
                        new CommitSummaryResponse("c", "m",
                                new CommitSignatureResponse("a", "a@a.com", "now"),
                                new CommitSignatureResponse("a", "a@a.com", "now"),
                                null)
                );
        com.smithy.g.files.server.files.model.FileOperationResponse mappedResponse =
                new com.smithy.g.files.server.files.model.FileOperationResponse();

        when(smithyDtoMapper.toUpdateFileRequest(updateFileBody)).thenReturn(internalRequest);
        when(fileService.updateFile(eq("o"), eq("r"), eq("a.txt"), any(UpdateFileRequest.class)))
                .thenReturn(serviceResponse);
        when(smithyDtoMapper.toFileOperationResponse(serviceResponse)).thenReturn(mappedResponse);

        ResponseEntity<com.smithy.g.files.server.files.model.FileOperationResponse> result =
                delegate.updateFile("o", "r", "a.txt", updateFileBody);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void debeEliminarArchivo() {
        DeleteFileResponse serviceResponse = new DeleteFileResponse(
                new CommitSummaryResponse("c", "m",
                        new CommitSignatureResponse("a", "a@a.com", "now"),
                        new CommitSignatureResponse("a", "a@a.com", "now"),
                        null)
        );
        DeleteFileResponseBody mappedResponse = new DeleteFileResponseBody();

        when(fileService.deleteFile("o", "r", "a.txt", "sha", "m", "main"))
                .thenReturn(serviceResponse);
        when(smithyDtoMapper.toDeleteFileResponseBody(serviceResponse)).thenReturn(mappedResponse);

        ResponseEntity<DeleteFileResponseBody> result =
                delegate.deleteFile("o", "r", "a.txt", "sha", "m", "main");

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(mappedResponse);
    }

    @Test
    void debeListarCommits() {
        ListCommitsResponse serviceResponse = new ListCommitsResponse(
                List.of(new CommitResponse("abc", "m",
                        new CommitSignatureResponse("a", "a@a.com", "now"),
                        new CommitSignatureResponse("a", "a@a.com", "now"),
                        null, List.of())),
                PaginationMeta.of(0, 30, 1)
        );
        ListCommitsBody mappedResponse = new ListCommitsBody();

        when(commitService.listCommits("o", "r", "sha1", "path1", 0, 30))
                .thenReturn(serviceResponse);
        when(smithyDtoMapper.toListCommitsBody(serviceResponse)).thenReturn(mappedResponse);

        ResponseEntity<ListCommitsBody> result =
                delegate.listCommits("o", "r", "sha1", "path1", BigDecimal.ZERO, BigDecimal.valueOf(30));

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(commitService).listCommits("o", "r", "sha1", "path1", 0, 30);
    }

    @Test
    void debeObtenerCommit() {
        GetCommitResponse serviceResponse = new GetCommitResponse(
                new CommitResponse("abc", "m",
                        new CommitSignatureResponse("a", "a@a.com", "now"),
                        new CommitSignatureResponse("a", "a@a.com", "now"),
                        null, List.of()),
                List.of(new CommitFileResponse("f.txt", "added", 1, 0, 1, "patch"))
        );
        GetCommitBody mappedResponse = new GetCommitBody();

        when(commitService.getCommit("o", "r", "abc")).thenReturn(serviceResponse);
        when(smithyDtoMapper.toGetCommitBody(serviceResponse)).thenReturn(mappedResponse);

        ResponseEntity<GetCommitBody> result = delegate.getCommit("o", "r", "abc");

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(mappedResponse);
    }

    @Test
    void debeObtenerDiff() {
        when(commitService.getCommitDiff("o", "r", "abc")).thenReturn("diff --git a/x b/x\n");

        ResponseEntity<String> result = delegate.getCommitDiff("o", "r", "abc");

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).contains("diff --git");
    }

    @Test
    void debeCompararBranches() {
        CompareResponse serviceResponse = new CompareResponse(List.of(), 0, List.of(), 1, 1);
        CompareDTO mappedResponse = new CompareDTO();

        when(commitService.compareCommits("o", "r", "main", "feature"))
                .thenReturn(serviceResponse);
        when(smithyDtoMapper.toCompareDTO(serviceResponse)).thenReturn(mappedResponse);

        ResponseEntity<CompareDTO> result = delegate.compareCommits("o", "r", "main", "feature");

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(mappedResponse);
    }

    @Test
    void debeDescargarArchivo() {
        byte[] content = "file content".getBytes();
        when(fileService.getRawFile("o", "r", "path/to/file.txt", null))
                .thenReturn(content);

        ResponseEntity<byte[]> result = delegate.getRawFile("o", "r", "path/to/file.txt", null);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(content);
        assertThat(result.getHeaders().getFirst("Content-Disposition"))
                .contains("file.txt");
    }
}
