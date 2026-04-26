$version: "2"

namespace com.github.files

use aws.protocols#restJson1
use smithy.api#documentation
use smithy.api#httpBearerAuth
use smithy.api#title

@title("Mini-GitHub Files API")
@restJson1
@httpBearerAuth
@documentation("Servicio para contenido de archivos, commits, descargas y comparacion de branches.")
service FilesApi {
    version: "1.0.0"
    operations: [
        GetFileContent
        GetRepositoryContents
        CreateFile
        UpdateFile
        DeleteFile
        CreateFolder
        GetRawFile
        ListCommits
        GetCommit
        GetCommitDiff
        CompareCommits
    ]
}
