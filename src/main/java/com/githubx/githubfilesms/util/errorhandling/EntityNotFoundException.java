package com.githubx.githubfilesms.util.errorhandling;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

    public EntityNotFoundException(String entityName, Object id) {
        super(entityName + " no encontrado con id: " + id, HttpStatus.NOT_FOUND);
    }
}
