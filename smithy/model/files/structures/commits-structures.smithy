$version: "2"

namespace com.github.files

use com.github.common#Email
use com.github.common#PaginationMeta
use com.github.common#RepoName
use com.github.common#RepoScopedInputMixin
use com.github.common#Url
use com.github.common#Username

/// Firma de commit (autor o committer)
structure CommitSignature {
    @required
    name: String

    @required
    email: Email

    @required
    date: String
}

/// Commit padre
structure CommitParent {
    @required
    sha: String

    url: Url
}

list CommitParentList {
    member: CommitParent
}

/// Commit completo
structure CommitDTO {
    @required
    sha: String

    @required
    message: String

    @required
    author: CommitSignature

    @required
    committer: CommitSignature

    htmlUrl: Url

    parents: CommitParentList
}

list CommitList {
    member: CommitDTO
}

/// Resumen de commit (para responses de operaciones)
structure CommitSummaryDTO {
    @required
    sha: String

    @required
    message: String

    @required
    author: CommitSignature

    @required
    committer: CommitSignature

    htmlUrl: Url
}

/// Archivo cambiado en un commit o comparacion
structure CommitFile {
    @required
    filename: String

    /// added, modified, deleted, renamed
    @required
    status: String

    additions: Integer

    deletions: Integer

    changes: Integer

    /// Diff del archivo
    patch: String
}

list CommitFileList {
    member: CommitFile
}

/// Comparacion entre branches (HU-20)
structure CompareDTO {
    @required
    commits: CommitList

    @required
    totalCommits: Integer

    @required
    files: CommitFileList

    @required
    aheadBy: Integer

    @required
    behindBy: Integer
}

structure ListCommitsInput with [RepoScopedInputMixin] {
    @required
    @httpLabel
    owner: Username

    @required
    @httpLabel
    repo: RepoName

    /// Branch o SHA desde donde empezar
    @httpQuery("sha")
    sha: String

    /// Filtrar por path de archivo
    @httpQuery("path")
    path: String

    @httpQuery("page")
    @range(min: 1)
    page: Integer

    @httpQuery("perPage")
    @range(min: 1, max: 100)
    perPage: Integer
}

structure ListCommitsOutput {
    @required
    @httpPayload
    body: ListCommitsBody
}

structure ListCommitsBody {
    @required
    commits: CommitList

    @required
    pagination: PaginationMeta
}

structure GetCommitInput with [RepoScopedInputMixin] {
    @required
    @httpLabel
    owner: Username

    @required
    @httpLabel
    repo: RepoName

    @required
    @httpLabel
    sha: String
}

structure GetCommitOutput {
    @required
    @httpPayload
    body: GetCommitBody
}

structure GetCommitBody {
    @required
    commit: CommitDTO

    /// Archivos modificados en este commit
    files: CommitFileList
}

structure GetCommitDiffInput with [RepoScopedInputMixin] {
    @required
    @httpLabel
    owner: Username

    @required
    @httpLabel
    repo: RepoName

    @required
    @httpLabel
    sha: String
}

structure GetCommitDiffOutput {
    @required
    @httpHeader("Content-Type")
    contentType: String

    @required
    @httpPayload
    body: String
}

structure CompareCommitsInput with [RepoScopedInputMixin] {
    @required
    @httpLabel
    owner: Username

    @required
    @httpLabel
    repo: RepoName

    /// Branch base para comparacion
    @required
    @httpLabel
    baseBranch: String

    /// Branch head para comparacion
    @required
    @httpLabel
    headBranch: String
}

structure CompareCommitsOutput {
    @required
    @httpPayload
    body: CompareDTO
}
