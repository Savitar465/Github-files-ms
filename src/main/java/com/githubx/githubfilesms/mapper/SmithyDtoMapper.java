package com.githubx.githubfilesms.mapper;

import com.githubx.githubfilesms.dto.request.CreateFileRequest;
import com.githubx.githubfilesms.dto.request.CreateFolderRequest;
import com.githubx.githubfilesms.dto.request.IdentityRequest;
import com.githubx.githubfilesms.dto.request.UpdateFileRequest;
import com.githubx.githubfilesms.dto.response.CommitFileResponse;
import com.githubx.githubfilesms.dto.response.CommitParentResponse;
import com.githubx.githubfilesms.dto.response.CommitResponse;
import com.githubx.githubfilesms.dto.response.CommitSignatureResponse;
import com.githubx.githubfilesms.dto.response.CommitSummaryResponse;
import com.githubx.githubfilesms.dto.response.CompareResponse;
import com.githubx.githubfilesms.dto.response.DeleteFileResponse;
import com.githubx.githubfilesms.dto.response.DirectoryEntryResponse;
import com.githubx.githubfilesms.dto.response.FileContentResponse;
import com.githubx.githubfilesms.dto.response.GetCommitResponse;
import com.githubx.githubfilesms.dto.response.GetFileContentBodyResponse;
import com.githubx.githubfilesms.dto.response.ListCommitsResponse;
import com.smithy.g.files.server.files.model.CommitDTO;
import com.smithy.g.files.server.files.model.CommitFile;
import com.smithy.g.files.server.files.model.CommitParent;
import com.smithy.g.files.server.files.model.CommitSignature;
import com.smithy.g.files.server.files.model.CommitSummaryDTO;
import com.smithy.g.files.server.files.model.CompareDTO;
import com.smithy.g.files.server.files.model.CreateFileBody;
import com.smithy.g.files.server.files.model.CreateFolderBody;
import com.smithy.g.files.server.files.model.DeleteFileResponseBody;
import com.smithy.g.files.server.files.model.DirectoryEntryDTO;
import com.smithy.g.files.server.files.model.FileContentDTO;
import com.smithy.g.files.server.files.model.GetCommitBody;
import com.smithy.g.files.server.files.model.GetFileContentBody;
import com.smithy.g.files.server.files.model.GitObjectType;
import com.smithy.g.files.server.files.model.Identity;
import com.smithy.g.files.server.files.model.ListCommitsBody;
import com.smithy.g.files.server.files.model.UpdateFileBody;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SmithyDtoMapper {

    // Request mappings: Smithy → Internal
    CreateFileRequest toCreateFileRequest(CreateFileBody body);

    CreateFolderRequest toCreateFolderRequest(CreateFolderBody body);

    UpdateFileRequest toUpdateFileRequest(UpdateFileBody body);

    IdentityRequest toIdentityRequest(Identity identity);

    // Response mappings: Internal → Smithy
    GetFileContentBody toGetFileContentBody(GetFileContentBodyResponse response);

    @Mapping(target = "type", source = "type")
    FileContentDTO toFileContentDTO(FileContentResponse response);

    @Mapping(target = "type", source = "type")
    DirectoryEntryDTO toDirectoryEntryDTO(DirectoryEntryResponse response);

    List<DirectoryEntryDTO> toDirectoryEntryDTOList(List<DirectoryEntryResponse> entries);

    com.smithy.g.files.server.files.model.FileOperationResponse toFileOperationResponse(
            com.githubx.githubfilesms.dto.response.FileOperationResponse response);

    CommitSummaryDTO toCommitSummaryDTO(CommitSummaryResponse response);

    CommitSignature toCommitSignature(CommitSignatureResponse response);

    DeleteFileResponseBody toDeleteFileResponseBody(DeleteFileResponse response);

    ListCommitsBody toListCommitsBody(ListCommitsResponse response);

    CommitDTO toCommitDTO(CommitResponse response);

    List<CommitDTO> toCommitDTOList(List<CommitResponse> commits);

    CommitParent toCommitParent(CommitParentResponse response);

    List<CommitParent> toCommitParentList(List<CommitParentResponse> parents);

    com.smithy.g.files.server.files.model.PaginationMeta toPaginationMeta(
            com.githubx.githubfilesms.dto.response.PaginationMeta pagination);

    GetCommitBody toGetCommitBody(GetCommitResponse response);

    CommitFile toCommitFile(CommitFileResponse response);

    List<CommitFile> toCommitFileList(List<CommitFileResponse> files);

    CompareDTO toCompareDTO(CompareResponse response);

    // Enum mapping
    default GitObjectType toGitObjectType(com.githubx.githubfilesms.model.enums.GitObjectType type) {
        if (type == null) return null;
        return switch (type) {
            case FILE -> GitObjectType.FILE;
            case DIRECTORY -> GitObjectType.DIR;
        };
    }

    // BigDecimal/Long conversions
    default BigDecimal toBigDecimal(Long value) {
        return value == null ? null : BigDecimal.valueOf(value);
    }

    default BigDecimal toBigDecimal(Integer value) {
        return value == null ? null : BigDecimal.valueOf(value);
    }
}
