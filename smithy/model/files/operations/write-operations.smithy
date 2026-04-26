$version: "2"

namespace com.github.files

use com.github.common#BadRequestError
use com.github.common#ConflictError
use com.github.common#ForbiddenError
use com.github.common#InternalServerError
use com.github.common#NotFoundError
use com.github.common#UnauthorizedError

@http(method: "PUT", uri: "/v1/repos/{owner}/{repo}/contents/{filePath+}", code: 201)
@idempotent
@documentation("Sube un archivo al repositorio (create/update por path). RF03.1")
operation CreateFile {
    input: CreateFileInput
    output: CreateFileOutput
    errors: [
        BadRequestError
        UnauthorizedError
        ForbiddenError
        NotFoundError
        ConflictError
        InternalServerError
    ]
}

@http(method: "PATCH", uri: "/v1/repos/{owner}/{repo}/contents/{filePath+}", code: 200)
@documentation("Actualiza un archivo existente en el repositorio. RF03.1")
operation UpdateFile {
    input: UpdateFileInput
    output: UpdateFileOutput
    errors: [
        BadRequestError
        UnauthorizedError
        ForbiddenError
        NotFoundError
        ConflictError
        InternalServerError
    ]
}

@http(method: "DELETE", uri: "/v1/repos/{owner}/{repo}/contents/{filePath+}", code: 200)
@idempotent
@documentation("Elimina un archivo generando un commit de borrado. RF03.5")
operation DeleteFile {
    input: DeleteFileInput
    output: DeleteFileOutput
    errors: [
        BadRequestError
        UnauthorizedError
        ForbiddenError
        NotFoundError
        InternalServerError
    ]
}

@http(method: "POST", uri: "/v1/repos/{owner}/{repo}/folders", code: 201)
@documentation("Crea una carpeta nueva con .gitkeep. RF03.4")
operation CreateFolder {
    input: CreateFolderInput
    output: CreateFolderOutput
    errors: [
        BadRequestError
        UnauthorizedError
        ForbiddenError
        NotFoundError
        ConflictError
        InternalServerError
    ]
}
