$version: "2"

namespace com.github.files

use com.github.common#ForbiddenError
use com.github.common#InternalServerError
use com.github.common#NotFoundError
use com.github.common#UnauthorizedError

@http(method: "GET", uri: "/v1/repos/{owner}/{repo}/commits", code: 200)
@readonly
@documentation("Lista el historial de commits del repositorio. HU-18")
operation ListCommits {
    input: ListCommitsInput
    output: ListCommitsOutput
    errors: [
        UnauthorizedError
        ForbiddenError
        NotFoundError
        InternalServerError
    ]
}

@http(method: "GET", uri: "/v1/repos/{owner}/{repo}/commits/{sha}", code: 200)
@readonly
@documentation("Obtiene el detalle de un commit especifico. HU-18")
operation GetCommit {
    input: GetCommitInput
    output: GetCommitOutput
    errors: [
        UnauthorizedError
        ForbiddenError
        NotFoundError
        InternalServerError
    ]
}

@http(method: "GET", uri: "/v1/repos/{owner}/{repo}/commits/{sha}/diff", code: 200)
@readonly
@documentation("Obtiene el diff de un commit en formato texto. HU-18")
operation GetCommitDiff {
    input: GetCommitDiffInput
    output: GetCommitDiffOutput
    errors: [
        UnauthorizedError
        ForbiddenError
        NotFoundError
        InternalServerError
    ]
}

@http(method: "GET", uri: "/v1/repos/{owner}/{repo}/compare/{baseBranch}/{headBranch}", code: 200)
@readonly
@documentation("Compara dos branches mostrando commits y archivos cambiados. HU-20")
operation CompareCommits {
    input: CompareCommitsInput
    output: CompareCommitsOutput
    errors: [
        UnauthorizedError
        ForbiddenError
        NotFoundError
        InternalServerError
    ]
}
