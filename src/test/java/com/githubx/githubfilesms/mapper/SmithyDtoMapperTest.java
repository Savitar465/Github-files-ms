package com.githubx.githubfilesms.mapper;

import com.githubx.githubfilesms.dto.request.CreateFileRequest;
import com.githubx.githubfilesms.dto.request.CreateFolderRequest;
import com.githubx.githubfilesms.dto.request.IdentityRequest;
import com.githubx.githubfilesms.dto.request.UpdateFileRequest;
import com.githubx.githubfilesms.dto.response.*;
import com.githubx.githubfilesms.model.enums.GitObjectType;
import com.smithy.g.files.server.files.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SmithyDtoMapperTest {

    private SmithyDtoMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(SmithyDtoMapper.class);
    }

    // ── Request mapping tests ──────────────────────────────────────────

    @Test
    void debeMapearCreateFileBody() {
        CreateFileBody body = new CreateFileBody();
        body.setContent("SGVsbG8gV29ybGQ=");
        body.setMessage("Add new file");
        body.setBranch("main");

        CreateFileRequest result = mapper.toCreateFileRequest(body);

        assertNotNull(result);
        assertEquals("SGVsbG8gV29ybGQ=", result.content());
        assertEquals("Add new file", result.message());
        assertEquals("main", result.branch());
    }

    @Test
    void debeMapearCreateFolderBody() {
        CreateFolderBody body = new CreateFolderBody();
        body.setPath("src/new-folder");
        body.setMessage("Create folder");
        body.setBranch("develop");

        CreateFolderRequest result = mapper.toCreateFolderRequest(body);

        assertNotNull(result);
        assertEquals("src/new-folder", result.path());
        assertEquals("Create folder", result.message());
        assertEquals("develop", result.branch());
    }

    @Test
    void debeMapearUpdateFileBody() {
        UpdateFileBody body = new UpdateFileBody();
        body.setContent("VXBkYXRlZCBjb250ZW50");
        body.setMessage("Update file");
        body.setSha("abc123");
        body.setBranch("main");

        UpdateFileRequest result = mapper.toUpdateFileRequest(body);

        assertNotNull(result);
        assertEquals("VXBkYXRlZCBjb250ZW50", result.content());
        assertEquals("Update file", result.message());
        assertEquals("abc123", result.sha());
        assertEquals("main", result.branch());
    }

    @Test
    void debeMapearIdentity() {
        Identity identity = new Identity();
        identity.setName("John Doe");
        identity.setEmail("john@example.com");

        IdentityRequest result = mapper.toIdentityRequest(identity);

        assertNotNull(result);
        assertEquals("John Doe", result.name());
        assertEquals("john@example.com", result.email());
    }

    // ── Response mapping tests ─────────────────────────────────────────

    @Test
    void debeMapearGetFileContentBodyResponse() {
        FileContentResponse fileContent = new FileContentResponse(
                "file.txt", "src/file.txt", "sha123", GitObjectType.FILE,
                100L, "content", "Y29udGVudA==", "base64", "/download", "/html");
        GetFileContentBodyResponse response = GetFileContentBodyResponse.ofFile(fileContent);

        GetFileContentBody result = mapper.toGetFileContentBody(response);

        assertNotNull(result);
    }

    @Test
    void debeMapearFileContentDTO() {
        FileContentResponse response = new FileContentResponse(
                "test.java", "src/test.java", "sha456", GitObjectType.FILE,
                250L, "public class Test {}", "cHVibGljIGNsYXNzIFRlc3Qge30=", "base64",
                "/download/test.java", "/html/test.java");

        FileContentDTO result = mapper.toFileContentDTO(response);

        assertNotNull(result);
        assertEquals("test.java", result.getName());
        assertEquals("src/test.java", result.getPath());
        assertEquals("sha456", result.getSha());
        assertEquals(com.smithy.g.files.server.files.model.GitObjectType.FILE, result.getType());
    }

    @Test
    void debeMapearDirectoryEntryDTO() {
        DirectoryEntryResponse response = new DirectoryEntryResponse(
                "folder", "src/folder", "sha789", GitObjectType.DIRECTORY, null, null);

        DirectoryEntryDTO result = mapper.toDirectoryEntryDTO(response);

        assertNotNull(result);
        assertEquals("folder", result.getName());
        assertEquals("src/folder", result.getPath());
        assertEquals(com.smithy.g.files.server.files.model.GitObjectType.DIR, result.getType());
    }

    @Test
    void debeMapearListaDeDirectoryEntryDTO() {
        DirectoryEntryResponse entry1 = new DirectoryEntryResponse(
                "file1.txt", "file1.txt", "sha1", GitObjectType.FILE, 100L, "/download1");
        DirectoryEntryResponse entry2 = new DirectoryEntryResponse(
                "folder", "folder", "sha2", GitObjectType.DIRECTORY, null, null);

        List<DirectoryEntryDTO> result = mapper.toDirectoryEntryDTOList(List.of(entry1, entry2));

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void debeMapearFileOperationResponse() {
        FileContentResponse content = new FileContentResponse(
                "new.txt", "new.txt", "sha", GitObjectType.FILE, 50L, "text", "dGV4dA==", "base64", "/dl", "/html");
        CommitSummaryResponse commit = new CommitSummaryResponse(
                "commitsha", "/commits/commitsha", "Add file",
                new CommitSignatureResponse("Author", "a@test.com", "2026-01-01T00:00:00Z"),
                new CommitSignatureResponse("Committer", "c@test.com", "2026-01-01T00:00:00Z"));
        FileOperationResponse response = new FileOperationResponse(content, commit);

        com.smithy.g.files.server.files.model.FileOperationResponse result =
                mapper.toFileOperationResponse(response);

        assertNotNull(result);
    }

    @Test
    void debeMapearCommitSummaryDTO() {
        CommitSummaryResponse response = new CommitSummaryResponse(
                "sha123", "/commits/sha123", "Commit message",
                new CommitSignatureResponse("Author", "author@test.com", "2026-01-01T00:00:00Z"),
                new CommitSignatureResponse("Committer", "committer@test.com", "2026-01-01T00:00:00Z"));

        CommitSummaryDTO result = mapper.toCommitSummaryDTO(response);

        assertNotNull(result);
        assertEquals("sha123", result.getSha());
        assertEquals("Commit message", result.getMessage());
    }

    @Test
    void debeMapearCommitSignature() {
        CommitSignatureResponse response = new CommitSignatureResponse(
                "John Doe", "john@example.com", "2026-06-15T10:30:00Z");

        CommitSignature result = mapper.toCommitSignature(response);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("john@example.com", result.getEmail());
        assertEquals("2026-06-15T10:30:00Z", result.getDate());
    }

    @Test
    void debeMapearDeleteFileResponseBody() {
        CommitSummaryResponse commit = new CommitSummaryResponse(
                "delsha", "/commits/delsha", "Delete file.txt",
                new CommitSignatureResponse("Author", "a@test.com", "2026-01-01T00:00:00Z"),
                new CommitSignatureResponse("Committer", "c@test.com", "2026-01-01T00:00:00Z"));
        DeleteFileResponse response = new DeleteFileResponse(commit);

        DeleteFileResponseBody result = mapper.toDeleteFileResponseBody(response);

        assertNotNull(result);
    }

    @Test
    void debeMapearListCommitsBody() {
        PaginationMeta pagination = new PaginationMeta(0, 30, 100, false, true);
        ListCommitsResponse response = new ListCommitsResponse(Collections.emptyList(), pagination);

        ListCommitsBody result = mapper.toListCommitsBody(response);

        assertNotNull(result);
    }

    @Test
    void debeMapearCommitDTO() {
        CommitResponse response = new CommitResponse(
                "sha123", "/commits/sha123", "Commit message",
                new CommitSignatureResponse("Author", "author@test.com", "2026-01-01T00:00:00Z"),
                new CommitSignatureResponse("Committer", "committer@test.com", "2026-01-01T00:00:00Z"),
                Collections.emptyList(), Collections.emptyList(), 5, 10, 3);

        CommitDTO result = mapper.toCommitDTO(response);

        assertNotNull(result);
        assertEquals("sha123", result.getSha());
        assertEquals("Commit message", result.getMessage());
    }

    @Test
    void debeMapearCommitDTOList() {
        CommitResponse commit1 = new CommitResponse(
                "sha1", "/commits/sha1", "First commit",
                new CommitSignatureResponse("Author", "a@test.com", "2026-01-01T00:00:00Z"),
                new CommitSignatureResponse("Committer", "c@test.com", "2026-01-01T00:00:00Z"),
                Collections.emptyList(), Collections.emptyList(), 1, 2, 1);
        CommitResponse commit2 = new CommitResponse(
                "sha2", "/commits/sha2", "Second commit",
                new CommitSignatureResponse("Author", "a@test.com", "2026-01-02T00:00:00Z"),
                new CommitSignatureResponse("Committer", "c@test.com", "2026-01-02T00:00:00Z"),
                Collections.emptyList(), Collections.emptyList(), 3, 4, 2);

        List<CommitDTO> result = mapper.toCommitDTOList(List.of(commit1, commit2));

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void debeMapearCommitParent() {
        CommitParentResponse response = new CommitParentResponse("parentsha", "/commits/parentsha");

        CommitParent result = mapper.toCommitParent(response);

        assertNotNull(result);
        assertEquals("parentsha", result.getSha());
        assertEquals("/commits/parentsha", result.getUrl());
    }

    @Test
    void debeMapearCommitParentList() {
        CommitParentResponse parent1 = new CommitParentResponse("parent1", "/commits/parent1");
        CommitParentResponse parent2 = new CommitParentResponse("parent2", "/commits/parent2");

        List<CommitParent> result = mapper.toCommitParentList(List.of(parent1, parent2));

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void debeMapearPaginationMeta() {
        PaginationMeta response = new PaginationMeta(2, 30, 150, true, true);

        com.smithy.g.files.server.files.model.PaginationMeta result = mapper.toPaginationMeta(response);

        assertNotNull(result);
    }

    @Test
    void debeMapearGetCommitBody() {
        CommitResponse commit = new CommitResponse(
                "sha123", "/commits/sha123", "Commit",
                new CommitSignatureResponse("Author", "a@test.com", "2026-01-01T00:00:00Z"),
                new CommitSignatureResponse("Committer", "c@test.com", "2026-01-01T00:00:00Z"),
                Collections.emptyList(), Collections.emptyList(), 1, 1, 1);
        GetCommitResponse response = new GetCommitResponse(commit);

        GetCommitBody result = mapper.toGetCommitBody(response);

        assertNotNull(result);
    }

    @Test
    void debeMapearCommitFile() {
        CommitFileResponse response = new CommitFileResponse(
                "sha", "file.txt", "modified", 10, 5, 15, "patch content");

        CommitFile result = mapper.toCommitFile(response);

        assertNotNull(result);
        assertEquals("file.txt", result.getFilename());
        assertEquals("modified", result.getStatus());
    }

    @Test
    void debeMapearCommitFileList() {
        CommitFileResponse file1 = new CommitFileResponse(
                "sha1", "file1.txt", "added", 10, 0, 10, "patch1");
        CommitFileResponse file2 = new CommitFileResponse(
                "sha2", "file2.txt", "deleted", 0, 20, 20, "patch2");

        List<CommitFile> result = mapper.toCommitFileList(List.of(file1, file2));

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void debeMapearCompareDTO() {
        CompareResponse response = new CompareResponse(
                "/compare/main...feature", "ahead", 10, 5, 3,
                Collections.emptyList(), Collections.emptyList());

        CompareDTO result = mapper.toCompareDTO(response);

        assertNotNull(result);
    }

    // ── Enum mapping tests ─────────────────────────────────────────────

    @Test
    void debeMapearGitObjectTypeFile() {
        com.smithy.g.files.server.files.model.GitObjectType result =
                mapper.toGitObjectType(GitObjectType.FILE);
        assertEquals(com.smithy.g.files.server.files.model.GitObjectType.FILE, result);
    }

    @Test
    void debeMapearGitObjectTypeDirectory() {
        com.smithy.g.files.server.files.model.GitObjectType result =
                mapper.toGitObjectType(GitObjectType.DIRECTORY);
        assertEquals(com.smithy.g.files.server.files.model.GitObjectType.DIR, result);
    }

    @Test
    void debeRetornarNullParaGitObjectTypeNull() {
        assertNull(mapper.toGitObjectType(null));
    }

    // ── BigDecimal conversion tests ────────────────────────────────────

    @Test
    void debeConvertirLongABigDecimal() {
        BigDecimal result = mapper.toBigDecimal(100L);
        assertEquals(BigDecimal.valueOf(100), result);
    }

    @Test
    void debeRetornarNullParaLongNull() {
        assertNull(mapper.toBigDecimal((Long) null));
    }

    @Test
    void debeConvertirIntegerABigDecimal() {
        BigDecimal result = mapper.toBigDecimal(50);
        assertEquals(BigDecimal.valueOf(50), result);
    }

    @Test
    void debeRetornarNullParaIntegerNull() {
        assertNull(mapper.toBigDecimal((Integer) null));
    }
}
