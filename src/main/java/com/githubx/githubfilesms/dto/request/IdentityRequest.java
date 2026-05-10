package com.githubx.githubfilesms.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record IdentityRequest(
        @NotBlank(message = "El nombre es requerido")
        String name,

        @NotBlank(message = "El email es requerido")
        @Email(message = "Email invalido")
        String email
) {}
