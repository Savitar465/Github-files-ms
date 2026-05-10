package com.githubx.githubfilesms.mapper;

import com.githubx.githubfilesms.dto.request.CreateRepositoryRequest;
import com.githubx.githubfilesms.dto.response.RepositoryResponse;
import com.githubx.githubfilesms.model.RepositoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RepositoryMapper {

    @Mapping(target = "fullName", expression = "java(entity.getOwner() + \"/\" + entity.getName())")
    @Mapping(target = "htmlUrl", expression = "java(\"/v1/repos/\" + entity.getOwner() + \"/\" + entity.getName())")
    RepositoryResponse toResponse(RepositoryEntity entity);

    List<RepositoryResponse> toResponseList(List<RepositoryEntity> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    RepositoryEntity toEntity(CreateRepositoryRequest request);
}
