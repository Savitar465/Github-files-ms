package com.githubx.githubfilesms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateFileRequest(
        @NotNull(message = "El contenido es requerido")
        byte[] content,

        @NotBlank(message = "El mensaje del commit es requerido")
        @Size(min = 1, max = 500, message = "El mensaje debe tener entre 1 y 500 caracteres")
        String message,

        String branch,

        IdentityRequest author,

        IdentityRequest committer
) {}
