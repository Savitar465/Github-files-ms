package com.githubx.githubfilesms.dto.response;

public record PaginationMeta(
        int page,
        int perPage,
        long total,
        int totalPages
) {
    public static PaginationMeta of(int page, int perPage, long total) {
        int totalPages = (int) Math.ceil((double) total / perPage);
        return new PaginationMeta(page, perPage, total, totalPages);
    }
}
