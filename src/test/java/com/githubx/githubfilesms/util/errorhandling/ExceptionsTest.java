package com.githubx.githubfilesms.util.errorhandling;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionsTest {

    // ── BusinessException tests ────────────────────────────────────────

    @Test
    void debeCrearBusinessException() {
        BusinessException ex = new BusinessException("Error de negocio", HttpStatus.BAD_REQUEST);

        assertEquals("Error de negocio", ex.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    }

    // ── EntityNotFoundException tests ──────────────────────────────────

    @Test
    void debeCrearEntityNotFoundException() {
        EntityNotFoundException ex = new EntityNotFoundException("Recurso no encontrado");

        assertEquals("Recurso no encontrado", ex.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
    }

    @Test
    void entityNotFoundExceptionDebeExtenderBusinessException() {
        EntityNotFoundException ex = new EntityNotFoundException("Not found");
        assertTrue(ex instanceof BusinessException);
    }

    // ── BadRequestException tests ──────────────────────────────────────

    @Test
    void debeCrearBadRequestException() {
        BadRequestException ex = new BadRequestException("Solicitud invalida");

        assertEquals("Solicitud invalida", ex.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    }

    @Test
    void badRequestExceptionDebeExtenderBusinessException() {
        BadRequestException ex = new BadRequestException("Bad request");
        assertTrue(ex instanceof BusinessException);
    }

    // ── EntityConflictException tests ──────────────────────────────────

    @Test
    void debeCrearEntityConflictException() {
        EntityConflictException ex = new EntityConflictException("Conflicto de entidad");

        assertEquals("Conflicto de entidad", ex.getMessage());
        assertEquals(HttpStatus.CONFLICT, ex.getStatus());
    }

    @Test
    void entityConflictExceptionDebeExtenderBusinessException() {
        EntityConflictException ex = new EntityConflictException("Conflict");
        assertTrue(ex instanceof BusinessException);
    }

    // ── ForbiddenException tests ───────────────────────────────────────

    @Test
    void debeCrearForbiddenException() {
        ForbiddenException ex = new ForbiddenException("Acceso prohibido");

        assertEquals("Acceso prohibido", ex.getMessage());
        assertEquals(HttpStatus.FORBIDDEN, ex.getStatus());
    }

    @Test
    void forbiddenExceptionDebeExtenderBusinessException() {
        ForbiddenException ex = new ForbiddenException("Forbidden");
        assertTrue(ex instanceof BusinessException);
    }

    // ── ErrorResponse tests ────────────────────────────────────────────

    @Test
    void debeCrearErrorResponseBasico() {
        ErrorResponse error = new ErrorResponse(404, "Not Found", "Recurso no encontrado", "/api/test");

        assertEquals(404, error.status());
        assertEquals("Not Found", error.error());
        assertEquals("Recurso no encontrado", error.message());
        assertEquals("/api/test", error.path());
        assertNull(error.fieldErrors());
    }

    @Test
    void debeCrearErrorResponseConFieldErrors() {
        List<ErrorResponse.FieldError> fieldErrors = List.of(
                new ErrorResponse.FieldError("name", "must not be blank"),
                new ErrorResponse.FieldError("email", "must be a valid email")
        );

        ErrorResponse error = new ErrorResponse(
                400, "Bad Request", "Error de validacion", "/api/test", fieldErrors);

        assertEquals(400, error.status());
        assertNotNull(error.fieldErrors());
        assertEquals(2, error.fieldErrors().size());
        assertEquals("name", error.fieldErrors().get(0).field());
        assertEquals("must not be blank", error.fieldErrors().get(0).message());
    }

    @Test
    void debeCrearFieldError() {
        ErrorResponse.FieldError fieldError = new ErrorResponse.FieldError("username", "already exists");

        assertEquals("username", fieldError.field());
        assertEquals("already exists", fieldError.message());
    }

    // ── Exception hierarchy tests ──────────────────────────────────────

    @Test
    void todasLasExcepcionesDebenTenerStatusCorrectos() {
        assertEquals(HttpStatus.NOT_FOUND, new EntityNotFoundException("test").getStatus());
        assertEquals(HttpStatus.BAD_REQUEST, new BadRequestException("test").getStatus());
        assertEquals(HttpStatus.CONFLICT, new EntityConflictException("test").getStatus());
        assertEquals(HttpStatus.FORBIDDEN, new ForbiddenException("test").getStatus());
    }

    @Test
    void excepcionesDebenSerRuntimeExceptions() {
        assertTrue(new EntityNotFoundException("test") instanceof RuntimeException);
        assertTrue(new BadRequestException("test") instanceof RuntimeException);
        assertTrue(new EntityConflictException("test") instanceof RuntimeException);
        assertTrue(new ForbiddenException("test") instanceof RuntimeException);
    }
}
