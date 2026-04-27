-- Schema para Github-files-ms
-- Base de datos: PostgreSQL

-- Tabla de repositorios
CREATE TABLE IF NOT EXISTS repositories (
    id BIGSERIAL PRIMARY KEY,
    owner VARCHAR(50) NOT NULL,
    name VARCHAR(150) NOT NULL,
    description VARCHAR(500),
    visibility VARCHAR(10) NOT NULL DEFAULT 'PRIVATE',
    default_branch VARCHAR(100) DEFAULT 'main',
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_repositories_owner_name UNIQUE (owner, name)
);

-- Tabla de archivos
CREATE TABLE IF NOT EXISTS files (
    id BIGSERIAL PRIMARY KEY,
    repository_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    path VARCHAR(1000) NOT NULL,
    sha VARCHAR(40) NOT NULL,
    type VARCHAR(10) NOT NULL,
    size BIGINT,
    content TEXT,
    encoding VARCHAR(20),
    branch VARCHAR(100) DEFAULT 'main',
    last_commit_sha VARCHAR(40),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_files_repository FOREIGN KEY (repository_id) REFERENCES repositories(id) ON DELETE CASCADE,
    CONSTRAINT uk_files_repo_path_branch UNIQUE (repository_id, path, branch)
);

-- Tabla de commits
CREATE TABLE IF NOT EXISTS commits (
    id BIGSERIAL PRIMARY KEY,
    repository_id BIGINT NOT NULL,
    sha VARCHAR(40) NOT NULL,
    message TEXT NOT NULL,
    author_name VARCHAR(100) NOT NULL,
    author_email VARCHAR(255) NOT NULL,
    author_date TIMESTAMP WITH TIME ZONE NOT NULL,
    committer_name VARCHAR(100) NOT NULL,
    committer_email VARCHAR(255) NOT NULL,
    committer_date TIMESTAMP WITH TIME ZONE NOT NULL,
    parent_sha VARCHAR(40),
    branch VARCHAR(100),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_commits_repository FOREIGN KEY (repository_id) REFERENCES repositories(id) ON DELETE CASCADE,
    CONSTRAINT uk_commits_repo_sha UNIQUE (repository_id, sha)
);

-- Tabla de archivos modificados en commits
CREATE TABLE IF NOT EXISTS commit_files (
    id BIGSERIAL PRIMARY KEY,
    commit_id BIGINT NOT NULL,
    filename VARCHAR(500) NOT NULL,
    status VARCHAR(20) NOT NULL,
    additions INTEGER DEFAULT 0,
    deletions INTEGER DEFAULT 0,
    changes INTEGER DEFAULT 0,
    patch TEXT,
    CONSTRAINT fk_commit_files_commit FOREIGN KEY (commit_id) REFERENCES commits(id) ON DELETE CASCADE
);

-- Indices para mejorar performance
CREATE INDEX IF NOT EXISTS idx_files_repository_branch ON files(repository_id, branch);
CREATE INDEX IF NOT EXISTS idx_commits_repository_branch ON commits(repository_id, branch);
CREATE INDEX IF NOT EXISTS idx_commits_committer_date ON commits(committer_date DESC);
CREATE INDEX IF NOT EXISTS idx_commit_files_commit ON commit_files(commit_id);
