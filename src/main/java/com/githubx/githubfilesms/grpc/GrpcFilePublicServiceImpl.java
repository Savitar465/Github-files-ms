package com.githubx.githubfilesms.grpc;

import com.githubx.grpc.proto.*;
import com.githubx.githubfilesms.service.contratos.FileService;
import com.githubx.githubfilesms.service.contratos.RepositoryService;
import com.githubx.githubfilesms.util.errorhandling.EntityNotFoundException;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class GrpcFilePublicServiceImpl extends FilePublicApiGrpc.FilePublicApiImplBase {

    private final RepositoryService repositoryService;
    private final FileService fileService;
    private final GrpcProtoMapper mapper;

    @Override
    public void getRepository(GetRepositoryRequest req, StreamObserver<GetRepositoryResponse> obs) {
        try {
            obs.onNext(GetRepositoryResponse.newBuilder()
                    .setRepository(mapper.toProtoRepo(repositoryService.getRepository(req.getOwner(), req.getRepo())))
                    .build());
            obs.onCompleted();
        } catch (EntityNotFoundException e) {
            obs.onError(Status.NOT_FOUND.withDescription(e.getMessage()).asRuntimeException());
        } catch (Exception e) {
            obs.onError(Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public void listRepositories(ListRepositoriesRequest req, StreamObserver<ListRepositoriesResponse> obs) {
        try {
            var repos = repositoryService.listRepositories(req.getOwner());
            obs.onNext(ListRepositoriesResponse.newBuilder()
                    .addAllRepositories(repos.stream().map(mapper::toProtoRepo).toList())
                    .build());
            obs.onCompleted();
        } catch (EntityNotFoundException e) {
            obs.onError(Status.NOT_FOUND.withDescription(e.getMessage()).asRuntimeException());
        } catch (Exception e) {
            obs.onError(Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public void getFileContent(GetFileContentRequest req, StreamObserver<GetFileContentResponse> obs) {
        try {
            var result = fileService.getFileContent(req.getOwner(), req.getRepo(), req.getPath(),
                    req.getRef().isEmpty() ? null : req.getRef());
            if (result.file() == null) {
                obs.onError(Status.NOT_FOUND.withDescription("Path is a directory, use GetDirectoryContents").asRuntimeException());
                return;
            }
            obs.onNext(GetFileContentResponse.newBuilder()
                    .setFile(mapper.toProtoFile(result.file()))
                    .build());
            obs.onCompleted();
        } catch (EntityNotFoundException e) {
            obs.onError(Status.NOT_FOUND.withDescription(e.getMessage()).asRuntimeException());
        } catch (Exception e) {
            obs.onError(Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public void getDirectoryContents(GetDirectoryContentsRequest req, StreamObserver<GetDirectoryContentsResponse> obs) {
        try {
            var result = fileService.getRepositoryContents(req.getOwner(), req.getRepo(), req.getPath(),
                    req.getRef().isEmpty() ? null : req.getRef());
            if (result.entries() == null) {
                obs.onError(Status.NOT_FOUND.withDescription("Path is a file, use GetFileContent").asRuntimeException());
                return;
            }
            obs.onNext(GetDirectoryContentsResponse.newBuilder()
                    .addAllEntries(result.entries().stream().map(mapper::toProtoEntry).toList())
                    .build());
            obs.onCompleted();
        } catch (EntityNotFoundException e) {
            obs.onError(Status.NOT_FOUND.withDescription(e.getMessage()).asRuntimeException());
        } catch (Exception e) {
            obs.onError(Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }
    }
}
