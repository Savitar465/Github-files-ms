package com.githubx.githubfilesms.util.errorhandling;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler exceptionHandler;

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        when(request.getRequestURI()).thenReturn("/v1/repos/owner/repo");
    }

    @Test
    void debeHandlearEntityNotFoundException() {
        EntityNotFoundException ex = new EntityNotFoundException("Archivo no encontrado");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleBusinessException(ex, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(404, response.getBody().status());
        assertEquals("Archivo no encontrado", response.getBody().message());
        assertEquals("/v1/repos/owner/repo", response.getBody().path());
    }

    @Test
    void debeHandlearBadRequestException() {
        BadRequestException ex = new BadRequestException("Parametro invalido");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleBusinessException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().status());
        assertEquals("Parametro invalido", response.getBody().message());
    }

    @Test
    void debeHandlearEntityConflictException() {
        EntityConflictException ex = new EntityConflictException("El archivo ya existe");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleBusinessException(ex, request);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(409, response.getBody().status());
        assertEquals("El archivo ya existe", response.getBody().message());
    }

    @Test
    void debeHandlearForbiddenException() {
        ForbiddenException ex = new ForbiddenException("No tiene permisos");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleBusinessException(ex, request);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(403, response.getBody().status());
        assertEquals("No tiene permisos", response.getBody().message());
    }

    @Test
    void debeHandlearValidationException() {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("object", "field", "must not be null");
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleValidationException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().status());
        assertEquals("Error de validacion", response.getBody().message());
        assertNotNull(response.getBody().fieldErrors());
        assertEquals(1, response.getBody().fieldErrors().size());
        assertEquals("field", response.getBody().fieldErrors().get(0).field());
    }

    @Test
    void debeHandlearTypeMismatchException() {
        MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
        when(ex.getName()).thenReturn("id");
        when(ex.getValue()).thenReturn("invalid");
        when(ex.getMessage()).thenReturn("Failed to convert");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleTypeMismatchException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().status());
        assertTrue(response.getBody().message().contains("id"));
        assertTrue(response.getBody().message().contains("invalid"));
    }

    @Test
    void debeHandlearGenericException() {
        Exception ex = new RuntimeException("Unexpected error");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleGenericException(ex, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(500, response.getBody().status());
        assertEquals("Error interno del servidor", response.getBody().message());
    }
}
