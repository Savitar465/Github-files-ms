package com.githubx.githubfilesms.grpc;

import com.githubx.grpc.proto.*;
import com.githubx.githubfilesms.dto.request.CreateRepositoryRequest;
import com.githubx.githubfilesms.dto.request.IdentityRequest;
import com.githubx.githubfilesms.dto.response.CommitSignatureResponse;
import com.githubx.githubfilesms.dto.response.CommitSummaryResponse;
import com.githubx.githubfilesms.dto.response.DirectoryEntryResponse;
import com.githubx.githubfilesms.dto.response.FileContentResponse;
import com.githubx.githubfilesms.dto.response.FileOperationResponse;
import com.githubx.githubfilesms.dto.response.RepositoryResponse;
import com.githubx.githubfilesms.model.enums.GitObjectType;
import com.githubx.githubfilesms.model.enums.RepoVisibility;
import org.springframework.stereotype.Component;

@Component
public class GrpcProtoMapper {

    // ─── RepoVisibility ───────────────────────────────────────

    public com.githubx.grpc.proto.RepoVisibility toProtoVisibility(RepoVisibility v) {
        if (v == null) return com.githubx.grpc.proto.RepoVisibility.REPO_VISIBILITY_UNSPECIFIED;
        return switch (v) {
            case PUBLIC -> com.githubx.grpc.proto.RepoVisibility.REPO_VISIBILITY_PUBLIC;
            case PRIVATE -> com.githubx.grpc.proto.RepoVisibility.REPO_VISIBILITY_PRIVATE;
        };
    }

    public RepoVisibility fromProtoVisibility(com.githubx.grpc.proto.RepoVisibility v) {
        return switch (v) {
            case REPO_VISIBILITY_PUBLIC -> RepoVisibility.PUBLIC;
            default -> RepoVisibility.PRIVATE;
        };
    }

    // ─── GitObjectType ────────────────────────────────────────

    public com.githubx.grpc.proto.GitObjectType toProtoObjectType(GitObjectType t) {
        if (t == null) return com.githubx.grpc.proto.GitObjectType.GIT_OBJECT_TYPE_UNSPECIFIED;
        return switch (t) {
            case FILE -> com.githubx.grpc.proto.GitObjectType.GIT_OBJECT_TYPE_FILE;
            case DIRECTORY -> com.githubx.grpc.proto.GitObjectType.GIT_OBJECT_TYPE_DIRECTORY;
        };
    }

    // ─── RepoDTO ──────────────────────────────────────────────

    public RepoDTO toProtoRepo(RepositoryResponse r) {
        return RepoDTO.newBuilder()
                .setId(r.id() != null ? r.id() : 0)
                .setOwner(safe(r.owner()))
                .setName(safe(r.name()))
                .setFullName(safe(r.fullName()))
                .setDescription(safe(r.description()))
                .setVisibility(toProtoVisibility(r.visibility()))
                .setDefaultBranch(safe(r.defaultBranch()))
                .setHtmlUrl(safe(r.htmlUrl()))
                .setCreatedAt(r.createdAt() != null ? r.createdAt().toString() : "")
                .setUpdatedAt(r.updatedAt() != null ? r.updatedAt().toString() : "")
                .build();
    }

    // ─── FileDTO ──────────────────────────────────────────────

    public FileDTO toProtoFile(FileContentResponse f) {
        if (f == null) return FileDTO.getDefaultInstance();
        return FileDTO.newBuilder()
                .setName(safe(f.name()))
                .setPath(safe(f.path()))
                .setSha(safe(f.sha()))
                .setType(toProtoObjectType(f.type()))
                .setSize(f.size() != null ? f.size() : 0)
                .setEncoding(safe(f.encoding()))
                .setContent(safe(f.content()))
                .setDownloadUrl(safe(f.downloadUrl()))
                .setHtmlUrl(safe(f.htmlUrl()))
                .setLastCommitSha(safe(f.lastCommitSha()))
                .build();
    }

    // ─── DirectoryEntryDTO ────────────────────────────────────

    public DirectoryEntryDTO toProtoEntry(DirectoryEntryResponse e) {
        return DirectoryEntryDTO.newBuilder()
                .setName(safe(e.name()))
                .setPath(safe(e.path()))
                .setSha(safe(e.sha()))
                .setType(toProtoObjectType(e.type()))
                .setSize(e.size() != null ? e.size() : 0)
                .setDownloadUrl(safe(e.downloadUrl()))
                .build();
    }

    // ─── CommitSignatureDTO ───────────────────────────────────

    public CommitSignatureDTO toProtoSignature(CommitSignatureResponse s) {
        if (s == null) return CommitSignatureDTO.getDefaultInstance();
        return CommitSignatureDTO.newBuilder()
                .setName(safe(s.name()))
                .setEmail(safe(s.email()))
                .setDate(safe(s.date()))
                .build();
    }

    // ─── CommitSummaryDTO ─────────────────────────────────────

    public CommitSummaryDTO toProtoCommit(CommitSummaryResponse c) {
        if (c == null) return CommitSummaryDTO.getDefaultInstance();
        return CommitSummaryDTO.newBuilder()
                .setSha(safe(c.sha()))
                .setMessage(safe(c.message()))
                .setAuthor(toProtoSignature(c.author()))
                .setCommitter(toProtoSignature(c.committer()))
                .setHtmlUrl(safe(c.htmlUrl()))
                .build();
    }

    // ─── FileOperationDTO ─────────────────────────────────────

    public FileOperationDTO toProtoFileOperation(FileOperationResponse op) {
        return FileOperationDTO.newBuilder()
                .setContent(toProtoFile(op.content()))
                .setCommit(toProtoCommit(op.commit()))
                .build();
    }

    // ─── IdentityRequest from proto IdentityDTO ───────────────

    public IdentityRequest fromProtoIdentity(IdentityDTO id) {
        if (id == null || (id.getName().isEmpty() && id.getEmail().isEmpty())) return null;
        return new IdentityRequest(id.getName(), id.getEmail());
    }

    // ─── CreateRepositoryRequest from proto ───────────────────

    public CreateRepositoryRequest fromProtoCreateRepo(com.githubx.grpc.proto.CreateRepositoryRequest req) {
        return new CreateRepositoryRequest(
                req.getName(),
                req.getDescription().isEmpty() ? null : req.getDescription(),
                fromProtoVisibility(req.getVisibility()),
                req.getDefaultBranch().isEmpty() ? null : req.getDefaultBranch()
        );
    }

    // ─── Helpers ──────────────────────────────────────────────

    private String safe(String s) {
        return s != null ? s : "";
    }
}
