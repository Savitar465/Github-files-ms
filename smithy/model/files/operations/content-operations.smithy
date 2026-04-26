$version: "2"

namespace com.github.files

use com.github.common#ForbiddenError
use com.github.common#InternalServerError
use com.github.common#NotFoundError
use com.github.common#UnauthorizedError

@http(method: "GET", uri: "/v1/repos/{owner}/{repo}/contents/{filePath+}", code: 200)
@readonly
@documentation("Obtiene el contenido de un archivo o lista el contenido de un directorio. RF03.3")
operation GetFileContent {
    input: GetFileContentInput
    output: GetFileContentOutput
    errors: [
        UnauthorizedError
        ForbiddenError
        NotFoundError
        InternalServerError
    ]
}

@http(method: "GET", uri: "/v1/repos/{owner}/{repo}/contents", code: 200)
@readonly
@documentation("Lista el contenido de una ruta del repositorio usando query path. HU-13")
operation GetRepositoryContents {
    input: GetRepositoryContentsInput
    output: GetFileContentOutput
    errors: [
        UnauthorizedError
        ForbiddenError
        NotFoundError
        InternalServerError
    ]
}

@http(method: "GET", uri: "/v1/repos/{owner}/{repo}/download", code: 200)
@readonly
@documentation("Descarga un archivo individual del repositorio. RF03.2")
operation GetRawFile {
    input: GetRawFileInput
    output: GetRawFileOutput
    errors: [
        UnauthorizedError
        ForbiddenError
        NotFoundError
        InternalServerError
    ]
}
