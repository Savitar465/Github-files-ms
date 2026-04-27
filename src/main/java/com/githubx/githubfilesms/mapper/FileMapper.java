package com.githubx.githubfilesms.mapper;

import com.githubx.githubfilesms.dto.response.DirectoryEntryResponse;
import com.githubx.githubfilesms.dto.response.FileContentResponse;
import com.githubx.githubfilesms.model.FileEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FileMapper {

    @Mapping(target = "downloadUrl", expression = "java(entity.getDownloadUrl())")
    @Mapping(target = "htmlUrl", expression = "java(entity.getHtmlUrl())")
    FileContentResponse toFileContentResponse(FileEntity entity);

    @Mapping(target = "downloadUrl", expression = "java(entity.getDownloadUrl())")
    DirectoryEntryResponse toDirectoryEntryResponse(FileEntity entity);

    List<DirectoryEntryResponse> toDirectoryEntryResponseList(List<FileEntity> entities);
}
