package com.bbse.identity.dto;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}
