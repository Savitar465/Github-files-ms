package com.githubx.githubfilesms.mapper;

import com.githubx.githubfilesms.dto.response.*;
import com.githubx.githubfilesms.model.CommitEntity;
import com.githubx.githubfilesms.model.CommitFileEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CommitMapper {

    @Mapping(target = "author", source = "entity", qualifiedByName = "toAuthorSignature")
    @Mapping(target = "committer", source = "entity", qualifiedByName = "toCommitterSignature")
    @Mapping(target = "htmlUrl", expression = "java(entity.getHtmlUrl())")
    @Mapping(target = "parents", source = "entity", qualifiedByName = "toParents")
    CommitResponse toCommitResponse(CommitEntity entity);

    List<CommitResponse> toCommitResponseList(List<CommitEntity> entities);

    CommitFileResponse toCommitFileResponse(CommitFileEntity entity);

    List<CommitFileResponse> toCommitFileResponseList(List<CommitFileEntity> entities);

    @Named("toAuthorSignature")
    default CommitSignatureResponse toAuthorSignature(CommitEntity entity) {
        return new CommitSignatureResponse(
                entity.getAuthorName(),
                entity.getAuthorEmail(),
                formatInstant(entity.getAuthorDate())
        );
    }

    @Named("toCommitterSignature")
    default CommitSignatureResponse toCommitterSignature(CommitEntity entity) {
        return new CommitSignatureResponse(
                entity.getCommitterName(),
                entity.getCommitterEmail(),
                formatInstant(entity.getCommitterDate())
        );
    }

    @Named("toParents")
    default List<CommitParentResponse> toParents(CommitEntity entity) {
        if (entity.getParentSha() == null) {
            return Collections.emptyList();
        }
        String parentUrl = String.format("/v1/repos/%s/%s/commits/%s",
                entity.getRepository().getOwner(),
                entity.getRepository().getName(),
                entity.getParentSha());
        return List.of(new CommitParentResponse(entity.getParentSha(), parentUrl));
    }

    default String formatInstant(Instant instant) {
        if (instant == null) return null;
        return DateTimeFormatter.ISO_INSTANT.format(instant);
    }
}
