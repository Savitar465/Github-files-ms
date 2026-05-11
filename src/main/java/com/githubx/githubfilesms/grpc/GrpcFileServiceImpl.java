package com.githubx.githubfilesms.grpc;

import com.githubx.grpc.proto.*;
import com.githubx.githubfilesms.dto.request.CreateFileRequest;
import com.githubx.githubfilesms.dto.request.CreateFolderRequest;
import com.githubx.githubfilesms.dto.request.UpdateFileRequest;
import com.githubx.githubfilesms.service.contratos.FileService;
import com.githubx.githubfilesms.service.contratos.RepositoryService;
import com.githubx.githubfilesms.util.errorhandling.BadRequestException;
import com.githubx.githubfilesms.util.errorhandling.EntityConflictException;
import com.githubx.githubfilesms.util.errorhandling.EntityNotFoundException;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class GrpcFileServiceImpl extends FileApiGrpc.FileApiImplBase {

    private final RepositoryService repositoryService;
    private final FileService fileService;
    private final GrpcProtoMapper mapper;

    @Override
    public void createRepository(com.githubx.grpc.proto.CreateRepositoryRequest req,
                                 StreamObserver<CreateRepositoryResponse> obs) {
        try {
            var repoReq = mapper.fromProtoCreateRepo(req);
            obs.onNext(CreateRepositoryResponse.newBuilder()
                    .setRepository(mapper.toProtoRepo(repositoryService.createRepository(req.getOwner(), repoReq)))
                    .build());
            obs.onCompleted();
        } catch (Exception e) {
            obs.onError(toStatus(e).asRuntimeException());
        }
    }

    @Override
    public void deleteRepository(DeleteRepositoryRequest req, StreamObserver<DeleteRepositoryResponse> obs) {
        try {
            repositoryService.deleteRepository(req.getOwner(), req.getRepo());
            obs.onNext(DeleteRepositoryResponse.newBuilder().setSuccess(true).build());
            obs.onCompleted();
        } catch (Exception e) {
            obs.onError(toStatus(e).asRuntimeException());
        }
    }

    @Override
    public void createFile(com.githubx.grpc.proto.CreateFileRequest req, StreamObserver<FileOperationDTO> obs) {
        try {
            var fileReq = new CreateFileRequest(
                    req.getContent().toByteArray(),
                    req.getMessage(),
                    req.getBranch().isEmpty() ? null : req.getBranch(),
                    mapper.fromProtoIdentity(req.getAuthor()),
                    mapper.fromProtoIdentity(req.getCommitter())
            );
            obs.onNext(mapper.toProtoFileOperation(
                    fileService.createFile(req.getOwner(), req.getRepo(), req.getPath(), fileReq)));
            obs.onCompleted();
        } catch (Exception e) {
            obs.onError(toStatus(e).asRuntimeException());
        }
    }

    @Override
    public void updateFile(com.githubx.grpc.proto.UpdateFileRequest req, StreamObserver<FileOperationDTO> obs) {
        try {
            var fileReq = new UpdateFileRequest(
                    req.getSha(),
                    req.getContent().toByteArray(),
                    req.getMessage(),
                    req.getBranch().isEmpty() ? null : req.getBranch(),
                    req.getFromPath().isEmpty() ? null : req.getFromPath(),
                    mapper.fromProtoIdentity(req.getAuthor()),
                    mapper.fromProtoIdentity(req.getCommitter())
            );
            obs.onNext(mapper.toProtoFileOperation(
                    fileService.updateFile(req.getOwner(), req.getRepo(), req.getPath(), fileReq)));
            obs.onCompleted();
        } catch (Exception e) {
            obs.onError(toStatus(e).asRuntimeException());
        }
    }

    @Override
    public void deleteFile(DeleteFileRequest req, StreamObserver<DeleteFileResponse> obs) {
        try {
            var result = fileService.deleteFile(
                    req.getOwner(), req.getRepo(), req.getPath(),
                    req.getSha(), req.getMessage(),
                    req.getBranch().isEmpty() ? null : req.getBranch());
            obs.onNext(DeleteFileResponse.newBuilder()
                    .setCommit(mapper.toProtoCommit(result.commit()))
                    .build());
            obs.onCompleted();
        } catch (Exception e) {
            obs.onError(toStatus(e).asRuntimeException());
        }
    }

    @Override
    public void createFolder(com.githubx.grpc.proto.CreateFolderRequest req, StreamObserver<FileOperationDTO> obs) {
        try {
            var folderReq = new CreateFolderRequest(
                    req.getFolderPath(),
                    req.getMessage(),
                    req.getBranch().isEmpty() ? null : req.getBranch()
            );
            obs.onNext(mapper.toProtoFileOperation(
                    fileService.createFolder(req.getOwner(), req.getRepo(), folderReq)));
            obs.onCompleted();
        } catch (Exception e) {
            obs.onError(toStatus(e).asRuntimeException());
        }
    }

    // ─── Exception mapping ────────────────────────────────────

    private Status toStatus(Exception e) {
        if (e instanceof EntityNotFoundException) return Status.NOT_FOUND.withDescription(e.getMessage());
        if (e instanceof EntityConflictException) return Status.ALREADY_EXISTS.withDescription(e.getMessage());
        if (e instanceof BadRequestException) return Status.INVALID_ARGUMENT.withDescription(e.getMessage());
        return Status.INTERNAL.withDescription(e.getMessage());
    }
}
