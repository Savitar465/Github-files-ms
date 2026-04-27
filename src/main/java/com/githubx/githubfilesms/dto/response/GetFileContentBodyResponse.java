package com.githubx.githubfilesms.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record GetFileContentBodyResponse(
        FileContentResponse file,
        List<DirectoryEntryResponse> entries
) {
    public static GetFileContentBodyResponse ofFile(FileContentResponse file) {
        return new GetFileContentBodyResponse(file, null);
    }

    public static GetFileContentBodyResponse ofDirectory(List<DirectoryEntryResponse> entries) {
        return new GetFileContentBodyResponse(null, entries);
    }
}
