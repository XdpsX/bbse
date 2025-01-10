package com.bbse.media.exception.handler;

import com.bbse.media.dto.ErrorDTO;
import com.bbse.media.dto.ErrorDetailsDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Slf4j
public abstract class AbstractExceptionHandler {
    protected static final String ERROR_LOG_FORMAT = "Error info - URI: {}, ErrorCode: {}, Message: {}";
    protected static final String INVALID_REQUEST_INFORMATION_MESSAGE = "Request information is not valid";

    protected ResponseEntity<ErrorDTO> buildErrorResponse(HttpStatus status, String message,
                                                          Exception ex, HttpServletRequest request) {
        logError(status, message, request, ex);
        ErrorDTO error = new ErrorDTO(status.value(), message);
        return ResponseEntity.status(status).body(error);
    }

    protected ResponseEntity<ErrorDetailsDTO> buildErrorDetailsResponse(HttpStatus status, String message,
                                                                        Map<String, String> errors, Exception ex, HttpServletRequest request) {
        logError(status, message, request, ex);
        ErrorDetailsDTO errorDetails = new ErrorDetailsDTO(status.value(), message, errors);
        return ResponseEntity.status(status).body(errorDetails);
    }

    protected void logError(HttpStatus status, String message, HttpServletRequest request, Exception ex) {
        if (request != null) {
            log.error(ERROR_LOG_FORMAT, request.getServletPath(), status.value(), message);
        }
        log.error(message, ex);
    }
}
