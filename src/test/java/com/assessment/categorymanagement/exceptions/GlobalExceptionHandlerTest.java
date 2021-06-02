package com.assessment.categorymanagement.exceptions;

import com.assessment.categorymanagement.common.dto.Response;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

public class GlobalExceptionHandlerTest {

    @Test
    public void defaultExceptionHandler() {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        ResponseEntity<Response<ErrorCode>> response =
                globalExceptionHandler.defaultExceptionHandler(new IllegalArgumentException("Sample"));

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ErrorCode.INTERNAL_SERVER_ERROR.getErrorCode(), response.getBody().getCode());
        assertEquals(ErrorCode.INTERNAL_SERVER_ERROR.getErrorDesc(), response.getBody().getMessage());
    }

    @Test
    public void preconditionExceptionHandler() {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        ResponseEntity<Response<ErrorCode>> response =
                globalExceptionHandler.preconditionExceptionHandler(new PreconditionException(ErrorCode.INVALID_PARENT));

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ErrorCode.INVALID_PARENT.getErrorCode(), response.getBody().getCode());
        assertEquals(ErrorCode.INVALID_PARENT.getErrorDesc(), response.getBody().getMessage());

    }
}