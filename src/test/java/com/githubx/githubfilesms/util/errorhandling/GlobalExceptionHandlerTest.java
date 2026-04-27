package com.githubx.githubfilesms.util.errorhandling;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.core.MethodParameter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void debeMapearBusinessException() {
        HttpServletRequest req = request("/v1/r");

        ResponseEntity<ErrorResponse> res = handler.handleBusinessException(
                new EntityNotFoundException("X", 1L), req);

        assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
        assertNotNull(res.getBody());
        assertEquals("/v1/r", res.getBody().path());
    }

    @Test
    void debeMapearValidacion() throws NoSuchMethodException {
        HttpServletRequest req = request("/v1/r");

        BeanPropertyBindingResult br = new BeanPropertyBindingResult(
                new Object(), "obj");
        br.addError(new FieldError("obj", "field", "bad"));

        var parameter = new MethodParameter(
                this.getClass().getDeclaredMethod("dummyParam", String.class), 0);
        var ex = new MethodArgumentNotValidException(parameter, br);

        ResponseEntity<ErrorResponse> res = handler.handleValidationException(ex, req);
        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
        assertNotNull(res.getBody().fieldErrors());
    }

    @Test
    void debeMapearTypeMismatch() {
        HttpServletRequest req = request("/v1/r");
        var ex = new MethodArgumentTypeMismatchException("v", String.class, "k", (MethodParameter) null, new IllegalArgumentException());

        ResponseEntity<ErrorResponse> res = handler.handleTypeMismatchException(ex, req);
        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
    }

    @Test
    void debeMapearAccessDenied() {
        HttpServletRequest req = request("/v1/r");
        ResponseEntity<ErrorResponse> res = handler.handleAccessDeniedException(
                new AccessDeniedException("x"), req);
        assertEquals(HttpStatus.FORBIDDEN, res.getStatusCode());
    }

    @Test
    void debeMapearAuthentication() {
        HttpServletRequest req = request("/v1/r");
        AuthenticationException ex = new AuthenticationCredentialsNotFoundException("x");
        ResponseEntity<ErrorResponse> res = handler.handleAuthenticationException(ex, req);
        assertEquals(HttpStatus.UNAUTHORIZED, res.getStatusCode());
    }

    @Test
    void debeMapearExcepcionGenerica() {
        HttpServletRequest req = request("/v1/r");
        ResponseEntity<ErrorResponse> res = handler.handleGenericException(
                new java.io.IOException("boom"), req);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode());
    }

    private static HttpServletRequest request(String path) {
        MockHttpServletRequest r = new MockHttpServletRequest();
        r.setRequestURI(path);
        return r;
    }

    @SuppressWarnings("unused")
    private void dummyParam(@RequestParam("k") String p) {
        // solo para MethodParameter
    }
}
