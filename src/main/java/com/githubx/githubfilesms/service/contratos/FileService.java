package com.githubx.githubfilesms.service.contratos;

import com.githubx.githubfilesms.dto.request.CreateFileRequest;
import com.githubx.githubfilesms.dto.request.CreateFolderRequest;
import com.githubx.githubfilesms.dto.request.UpdateFileRequest;
import com.githubx.githubfilesms.dto.response.DeleteFileResponse;
import com.githubx.githubfilesms.dto.response.FileOperationResponse;
import com.githubx.githubfilesms.dto.response.GetFileContentBodyResponse;

public interface FileService {

    GetFileContentBodyResponse getFileContent(String owner, String repo, String filePath, String ref);

    GetFileContentBodyResponse getRepositoryContents(String owner, String repo, String path, String ref);

    byte[] getRawFile(String owner, String repo, String path, String ref);

    FileOperationResponse createFile(String owner, String repo, String filePath, CreateFileRequest request);

    FileOperationResponse updateFile(String owner, String repo, String filePath, UpdateFileRequest request);

    DeleteFileResponse deleteFile(String owner, String repo, String filePath, String sha, String message, String branch);

    FileOperationResponse createFolder(String owner, String repo, CreateFolderRequest request);
}
