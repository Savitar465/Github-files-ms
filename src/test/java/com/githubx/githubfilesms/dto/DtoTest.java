package com.githubx.githubfilesms.dto;

import com.githubx.githubfilesms.dto.request.*;
import com.githubx.githubfilesms.dto.response.*;
import com.githubx.githubfilesms.model.enums.GitObjectType;
import com.githubx.githubfilesms.model.enums.RepoVisibility;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DtoTest {

    // ── Request DTOs ───────────────────────────────────────────────────

    @Test
    void debeCrearCreateFileRequest() {
        IdentityRequest author = new IdentityRequest("John", "john@test.com");
        byte[] content = "Hello".getBytes();
        CreateFileRequest request = new CreateFileRequest(content, "Add file", "main", author, author);

        assertArrayEquals(content, request.content());
        assertEquals("Add file", request.message());
        assertEquals("main", request.branch());
        assertNotNull(request.author());
    }

    @Test
    void debeCrearCreateFolderRequest() {
        CreateFolderRequest request = new CreateFolderRequest("src/new-folder", "Create folder", "develop");

        assertEquals("src/new-folder", request.path());
        assertEquals("Create folder", request.message());
        assertEquals("develop", request.branch());
    }

    @Test
    void debeCrearUpdateFileRequest() {
        byte[] content = "Updated".getBytes();
        UpdateFileRequest request = new UpdateFileRequest("sha123", content, "Update file", "main", null, null, null);

        assertEquals("sha123", request.sha());
        assertArrayEquals(content, request.content());
        assertEquals("Update file", request.message());
        assertEquals("main", request.branch());
    }

    @Test
    void debeCrearCreateRepositoryRequest() {
        CreateRepositoryRequest request = new CreateRepositoryRequest(
                "myrepo", "My repository", RepoVisibility.PUBLIC, "main");

        assertEquals("myrepo", request.name());
        assertEquals("My repository", request.description());
        assertEquals(RepoVisibility.PUBLIC, request.visibility());
        assertEquals("main", request.defaultBranch());
    }

    @Test
    void debeCrearIdentityRequest() {
        IdentityRequest request = new IdentityRequest("John Doe", "john@example.com");

        assertEquals("John Doe", request.name());
        assertEquals("john@example.com", request.email());
    }

    // ── Response DTOs ──────────────────────────────────────────────────

    @Test
    void debeCrearFileContentResponse() {
        FileContentResponse response = new FileContentResponse(
                "file.txt", "src/file.txt", "sha123", GitObjectType.FILE,
                100L, "base64", "Y29udGVudA==", "/download", "/html", "commitsha");

        assertEquals("file.txt", response.name());
        assertEquals("src/file.txt", response.path());
        assertEquals("sha123", response.sha());
        assertEquals(GitObjectType.FILE, response.type());
        assertEquals(100L, response.size());
    }

    @Test
    void debeCrearDirectoryEntryResponse() {
        DirectoryEntryResponse response = new DirectoryEntryResponse(
                "folder", "src/folder", "sha456", GitObjectType.DIRECTORY, null, "/download");

        assertEquals("folder", response.name());
        assertEquals(GitObjectType.DIRECTORY, response.type());
        assertNull(response.size());
    }

    @Test
    void debeCrearGetFileContentBodyResponseConFile() {
        FileContentResponse file = new FileContentResponse(
                "test.txt", "test.txt", "sha", GitObjectType.FILE, 50L,
                "base64", "dGV4dA==", "/dl", "/html", "commitsha");

        GetFileContentBodyResponse response = GetFileContentBodyResponse.ofFile(file);

        assertNotNull(response.file());
        assertNull(response.entries());
    }

    @Test
    void debeCrearGetFileContentBodyResponseConDirectory() {
        DirectoryEntryResponse entry = new DirectoryEntryResponse(
                "file.txt", "file.txt", "sha", GitObjectType.FILE, 100L, "/dl");

        GetFileContentBodyResponse response = GetFileContentBodyResponse.ofDirectory(List.of(entry));

        assertNull(response.file());
        assertNotNull(response.entries());
        assertEquals(1, response.entries().size());
    }

    @Test
    void debeCrearFileOperationResponse() {
        CommitSignatureResponse author = new CommitSignatureResponse("Author", "a@test.com", "2026-01-01T00:00:00Z");
        FileContentResponse content = new FileContentResponse(
                "new.txt", "new.txt", "sha", GitObjectType.FILE, 10L,
                "base64", "ZGF0YQ==", "/dl", "/html", "commitsha");
        CommitSummaryResponse commit = new CommitSummaryResponse(
                "commitsha", "Add file", author, author, "/commits/commitsha");

        FileOperationResponse response = new FileOperationResponse(content, commit);

        assertNotNull(response.content());
        assertNotNull(response.commit());
    }

    @Test
    void debeCrearDeleteFileResponse() {
        CommitSignatureResponse author = new CommitSignatureResponse("Author", "a@test.com", "2026-01-01T00:00:00Z");
        CommitSummaryResponse commit = new CommitSummaryResponse(
                "delsha", "Delete file", author, author, "/commits/delsha");

        DeleteFileResponse response = new DeleteFileResponse(commit);

        assertNotNull(response.commit());
        assertEquals("Delete file", response.commit().message());
    }

    @Test
    void debeCrearCommitResponse() {
        CommitSignatureResponse author = new CommitSignatureResponse(
                "Author", "author@test.com", "2026-01-01T00:00:00Z");
        CommitSignatureResponse committer = new CommitSignatureResponse(
                "Committer", "committer@test.com", "2026-01-01T00:00:00Z");

        CommitResponse response = new CommitResponse(
                "sha123", "Commit message", author, committer,
                "/commits/sha123", Collections.emptyList());

        assertEquals("sha123", response.sha());
        assertEquals("Commit message", response.message());
        assertNotNull(response.author());
        assertNotNull(response.committer());
    }

    @Test
    void debeCrearCommitSummaryResponse() {
        CommitSignatureResponse author = new CommitSignatureResponse(
                "Author", "author@test.com", "2026-01-01T00:00:00Z");

        CommitSummaryResponse response = new CommitSummaryResponse(
                "sha", "Summary", author, author, "/commits/sha");

        assertEquals("sha", response.sha());
        assertEquals("Summary", response.message());
    }

    @Test
    void debeCrearCommitSignatureResponse() {
        CommitSignatureResponse response = new CommitSignatureResponse(
                "John Doe", "john@example.com", "2026-06-15T10:30:00Z");

        assertEquals("John Doe", response.name());
        assertEquals("john@example.com", response.email());
        assertEquals("2026-06-15T10:30:00Z", response.date());
    }

    @Test
    void debeCrearCommitParentResponse() {
        CommitParentResponse response = new CommitParentResponse("parentsha", "/commits/parentsha");

        assertEquals("parentsha", response.sha());
        assertEquals("/commits/parentsha", response.url());
    }

    @Test
    void debeCrearCommitFileResponse() {
        CommitFileResponse response = new CommitFileResponse(
                "Main.java", "modified", 10, 5, 15, "patch");

        assertEquals("Main.java", response.filename());
        assertEquals("modified", response.status());
        assertEquals(10, response.additions());
        assertEquals(5, response.deletions());
    }

    @Test
    void debeCrearListCommitsResponse() {
        PaginationMeta pagination = new PaginationMeta(0, 30, 100L, 4);
        ListCommitsResponse response = new ListCommitsResponse(Collections.emptyList(), pagination);

        assertNotNull(response.commits());
        assertNotNull(response.pagination());
        assertEquals(100L, response.pagination().total());
    }

    @Test
    void debeCrearGetCommitResponse() {
        CommitSignatureResponse author = new CommitSignatureResponse("Author", "a@test.com", "2026-01-01T00:00:00Z");
        CommitResponse commit = new CommitResponse(
                "sha", "message", author, author, "/url", Collections.emptyList());
        GetCommitResponse response = new GetCommitResponse(commit, Collections.emptyList());

        assertNotNull(response.commit());
    }

    @Test
    void debeCrearCompareResponse() {
        CompareResponse response = new CompareResponse(
                Collections.emptyList(), 10, Collections.emptyList(), 5, 3);

        assertEquals(10, response.totalCommits());
        assertEquals(5, response.aheadBy());
        assertEquals(3, response.behindBy());
    }

    @Test
    void debeCrearPaginationMeta() {
        PaginationMeta meta = new PaginationMeta(2, 30, 150L, 5);

        assertEquals(2, meta.page());
        assertEquals(30, meta.perPage());
        assertEquals(150L, meta.total());
        assertEquals(5, meta.totalPages());
    }

    @Test
    void debeCrearRepositoryResponse() {
        RepositoryResponse response = new RepositoryResponse(
                1L, "owner", "repo", "owner/repo", "Description",
                RepoVisibility.PUBLIC, "main", "/v1/repos/owner/repo", null, null);

        assertEquals(1L, response.id());
        assertEquals("owner", response.owner());
        assertEquals("repo", response.name());
        assertEquals("owner/repo", response.fullName());
    }
}
