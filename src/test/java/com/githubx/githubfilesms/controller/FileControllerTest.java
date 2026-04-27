package com.githubx.githubfilesms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.githubx.githubfilesms.dto.request.CreateFileRequest;
import com.githubx.githubfilesms.dto.request.CreateFolderRequest;
import com.githubx.githubfilesms.dto.request.UpdateFileRequest;
import com.githubx.githubfilesms.dto.response.*;
import com.githubx.githubfilesms.service.contratos.FileService;
import com.githubx.githubfilesms.util.errorhandling.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class FileControllerTest {

    @Mock
    private FileService fileService;

    @InjectMocks
    private FileController fileController;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(fileController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void debeLlamarGetFileContentConPathResuelto() throws Exception {
        when(fileService.getFileContent(eq("owner"), eq("repo"), eq("a/b/c.txt"), isNull()))
                .thenReturn(GetFileContentBodyResponse.ofFile(
                        new FileContentResponse("c.txt", "a/b/c.txt", "sha1", null, 1L, "base64", "YQ==",
                                null, null, "lcsha")));

        mockMvc.perform(get("/v1/repos/owner/repo/contents/a/b/c.txt"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.file.path").value("a/b/c.txt"));

        verify(fileService).getFileContent("owner", "repo", "a/b/c.txt", null);
    }

    @Test
    void debeCrearArchivo() throws Exception {
        CreateFileRequest request = new CreateFileRequest("hola".getBytes(), "msg", "main", null, null);
        when(fileService.createFile(eq("o"), eq("r"), eq("p.txt"), any(CreateFileRequest.class)))
                .thenReturn(new FileOperationResponse(
                        new FileContentResponse("p.txt", "p.txt", "sha1", null, 4L, "base64", "aQ==", null, null, "c"),
                        new CommitSummaryResponse("c", "msg",
                                new CommitSignatureResponse("a", "a@a.com", "now"),
                                new CommitSignatureResponse("a", "a@a.com", "now"),
                                null
                        )
                ));

        mockMvc.perform(put("/v1/repos/o/r/contents/p.txt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.commit.sha").value("c"));
    }

    @Test
    void debeCrearCarpeta() throws Exception {
        CreateFolderRequest request = new CreateFolderRequest("dir", "msg", "main");
        when(fileService.createFolder(eq("o"), eq("r"), any(CreateFolderRequest.class)))
                .thenReturn(new FileOperationResponse(
                        new FileContentResponse(".gitkeep", "dir/.gitkeep", "sha1", null, 0L, "base64", "", null, null, "c"),
                        new CommitSummaryResponse("c", "msg",
                                new CommitSignatureResponse("a", "a@a.com", "now"),
                                new CommitSignatureResponse("a", "a@a.com", "now"),
                                null
                        )
                ));

        mockMvc.perform(post("/v1/repos/o/r/folders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void debeActualizarArchivo() throws Exception {
        UpdateFileRequest request = new UpdateFileRequest("oldsha", "x".getBytes(), "m", "main", null, null, null);
        when(fileService.updateFile(eq("o"), eq("r"), eq("a.txt"), any(UpdateFileRequest.class)))
                .thenReturn(new FileOperationResponse(
                        new FileContentResponse("a.txt", "a.txt", "newsha", null, 1L, "base64", "eA==", null, null, "c"),
                        new CommitSummaryResponse("c", "m",
                                new CommitSignatureResponse("a", "a@a.com", "now"),
                                new CommitSignatureResponse("a", "a@a.com", "now"),
                                null
                        )
                ));

        mockMvc.perform(patch("/v1/repos/o/r/contents/a.txt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.sha").value("newsha"));
    }

    @Test
    void debeEliminarArchivo() throws Exception {
        when(fileService.deleteFile("o", "r", "a.txt", "sha", "m", "main"))
                .thenReturn(new DeleteFileResponse(
                        new CommitSummaryResponse("c", "m",
                                new CommitSignatureResponse("a", "a@a.com", "now"),
                                new CommitSignatureResponse("a", "a@a.com", "now"),
                                null
                        )
                ));

        mockMvc.perform(delete("/v1/repos/o/r/contents/a.txt")
                        .param("sha", "sha")
                        .param("message", "m")
                        .param("branch", "main"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.commit.sha").value("c"));
    }
}
