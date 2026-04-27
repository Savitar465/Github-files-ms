package com.githubx.githubfilesms.dto.request;

import com.githubx.githubfilesms.model.enums.RepoVisibility;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateRepositoryRequest(
        @NotBlank(message = "El nombre es requerido")
        @Size(min = 1, max = 150, message = "El nombre debe tener entre 1 y 150 caracteres")
        @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "El nombre contiene caracteres invalidos")
        String name,

        @Size(max = 500, message = "La descripcion no puede exceder 500 caracteres")
        String description,

        RepoVisibility visibility,

        String defaultBranch
) {}
