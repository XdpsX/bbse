package com.bbse.media.dto;

public record ViewMediaDTO(
        Long id,
        String caption,
        String fileName,
        String fileType,
        String url
) {
}
