package com.bbse.media.dto;

import com.bbse.media.validation.annotations.FileTypeConstraint;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.*;

public record CreateMediaDTO (
        String caption,
        @NotNull
        @FileTypeConstraint(
                allowedTypes = {IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE, IMAGE_GIF_VALUE},
                message = "File type not allowed. Allowed types are: JPEG, PNG, GIF"
        )
        MultipartFile file,
        String fileNameOverride
) {
}
