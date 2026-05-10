-- Datos de prueba para Github-files-ms

-- Repositorio de ejemplo
INSERT INTO repositories (owner, name, description, visibility, default_branch, created_at, updated_at)
VALUES ('demo-user', 'demo-repo', 'Repositorio de demostracion', 'PUBLIC', 'main', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (owner, name) DO NOTHING;

-- Archivos de ejemplo
INSERT INTO files (repository_id, name, path, sha, type, size, content, encoding, branch, last_commit_sha, created_at, updated_at)
SELECT
    r.id,
    v.name,
    v.path,
    v.sha,
    v.type,
    v.size,
    v.content,
    v.encoding,
    v.branch,
    v.last_commit_sha,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
FROM repositories r
CROSS JOIN (
    VALUES
        ('README.md', 'README.md', 'a1b2c3d4e5f6789012345678901234567890abcd', 'FILE', 45,
         'IyBEZW1vIFJlcG9zaXRvcnkKCkJpZW52ZW5pZG8gYWwgcmVwb3NpdG9yaW8gZGUgZGVtby4=', 'base64', 'main',
         'abc123def456789012345678901234567890abcd'),
        ('src', 'src', 'b2c3d4e5f67890123456789012345678901234ab', 'DIRECTORY', NULL,
         NULL, NULL, 'main', 'abc123def456789012345678901234567890abcd'),
        ('main.java', 'src/main.java', 'c3d4e5f6789012345678901234567890123456bc', 'FILE', 120,
         'cHVibGljIGNsYXNzIE1haW4gewogICAgcHVibGljIHN0YXRpYyB2b2lkIG1haW4oU3RyaW5nW10gYXJncykgewogICAgICAgIFN5c3RlbS5vdXQucHJpbnRsbigiSGVsbG8gV29ybGQhIik7CiAgICB9Cn0=',
         'base64', 'main', 'abc123def456789012345678901234567890abcd')
) AS v(name, path, sha, type, size, content, encoding, branch, last_commit_sha)
WHERE r.owner = 'demo-user' AND r.name = 'demo-repo'
ON CONFLICT (repository_id, path, branch) DO NOTHING;

-- Commit de ejemplo
INSERT INTO commits (repository_id, sha, message, author_name, author_email, author_date,
                    committer_name, committer_email, committer_date, parent_sha, branch, created_at)
SELECT
    r.id,
    'abc123def456789012345678901234567890abcd',
    'Initial commit',
    'Demo User',
    'demo@example.com',
    CURRENT_TIMESTAMP,
    'Demo User',
    'demo@example.com',
    CURRENT_TIMESTAMP,
    NULL,
    'main',
    CURRENT_TIMESTAMP
FROM repositories r
WHERE r.owner = 'demo-user' AND r.name = 'demo-repo'
ON CONFLICT (repository_id, sha) DO NOTHING;

-- Archivos del commit
INSERT INTO commit_files (commit_id, filename, status, additions, deletions, changes)
SELECT
    c.id,
    v.filename,
    v.status,
    v.additions,
    v.deletions,
    v.changes
FROM commits c
CROSS JOIN (
    VALUES
        ('README.md', 'added', 3, 0, 3),
        ('src/main.java', 'added', 6, 0, 6)
) AS v(filename, status, additions, deletions, changes)
WHERE c.sha = 'abc123def456789012345678901234567890abcd'
AND NOT EXISTS (
    SELECT 1
    FROM commit_files cf
    WHERE cf.commit_id = c.id
      AND cf.filename = v.filename
      AND cf.status = v.status
);
