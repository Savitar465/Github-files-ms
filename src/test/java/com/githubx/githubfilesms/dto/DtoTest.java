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
        CreateFileRequest request = new CreateFileRequest(
                "SGVsbG8=", "Add file", "main", author, author);

        assertEquals("SGVsbG8=", request.content());
        assertEquals("Add file", request.message());
        assertEquals("main", request.branch());
        assertNotNull(request.author());
    }

    @Test
    void debeCrearCreateFolderRequest() {
        CreateFolderRequest request = new CreateFolderRequest(
                "src/new-folder", "Create folder", "develop", null, null);

        assertEquals("src/new-folder", request.path());
        assertEquals("Create folder", request.message());
        assertEquals("develop", request.branch());
    }

    @Test
    void debeCrearUpdateFileRequest() {
        UpdateFileRequest request = new UpdateFileRequest(
                "VXBkYXRlZA==", "Update file", "sha123", "main", null, null);

        assertEquals("VXBkYXRlZA==", request.content());
        assertEquals("Update file", request.message());
        assertEquals("sha123", request.sha());
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
                100L, "content", "Y29udGVudA==", "base64", "/download", "/html");

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
                "text", "dGV4dA==", "base64", "/dl", "/html");

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
        FileContentResponse content = new FileContentResponse(
                "new.txt", "new.txt", "sha", GitObjectType.FILE, 10L,
                "data", "ZGF0YQ==", "base64", "/dl", "/html");
        CommitSummaryResponse commit = new CommitSummaryResponse(
                "commitsha", "/commits/commitsha", "Add file", null, null);

        FileOperationResponse response = new FileOperationResponse(content, commit);

        assertNotNull(response.content());
        assertNotNull(response.commit());
    }

    @Test
    void debeCrearDeleteFileResponse() {
        CommitSummaryResponse commit = new CommitSummaryResponse(
                "delsha", "/commits/delsha", "Delete file", null, null);

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
                "sha123", "/commits/sha123", "Commit message",
                author, committer, Collections.emptyList(), Collections.emptyList(),
                10, 5, 3);

        assertEquals("sha123", response.sha());
        assertEquals("Commit message", response.message());
        assertEquals(10, response.additions());
        assertEquals(5, response.deletions());
        assertEquals(3, response.changedFiles());
    }

    @Test
    void debeCrearCommitSummaryResponse() {
        CommitSignatureResponse author = new CommitSignatureResponse(
                "Author", "author@test.com", "2026-01-01T00:00:00Z");

        CommitSummaryResponse response = new CommitSummaryResponse(
                "sha", "/commits/sha", "Summary", author, author);

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
                "filesha", "Main.java", "modified", 10, 5, 15, "patch");

        assertEquals("filesha", response.sha());
        assertEquals("Main.java", response.filename());
        assertEquals("modified", response.status());
        assertEquals(10, response.additions());
        assertEquals(5, response.deletions());
    }

    @Test
    void debeCrearListCommitsResponse() {
        PaginationMeta pagination = new PaginationMeta(0, 30, 100, false, true);
        ListCommitsResponse response = new ListCommitsResponse(Collections.emptyList(), pagination);

        assertNotNull(response.commits());
        assertNotNull(response.pagination());
        assertEquals(100, response.pagination().totalCount());
    }

    @Test
    void debeCrearGetCommitResponse() {
        CommitResponse commit = new CommitResponse(
                "sha", "/url", "message", null, null,
                Collections.emptyList(), Collections.emptyList(), 0, 0, 0);
        GetCommitResponse response = new GetCommitResponse(commit);

        assertNotNull(response.commit());
    }

    @Test
    void debeCrearCompareResponse() {
        CompareResponse response = new CompareResponse(
                "/compare/main...feature", "ahead", 10, 5, 3,
                Collections.emptyList(), Collections.emptyList());

        assertEquals("/compare/main...feature", response.url());
        assertEquals("ahead", response.status());
        assertEquals(10, response.aheadBy());
        assertEquals(5, response.behindBy());
        assertEquals(3, response.totalCommits());
    }

    @Test
    void debeCrearPaginationMeta() {
        PaginationMeta meta = new PaginationMeta(2, 30, 150, true, true);

        assertEquals(2, meta.page());
        assertEquals(30, meta.perPage());
        assertEquals(150, meta.totalCount());
        assertTrue(meta.hasPrevious());
        assertTrue(meta.hasNext());
    }

    @Test
    void debeCrearRepositoryResponse() {
        RepositoryResponse response = new RepositoryResponse(
                1L, "owner", "repo", "Description",
                RepoVisibility.PUBLIC, "main", "owner/repo",
                "/v1/repos/owner/repo", null, null);

        assertEquals(1L, response.id());
        assertEquals("owner", response.owner());
        assertEquals("repo", response.name());
        assertEquals("owner/repo", response.fullName());
    }
}
