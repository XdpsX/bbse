package com.bbse.identity.dto;

import java.util.Map;

public record ErrorDetailsDTO(
        int status,
        String message,
        Map<String, String> fieldErrors
) {
    public ErrorDetailsDTO(int status, String message){
        this(status, message, Map.of());
    }
}
