$version: "2"

namespace com.github.files

use com.github.common#Identity
use com.github.common#RepoName
use com.github.common#RepoScopedInputMixin
use com.github.common#Username

/// Respuesta de operacion de archivo (create/update/delete)
structure FileOperationResponse {
    @required
    content: FileContentDTO

    @required
    commit: CommitSummaryDTO
}

structure CreateFileInput with [RepoScopedInputMixin] {
    @required
    @httpLabel
    owner: Username

    @required
    @httpLabel
    repo: RepoName

    @required
    @httpLabel
    filePath: String

    @required
    @httpPayload
    body: CreateFileBody
}

structure CreateFileBody {
    /// Contenido binario del archivo (maximo 10MB)
    @required
    content: FileBytes

    /// Mensaje del commit
    @required
    @length(min: 1, max: 500)
    message: String

    /// Branch destino (default: rama por defecto)
    branch: String

    /// Autor del commit
    author: Identity

    /// Committer del commit
    committer: Identity
}

structure CreateFileOutput {
    @required
    @httpPayload
    body: FileOperationResponse
}

structure UpdateFileInput with [RepoScopedInputMixin] {
    @required
    @httpLabel
    owner: Username

    @required
    @httpLabel
    repo: RepoName

    @required
    @httpLabel
    filePath: String

    @required
    @httpPayload
    body: UpdateFileBody
}

structure UpdateFileBody {
    /// SHA actual del archivo (para control de concurrencia)
    @required
    sha: String

    /// Nuevo contenido binario (maximo 10MB)
    @required
    content: FileBytes

    /// Mensaje del commit
    @required
    @length(min: 1, max: 500)
    message: String

    /// Branch destino
    branch: String

    /// Path original si es rename/move
    fromPath: String

    author: Identity

    committer: Identity
}

structure UpdateFileOutput {
    @required
    @httpPayload
    body: FileOperationResponse
}

structure DeleteFileInput with [RepoScopedInputMixin] {
    @required
    @httpLabel
    owner: Username

    @required
    @httpLabel
    repo: RepoName

    @required
    @httpLabel
    filePath: String

    /// SHA del archivo a eliminar (para control de concurrencia)
    @required
    @httpQuery("sha")
    sha: String

    /// Mensaje del commit
    @required
    @httpQuery("message")
    @length(min: 1, max: 500)
    message: String

    /// Branch donde eliminar
    @httpQuery("branch")
    branch: String
}

structure DeleteFileOutput {
    @required
    @httpPayload
    body: DeleteFileResponseBody
}

structure DeleteFileResponseBody {
    @required
    commit: CommitSummaryDTO
}

structure CreateFolderInput with [RepoScopedInputMixin] {
    @required
    @httpLabel
    owner: Username

    @required
    @httpLabel
    repo: RepoName

    @required
    @httpPayload
    body: CreateFolderBody
}

structure CreateFolderBody {
    /// Nombre/ruta de la carpeta
    @required
    @length(min: 1, max: 255)
    @pattern("^[a-zA-Z0-9._/-]+$")
    path: String

    /// Mensaje del commit
    @required
    @length(min: 1, max: 500)
    message: String

    /// Branch destino
    branch: String
}

structure CreateFolderOutput {
    @required
    @httpPayload
    body: FileOperationResponse
}
