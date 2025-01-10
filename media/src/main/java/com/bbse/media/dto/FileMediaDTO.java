package com.bbse.media.dto;

import org.springframework.http.MediaType;

import java.io.InputStream;

public record FileMediaDTO (
        InputStream content,
        MediaType mediaType
) {
}
