$version: "2"

namespace com.github.files

use com.github.common#GitObjectType
use com.github.common#RepoName
use com.github.common#RepoScopedInputMixin
use com.github.common#Url
use com.github.common#Username

@length(max: 10485760)
blob FileBytes

/// Contenido de archivo (respuesta GET para archivos)
structure FileContentDTO {
    @required
    name: String

    @required
    path: String

    @required
    sha: String

    @required
    type: GitObjectType

    size: Long

    /// "base64" para archivos con contenido
    encoding: String

    /// Contenido del archivo en base64
    content: String

    @required
    downloadUrl: Url

    htmlUrl: Url

    /// SHA del ultimo commit que modifico el archivo
    lastCommitSha: String
}

/// Entrada de directorio (para listar contenido de carpetas)
structure DirectoryEntryDTO {
    @required
    name: String

    @required
    path: String

    @required
    sha: String

    @required
    type: GitObjectType

    size: Long

    downloadUrl: Url
}

list DirectoryEntryList {
    member: DirectoryEntryDTO
}

structure GetFileContentInput with [RepoScopedInputMixin] {
    @required
    @httpLabel
    owner: Username

    @required
    @httpLabel
    repo: RepoName

    @required
    @httpLabel
    filePath: String

    /// Branch, tag o commit SHA
    @httpQuery("ref")
    ref: String
}

structure GetFileContentOutput {
    @required
    @httpPayload
    body: GetFileContentBody
}

/// Respuesta polimorfica: archivo o lista de entradas
structure GetFileContentBody {
    /// Si es archivo, contiene los datos del archivo
    file: FileContentDTO

    /// Si es directorio, contiene la lista de entradas
    entries: DirectoryEntryList
}

structure GetRepositoryContentsInput with [RepoScopedInputMixin] {
    @required
    @httpLabel
    owner: Username

    @required
    @httpLabel
    repo: RepoName

    /// Ruta relativa opcional. Si se omite, lista la raiz.
    @httpQuery("path")
    path: String

    /// Branch, tag o commit SHA
    @httpQuery("ref")
    ref: String
}

structure GetRawFileInput with [RepoScopedInputMixin] {
    @required
    @httpLabel
    owner: Username

    @required
    @httpLabel
    repo: RepoName

    @required
    @httpQuery("path")
    path: String

    /// Branch, tag o commit SHA
    @httpQuery("ref")
    ref: String
}

structure GetRawFileOutput {
    @required
    @httpHeader("Content-Type")
    contentType: String

    @required
    @httpHeader("Content-Disposition")
    contentDisposition: String

    @required
    @httpPayload
    body: FileBytes
}
