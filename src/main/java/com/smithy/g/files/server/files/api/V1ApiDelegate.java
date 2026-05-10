package com.smithy.g.files.server.files.api;

import com.smithy.g.files.server.files.model.BadRequestErrorResponseContent;
import java.math.BigDecimal;
import com.smithy.g.files.server.files.model.CompareDTO;
import com.smithy.g.files.server.files.model.ConflictErrorResponseContent;
import com.smithy.g.files.server.files.model.CreateFileBody;
import com.smithy.g.files.server.files.model.CreateFolderBody;
import com.smithy.g.files.server.files.model.DeleteFileResponseBody;
import com.smithy.g.files.server.files.model.FileOperationResponse;
import com.smithy.g.files.server.files.model.ForbiddenErrorResponseContent;
import com.smithy.g.files.server.files.model.GetCommitBody;
import com.smithy.g.files.server.files.model.GetFileContentBody;
import com.smithy.g.files.server.files.model.InternalServerErrorResponseContent;
import com.smithy.g.files.server.files.model.ListCommitsBody;
import com.smithy.g.files.server.files.model.NotFoundErrorResponseContent;
import com.smithy.g.files.server.files.model.UnauthorizedErrorResponseContent;
import com.smithy.g.files.server.files.model.UpdateFileBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

/**
 * A delegate to be called by the {@link V1ApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.5.0")
public interface V1ApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /v1/repos/{owner}/{repo}/compare/{baseBranch}/{headBranch}
     * Compara dos branches mostrando commits y archivos cambiados. HU-20
     *
     * @param owner  (required)
     * @param repo  (required)
     * @param baseBranch Branch base para comparacion (required)
     * @param headBranch Branch head para comparacion (required)
     * @return CompareCommits 200 response (status code 200)
     *         or UnauthorizedError 401 response (status code 401)
     *         or ForbiddenError 403 response (status code 403)
     *         or NotFoundError 404 response (status code 404)
     *         or InternalServerError 500 response (status code 500)
     * @see V1Api#compareCommits
     */
    default ResponseEntity<CompareDTO> compareCommits(String owner,
        String repo,
        String baseBranch,
        String headBranch) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"totalCommits\" : 0.8008281904610115, \"commits\" : [ { \"committer\" : { \"date\" : \"date\", \"name\" : \"name\", \"email\" : \"email\" }, \"author\" : { \"date\" : \"date\", \"name\" : \"name\", \"email\" : \"email\" }, \"htmlUrl\" : \"htmlUrl\", \"message\" : \"message\", \"sha\" : \"sha\", \"parents\" : [ { \"sha\" : \"sha\", \"url\" : \"url\" }, { \"sha\" : \"sha\", \"url\" : \"url\" } ] }, { \"committer\" : { \"date\" : \"date\", \"name\" : \"name\", \"email\" : \"email\" }, \"author\" : { \"date\" : \"date\", \"name\" : \"name\", \"email\" : \"email\" }, \"htmlUrl\" : \"htmlUrl\", \"message\" : \"message\", \"sha\" : \"sha\", \"parents\" : [ { \"sha\" : \"sha\", \"url\" : \"url\" }, { \"sha\" : \"sha\", \"url\" : \"url\" } ] } ], \"files\" : [ { \"patch\" : \"patch\", \"filename\" : \"filename\", \"additions\" : 0.8008281904610115, \"deletions\" : 6.027456183070403, \"changes\" : 1.4658129805029452, \"status\" : \"status\" }, { \"patch\" : \"patch\", \"filename\" : \"filename\", \"additions\" : 0.8008281904610115, \"deletions\" : 6.027456183070403, \"changes\" : 1.4658129805029452, \"status\" : \"status\" } ], \"aheadBy\" : 6.027456183070403, \"behindBy\" : 1.4658129805029452 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * PUT /v1/repos/{owner}/{repo}/contents/{filePath+}
     * Sube un archivo al repositorio (create/update por path). RF03.1
     *
     * @param owner  (required)
     * @param repo  (required)
     * @param filePathPlus  (required)
     * @param createFileBody  (required)
     * @return CreateFile 201 response (status code 201)
     *         or BadRequestError 400 response (status code 400)
     *         or UnauthorizedError 401 response (status code 401)
     *         or ForbiddenError 403 response (status code 403)
     *         or NotFoundError 404 response (status code 404)
     *         or ConflictError 409 response (status code 409)
     *         or InternalServerError 500 response (status code 500)
     * @see V1Api#createFile
     */
    default ResponseEntity<FileOperationResponse> createFile(String owner,
        String repo,
        String filePathPlus,
        CreateFileBody createFileBody) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"commit\" : { \"committer\" : { \"date\" : \"date\", \"name\" : \"name\", \"email\" : \"email\" }, \"author\" : { \"date\" : \"date\", \"name\" : \"name\", \"email\" : \"email\" }, \"htmlUrl\" : \"htmlUrl\", \"message\" : \"message\", \"sha\" : \"sha\" }, \"content\" : { \"path\" : \"path\", \"size\" : 0.8008281904610115, \"lastCommitSha\" : \"lastCommitSha\", \"name\" : \"name\", \"downloadUrl\" : \"downloadUrl\", \"htmlUrl\" : \"htmlUrl\", \"encoding\" : \"encoding\", \"sha\" : \"sha\", \"content\" : \"content\" } }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /v1/repos/{owner}/{repo}/folders
     * Crea una carpeta nueva con .gitkeep. RF03.4
     *
     * @param owner  (required)
     * @param repo  (required)
     * @param createFolderBody  (required)
     * @return CreateFolder 201 response (status code 201)
     *         or BadRequestError 400 response (status code 400)
     *         or UnauthorizedError 401 response (status code 401)
     *         or ForbiddenError 403 response (status code 403)
     *         or NotFoundError 404 response (status code 404)
     *         or ConflictError 409 response (status code 409)
     *         or InternalServerError 500 response (status code 500)
     * @see V1Api#createFolder
     */
    default ResponseEntity<FileOperationResponse> createFolder(String owner,
        String repo,
        CreateFolderBody createFolderBody) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"commit\" : { \"committer\" : { \"date\" : \"date\", \"name\" : \"name\", \"email\" : \"email\" }, \"author\" : { \"date\" : \"date\", \"name\" : \"name\", \"email\" : \"email\" }, \"htmlUrl\" : \"htmlUrl\", \"message\" : \"message\", \"sha\" : \"sha\" }, \"content\" : { \"path\" : \"path\", \"size\" : 0.8008281904610115, \"lastCommitSha\" : \"lastCommitSha\", \"name\" : \"name\", \"downloadUrl\" : \"downloadUrl\", \"htmlUrl\" : \"htmlUrl\", \"encoding\" : \"encoding\", \"sha\" : \"sha\", \"content\" : \"content\" } }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * DELETE /v1/repos/{owner}/{repo}/contents/{filePath+}
     * Elimina un archivo generando un commit de borrado. RF03.5
     *
     * @param owner  (required)
     * @param repo  (required)
     * @param filePathPlus  (required)
     * @param sha SHA del archivo a eliminar (para control de concurrencia) (required)
     * @param message Mensaje del commit (required)
     * @param branch Branch donde eliminar (optional)
     * @return DeleteFile 200 response (status code 200)
     *         or BadRequestError 400 response (status code 400)
     *         or UnauthorizedError 401 response (status code 401)
     *         or ForbiddenError 403 response (status code 403)
     *         or NotFoundError 404 response (status code 404)
     *         or InternalServerError 500 response (status code 500)
     * @see V1Api#deleteFile
     */
    default ResponseEntity<DeleteFileResponseBody> deleteFile(String owner,
        String repo,
        String filePathPlus,
        String sha,
        String message,
        String branch) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"commit\" : { \"committer\" : { \"date\" : \"date\", \"name\" : \"name\", \"email\" : \"email\" }, \"author\" : { \"date\" : \"date\", \"name\" : \"name\", \"email\" : \"email\" }, \"htmlUrl\" : \"htmlUrl\", \"message\" : \"message\", \"sha\" : \"sha\" } }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /v1/repos/{owner}/{repo}/commits/{sha}
     * Obtiene el detalle de un commit especifico. HU-18
     *
     * @param owner  (required)
     * @param repo  (required)
     * @param sha  (required)
     * @return GetCommit 200 response (status code 200)
     *         or UnauthorizedError 401 response (status code 401)
     *         or ForbiddenError 403 response (status code 403)
     *         or NotFoundError 404 response (status code 404)
     *         or InternalServerError 500 response (status code 500)
     * @see V1Api#getCommit
     */
    default ResponseEntity<GetCommitBody> getCommit(String owner,
        String repo,
        String sha) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"commit\" : { \"committer\" : { \"date\" : \"date\", \"name\" : \"name\", \"email\" : \"email\" }, \"author\" : { \"date\" : \"date\", \"name\" : \"name\", \"email\" : \"email\" }, \"htmlUrl\" : \"htmlUrl\", \"message\" : \"message\", \"sha\" : \"sha\", \"parents\" : [ { \"sha\" : \"sha\", \"url\" : \"url\" }, { \"sha\" : \"sha\", \"url\" : \"url\" } ] }, \"files\" : [ { \"patch\" : \"patch\", \"filename\" : \"filename\", \"additions\" : 0.8008281904610115, \"deletions\" : 6.027456183070403, \"changes\" : 1.4658129805029452, \"status\" : \"status\" }, { \"patch\" : \"patch\", \"filename\" : \"filename\", \"additions\" : 0.8008281904610115, \"deletions\" : 6.027456183070403, \"changes\" : 1.4658129805029452, \"status\" : \"status\" } ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /v1/repos/{owner}/{repo}/commits/{sha}/diff
     * Obtiene el diff de un commit en formato texto. HU-18
     *
     * @param owner  (required)
     * @param repo  (required)
     * @param sha  (required)
     * @return GetCommitDiff 200 response (status code 200)
     *         or UnauthorizedError 401 response (status code 401)
     *         or ForbiddenError 403 response (status code 403)
     *         or NotFoundError 404 response (status code 404)
     *         or InternalServerError 500 response (status code 500)
     * @see V1Api#getCommitDiff
     */
    default ResponseEntity<String> getCommitDiff(String owner,
        String repo,
        String sha) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /v1/repos/{owner}/{repo}/contents/{filePath+}
     * Obtiene el contenido de un archivo o lista el contenido de un directorio. RF03.3
     *
     * @param owner  (required)
     * @param repo  (required)
     * @param filePathPlus  (required)
     * @param ref Branch, tag o commit SHA (optional)
     * @return GetFileContent 200 response (status code 200)
     *         or UnauthorizedError 401 response (status code 401)
     *         or ForbiddenError 403 response (status code 403)
     *         or NotFoundError 404 response (status code 404)
     *         or InternalServerError 500 response (status code 500)
     * @see V1Api#getFileContent
     */
    default ResponseEntity<GetFileContentBody> getFileContent(String owner,
        String repo,
        String filePathPlus,
        String ref) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"entries\" : [ { \"path\" : \"path\", \"size\" : 6.027456183070403, \"name\" : \"name\", \"downloadUrl\" : \"downloadUrl\", \"sha\" : \"sha\" }, { \"path\" : \"path\", \"size\" : 6.027456183070403, \"name\" : \"name\", \"downloadUrl\" : \"downloadUrl\", \"sha\" : \"sha\" } ], \"file\" : { \"path\" : \"path\", \"size\" : 0.8008281904610115, \"lastCommitSha\" : \"lastCommitSha\", \"name\" : \"name\", \"downloadUrl\" : \"downloadUrl\", \"htmlUrl\" : \"htmlUrl\", \"encoding\" : \"encoding\", \"sha\" : \"sha\", \"content\" : \"content\" } }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /v1/repos/{owner}/{repo}/download
     * Descarga un archivo individual del repositorio. RF03.2
     *
     * @param owner  (required)
     * @param repo  (required)
     * @param path  (required)
     * @param ref Branch, tag o commit SHA (optional)
     * @return GetRawFile 200 response (status code 200)
     *         or UnauthorizedError 401 response (status code 401)
     *         or ForbiddenError 403 response (status code 403)
     *         or NotFoundError 404 response (status code 404)
     *         or InternalServerError 500 response (status code 500)
     * @see V1Api#getRawFile
     */
    default ResponseEntity<byte[]> getRawFile(String owner,
        String repo,
        String path,
        String ref) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /v1/repos/{owner}/{repo}/contents
     * Lista el contenido de una ruta del repositorio usando query path. HU-13
     *
     * @param owner  (required)
     * @param repo  (required)
     * @param path Ruta relativa opcional. Si se omite, lista la raiz. (optional)
     * @param ref Branch, tag o commit SHA (optional)
     * @return GetRepositoryContents 200 response (status code 200)
     *         or UnauthorizedError 401 response (status code 401)
     *         or ForbiddenError 403 response (status code 403)
     *         or NotFoundError 404 response (status code 404)
     *         or InternalServerError 500 response (status code 500)
     * @see V1Api#getRepositoryContents
     */
    default ResponseEntity<GetFileContentBody> getRepositoryContents(String owner,
        String repo,
        String path,
        String ref) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"entries\" : [ { \"path\" : \"path\", \"size\" : 6.027456183070403, \"name\" : \"name\", \"downloadUrl\" : \"downloadUrl\", \"sha\" : \"sha\" }, { \"path\" : \"path\", \"size\" : 6.027456183070403, \"name\" : \"name\", \"downloadUrl\" : \"downloadUrl\", \"sha\" : \"sha\" } ], \"file\" : { \"path\" : \"path\", \"size\" : 0.8008281904610115, \"lastCommitSha\" : \"lastCommitSha\", \"name\" : \"name\", \"downloadUrl\" : \"downloadUrl\", \"htmlUrl\" : \"htmlUrl\", \"encoding\" : \"encoding\", \"sha\" : \"sha\", \"content\" : \"content\" } }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /v1/repos/{owner}/{repo}/commits
     * Lista el historial de commits del repositorio. HU-18
     *
     * @param owner  (required)
     * @param repo  (required)
     * @param sha Branch o SHA desde donde empezar (optional)
     * @param path Filtrar por path de archivo (optional)
     * @param page  (optional)
     * @param perPage  (optional)
     * @return ListCommits 200 response (status code 200)
     *         or UnauthorizedError 401 response (status code 401)
     *         or ForbiddenError 403 response (status code 403)
     *         or NotFoundError 404 response (status code 404)
     *         or InternalServerError 500 response (status code 500)
     * @see V1Api#listCommits
     */
    default ResponseEntity<ListCommitsBody> listCommits(String owner,
        String repo,
        String sha,
        String path,
        BigDecimal page,
        BigDecimal perPage) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"pagination\" : { \"total\" : 1.4658129805029452, \"perPage\" : 6.027456183070403, \"totalPages\" : 5.962133916683182, \"page\" : 0.8008281904610115 }, \"commits\" : [ { \"committer\" : { \"date\" : \"date\", \"name\" : \"name\", \"email\" : \"email\" }, \"author\" : { \"date\" : \"date\", \"name\" : \"name\", \"email\" : \"email\" }, \"htmlUrl\" : \"htmlUrl\", \"message\" : \"message\", \"sha\" : \"sha\", \"parents\" : [ { \"sha\" : \"sha\", \"url\" : \"url\" }, { \"sha\" : \"sha\", \"url\" : \"url\" } ] }, { \"committer\" : { \"date\" : \"date\", \"name\" : \"name\", \"email\" : \"email\" }, \"author\" : { \"date\" : \"date\", \"name\" : \"name\", \"email\" : \"email\" }, \"htmlUrl\" : \"htmlUrl\", \"message\" : \"message\", \"sha\" : \"sha\", \"parents\" : [ { \"sha\" : \"sha\", \"url\" : \"url\" }, { \"sha\" : \"sha\", \"url\" : \"url\" } ] } ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * PATCH /v1/repos/{owner}/{repo}/contents/{filePath+}
     * Actualiza un archivo existente en el repositorio. RF03.1
     *
     * @param owner  (required)
     * @param repo  (required)
     * @param filePathPlus  (required)
     * @param updateFileBody  (required)
     * @return UpdateFile 200 response (status code 200)
     *         or BadRequestError 400 response (status code 400)
     *         or UnauthorizedError 401 response (status code 401)
     *         or ForbiddenError 403 response (status code 403)
     *         or NotFoundError 404 response (status code 404)
     *         or ConflictError 409 response (status code 409)
     *         or InternalServerError 500 response (status code 500)
     * @see V1Api#updateFile
     */
    default ResponseEntity<FileOperationResponse> updateFile(String owner,
        String repo,
        String filePathPlus,
        UpdateFileBody updateFileBody) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"commit\" : { \"committer\" : { \"date\" : \"date\", \"name\" : \"name\", \"email\" : \"email\" }, \"author\" : { \"date\" : \"date\", \"name\" : \"name\", \"email\" : \"email\" }, \"htmlUrl\" : \"htmlUrl\", \"message\" : \"message\", \"sha\" : \"sha\" }, \"content\" : { \"path\" : \"path\", \"size\" : 0.8008281904610115, \"lastCommitSha\" : \"lastCommitSha\", \"name\" : \"name\", \"downloadUrl\" : \"downloadUrl\", \"htmlUrl\" : \"htmlUrl\", \"encoding\" : \"encoding\", \"sha\" : \"sha\", \"content\" : \"content\" } }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
