package com.githubx.githubfilesms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateFolderRequest(
        @NotBlank(message = "El path de la carpeta es requerido")
        @Size(min = 1, max = 255, message = "El path debe tener entre 1 y 255 caracteres")
        @Pattern(regexp = "^[a-zA-Z0-9._/-]+$", message = "El path contiene caracteres invalidos")
        String path,

        @NotBlank(message = "El mensaje del commit es requerido")
        @Size(min = 1, max = 500, message = "El mensaje debe tener entre 1 y 500 caracteres")
        String message,

        String branch
) {}
