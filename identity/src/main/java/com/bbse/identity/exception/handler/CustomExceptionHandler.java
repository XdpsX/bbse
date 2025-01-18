package com.bbse.identity.exception.handler;

import com.bbse.identity.dto.ErrorDTO;
import com.bbse.identity.exception.DuplicateException;
import com.bbse.identity.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends AbstractExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDTO> handleNotFoundException(NotFoundException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = ex.getMessage();

        return buildErrorResponse(status, message, ex, request);
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ErrorDTO> handleDuplicateException(DuplicateException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        String message = ex.getMessage();

        return buildErrorResponse(status, message, ex, request);
    }
}
