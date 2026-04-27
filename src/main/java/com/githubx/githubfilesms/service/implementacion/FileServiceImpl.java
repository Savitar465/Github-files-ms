package com.githubx.githubfilesms.service.implementacion;

import com.githubx.githubfilesms.dao.FileDao;
import com.githubx.githubfilesms.dao.CommitDao;
import com.githubx.githubfilesms.dao.RepositoryDao;
import com.githubx.githubfilesms.dto.request.CreateFileRequest;
import com.githubx.githubfilesms.dto.request.CreateFolderRequest;
import com.githubx.githubfilesms.dto.request.UpdateFileRequest;
import com.githubx.githubfilesms.dto.response.*;
import com.githubx.githubfilesms.mapper.FileMapper;
import com.githubx.githubfilesms.model.FileEntity;
import com.githubx.githubfilesms.model.CommitEntity;
import com.githubx.githubfilesms.model.CommitFileEntity;
import com.githubx.githubfilesms.model.RepositoryEntity;
import com.githubx.githubfilesms.model.enums.GitObjectType;
import com.githubx.githubfilesms.service.contratos.FileService;
import com.githubx.githubfilesms.util.errorhandling.EntityConflictException;
import com.githubx.githubfilesms.util.errorhandling.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileServiceImpl implements FileService {

    private final FileDao fileDao;
    private final CommitDao commitDao;
    private final RepositoryDao repositoryDao;
    private final FileMapper fileMapper;

    @Override
    public GetFileContentBodyResponse getFileContent(String owner, String repo, String filePath, String ref) {
        RepositoryEntity repository = getRepository(owner, repo);
        String branch = ref != null ? ref : repository.getDefaultBranch();

        FileEntity file = fileDao.findByRepositoryAndPathAndBranch(repository, filePath, branch)
                .orElseThrow(() -> new EntityNotFoundException("Archivo", filePath));

        if (file.getType() == GitObjectType.DIRECTORY) {
            List<FileEntity> children = getDirectoryChildren(repository, filePath, branch);
            return GetFileContentBodyResponse.ofDirectory(fileMapper.toDirectoryEntryResponseList(children));
        }

        return GetFileContentBodyResponse.ofFile(fileMapper.toFileContentResponse(file));
    }

    @Override
    public GetFileContentBodyResponse getRepositoryContents(String owner, String repo, String path, String ref) {
        RepositoryEntity repository = getRepository(owner, repo);
        String branch = ref != null ? ref : repository.getDefaultBranch();

        if (path == null || path.isEmpty()) {
            List<FileEntity> rootEntries = fileDao.findRootEntriesByRepositoryAndBranch(repository, branch);
            return GetFileContentBodyResponse.ofDirectory(fileMapper.toDirectoryEntryResponseList(rootEntries));
        }

        return getFileContent(owner, repo, path, ref);
    }

    @Override
    public byte[] getRawFile(String owner, String repo, String path, String ref) {
        RepositoryEntity repository = getRepository(owner, repo);
        String branch = ref != null ? ref : repository.getDefaultBranch();

        FileEntity file = fileDao.findByRepositoryAndPathAndBranch(repository, path, branch)
                .orElseThrow(() -> new EntityNotFoundException("Archivo", path));

        if (file.getType() == GitObjectType.DIRECTORY) {
            throw new EntityNotFoundException("No se puede descargar un directorio");
        }

        if (file.getContent() == null) {
            return new byte[0];
        }

        return Base64.getDecoder().decode(file.getContent());
    }

    @Override
    @Transactional
    public FileOperationResponse createFile(String owner, String repo, String filePath, CreateFileRequest request) {
        RepositoryEntity repository = getRepository(owner, repo);
        String branch = request.branch() != null ? request.branch() : repository.getDefaultBranch();

        if (fileDao.existsByRepositoryAndPathAndBranch(repository, filePath, branch)) {
            throw new EntityConflictException("El archivo ya existe: " + filePath);
        }

        String contentBase64 = Base64.getEncoder().encodeToString(request.content());
        String sha = generateSha(contentBase64);

        String parentCommitSha = resolveParentCommitSha(repository, branch);
        String commitSha = generateCommitSha();

        FileEntity file = FileEntity.builder()
                .repository(repository)
                .name(extractFileName(filePath))
                .path(filePath)
                .sha(sha)
                .type(GitObjectType.FILE)
                .size((long) request.content().length)
                .content(contentBase64)
                .encoding("base64")
                .branch(branch)
                .lastCommitSha(commitSha)
                .build();

        file = fileDao.save(file);
        CommitEntity persistedCommit = saveCommit(repository, commitSha, parentCommitSha, branch,
                request.message(), request.author(), request.committer(), filePath, "added");

        return new FileOperationResponse(
                fileMapper.toFileContentResponse(file),
                createCommitSummary(persistedCommit.getSha(), request.message(), request.author(), request.committer())
        );
    }

    @Override
    @Transactional
    public FileOperationResponse updateFile(String owner, String repo, String filePath, UpdateFileRequest request) {
        RepositoryEntity repository = getRepository(owner, repo);
        String branch = request.branch() != null ? request.branch() : repository.getDefaultBranch();

        FileEntity file = fileDao.findByRepositoryAndPathAndBranch(repository, filePath, branch)
                .orElseThrow(() -> new EntityNotFoundException("Archivo", filePath));

        if (!file.getSha().equals(request.sha())) {
            throw new EntityConflictException("El SHA no coincide. El archivo fue modificado.");
        }

        String contentBase64 = Base64.getEncoder().encodeToString(request.content());
        String newSha = generateSha(contentBase64);

        String parentCommitSha = resolveParentCommitSha(repository, branch);
        String commitSha = generateCommitSha();

        file.setContent(contentBase64);
        file.setSha(newSha);
        file.setSize((long) request.content().length);
        file.setLastCommitSha(commitSha);

        file = fileDao.save(file);
        CommitEntity persistedCommit = saveCommit(repository, commitSha, parentCommitSha, branch,
                request.message(), request.author(), request.committer(), filePath, "modified");

        return new FileOperationResponse(
                fileMapper.toFileContentResponse(file),
                createCommitSummary(persistedCommit.getSha(), request.message(), request.author(), request.committer())
        );
    }

    @Override
    @Transactional
    public DeleteFileResponse deleteFile(String owner, String repo, String filePath, String sha, String message, String branch) {
        RepositoryEntity repository = getRepository(owner, repo);
        String effectiveBranch = branch != null ? branch : repository.getDefaultBranch();

        FileEntity file = fileDao.findByRepositoryAndPathAndBranch(repository, filePath, effectiveBranch)
                .orElseThrow(() -> new EntityNotFoundException("Archivo", filePath));

        if (!file.getSha().equals(sha)) {
            throw new EntityConflictException("El SHA no coincide. El archivo fue modificado.");
        }

        String parentCommitSha = resolveParentCommitSha(repository, effectiveBranch);
        String commitSha = generateCommitSha();
        fileDao.delete(file);
        CommitEntity persistedCommit = saveCommit(repository, commitSha, parentCommitSha, effectiveBranch,
                message, null, null, filePath, "removed");

        return new DeleteFileResponse(
                createCommitSummary(persistedCommit.getSha(), message, null, null)
        );
    }

    @Override
    @Transactional
    public FileOperationResponse createFolder(String owner, String repo, CreateFolderRequest request) {
        RepositoryEntity repository = getRepository(owner, repo);
        String branch = request.branch() != null ? request.branch() : repository.getDefaultBranch();

        String gitkeepPath = request.path() + "/.gitkeep";

        if (fileDao.existsByRepositoryAndPathAndBranch(repository, gitkeepPath, branch)) {
            throw new EntityConflictException("La carpeta ya existe: " + request.path());
        }

        String sha = generateSha("");
        String parentCommitSha = resolveParentCommitSha(repository, branch);
        String commitSha = generateCommitSha();

        FileEntity gitkeep = FileEntity.builder()
                .repository(repository)
                .name(".gitkeep")
                .path(gitkeepPath)
                .sha(sha)
                .type(GitObjectType.FILE)
                .size(0L)
                .content("")
                .encoding("base64")
                .branch(branch)
                .lastCommitSha(commitSha)
                .build();

        gitkeep = fileDao.save(gitkeep);
        CommitEntity persistedCommit = saveCommit(repository, commitSha, parentCommitSha, branch,
                request.message(), null, null, gitkeepPath, "added");

        return new FileOperationResponse(
                fileMapper.toFileContentResponse(gitkeep),
                createCommitSummary(persistedCommit.getSha(), request.message(), null, null)
        );
    }

    private String resolveParentCommitSha(RepositoryEntity repository, String branch) {
        return commitDao.findByRepositoryAndBranchOrderByCommitterDateDesc(repository, branch,
                        org.springframework.data.domain.PageRequest.of(0, 1))
                .stream()
                .findFirst()
                .map(CommitEntity::getSha)
                .orElse(null);
    }

    private CommitEntity saveCommit(RepositoryEntity repository, String commitSha, String parentCommitSha,
            String branch, String message,
            com.githubx.githubfilesms.dto.request.IdentityRequest author,
            com.githubx.githubfilesms.dto.request.IdentityRequest committer,
            String filePath, String status) {
        Instant now = Instant.now();
        String authorName = author != null ? author.name() : "System";
        String authorEmail = author != null ? author.email() : "system@github-files.local";
        String committerName = committer != null ? committer.name() : authorName;
        String committerEmail = committer != null ? committer.email() : authorEmail;

        CommitEntity commit = CommitEntity.builder()
                .repository(repository)
                .sha(commitSha)
                .message(message != null ? message : "File operation")
                .authorName(authorName)
                .authorEmail(authorEmail)
                .authorDate(now)
                .committerName(committerName)
                .committerEmail(committerEmail)
                .committerDate(now)
                .parentSha(parentCommitSha)
                .branch(branch)
                .build();

        CommitFileEntity commitFile = CommitFileEntity.builder()
                .commit(commit)
                .filename(filePath)
                .status(status)
                .additions(0)
                .deletions(0)
                .changes(0)
                .build();

        commit.setFiles(List.of(commitFile));
        return commitDao.save(commit);
    }

    private RepositoryEntity getRepository(String owner, String repo) {
        return repositoryDao.findByOwnerAndName(owner, repo)
                .orElseThrow(() -> new EntityNotFoundException("Repositorio", owner + "/" + repo));
    }

    private List<FileEntity> getDirectoryChildren(RepositoryEntity repository, String parentPath, String branch) {
        String searchPath = parentPath.endsWith("/") ? parentPath : parentPath + "/";
        String deepPath = searchPath + "%/%";
        return fileDao.findDirectChildrenByRepositoryAndBranchAndParentPath(repository, branch, searchPath, deepPath);
    }

    private String extractFileName(String path) {
        int lastSlash = path.lastIndexOf('/');
        return lastSlash >= 0 ? path.substring(lastSlash + 1) : path;
    }

    private String generateSha(String content) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] hash = digest.digest(content.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return UUID.randomUUID().toString().replace("-", "").substring(0, 40);
        }
    }

    private String generateCommitSha() {
        return UUID.randomUUID().toString().replace("-", "") +
                UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }

    private CommitSummaryResponse createCommitSummary(String sha, String message,
            com.githubx.githubfilesms.dto.request.IdentityRequest author,
            com.githubx.githubfilesms.dto.request.IdentityRequest committer) {
        String now = DateTimeFormatter.ISO_INSTANT.format(Instant.now());

        CommitSignatureResponse authorSig = author != null
                ? new CommitSignatureResponse(author.name(), author.email(), now)
                : new CommitSignatureResponse("System", "system@github-files.local", now);

        CommitSignatureResponse committerSig = committer != null
                ? new CommitSignatureResponse(committer.name(), committer.email(), now)
                : authorSig;

        return new CommitSummaryResponse(sha, message, authorSig, committerSig, null);
    }
}
