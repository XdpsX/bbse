package com.bbse.media.exception.handler;

import com.bbse.media.dto.ErrorDTO;
import com.bbse.media.dto.ErrorDetailsDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends AbstractExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorDTO> handleMaxUploadSizeExceededException(final MaxUploadSizeExceededException ex,
                                                                         final HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = ex.getMessage();

        return buildErrorResponse(status, message, ex, request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetailsDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                                 HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        List<FieldError> fieldErrorsList = ex.getBindingResult().getFieldErrors();
        Map<String, String> fieldErrors = new HashMap<>();

        for (FieldError fieldError : fieldErrorsList) {
            String errorMessage = fieldError.getDefaultMessage();
            if (errorMessage != null) {
                fieldErrors.merge(fieldError.getField(), errorMessage,
                        (existingMessage, newMessage) -> existingMessage + "; " + newMessage);
            }
        }
        return buildErrorDetailsResponse(status, INVALID_REQUEST_INFORMATION_MESSAGE, fieldErrors, ex, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleException(HttpServletRequest request, Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "Internal Server Error";
        return buildErrorResponse(status, message, ex, request);
    }
}
