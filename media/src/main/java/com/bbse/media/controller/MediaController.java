package com.bbse.media.controller;

import com.bbse.media.dto.*;
import com.bbse.media.service.MediaService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MediaController {
    private final MediaService mediaService;

    @Operation(description = "Max file size: 2MB")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(
                    schema = @Schema(implementation = ViewMediaDTO.class)
            )),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(
                    schema = @Schema(implementation = ErrorDetailsDTO.class)
            ))
    })
    @PostMapping(path="/media", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<ViewMediaDTO> createMedia(@Valid @ModelAttribute CreateMediaDTO request) {
        System.out.println("Create Media");
        return new ResponseEntity<>(mediaService.createMedia(request), HttpStatus.CREATED);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Deleted"),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(
                    schema = @Schema(implementation = ErrorDTO.class)
            ))
    })
    @DeleteMapping("/media/{id}")
    ResponseEntity<Void> deleteMedia(@PathVariable Long id) {
        mediaService.deleteMedia(id);
        return ResponseEntity.noContent().build();
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(
                    schema = @Schema(implementation = ViewMediaDTO.class)
            )),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(
                    schema = @Schema(implementation = ErrorDTO.class)
            ))
    })
    @GetMapping("/media/{id}")
    ResponseEntity<ViewMediaDTO> getMedia(@PathVariable Long id) {
        return new ResponseEntity<>(mediaService.getMedia(id), HttpStatus.OK);
    }

    @Hidden
    @GetMapping("/media/{id}/file/{fileName}")
    ResponseEntity<InputStreamResource> getMediaFile(@PathVariable Long id, @PathVariable String fileName) {
        FileMediaDTO file = mediaService.getFileMedia(id, fileName);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(file.mediaType())
                .body(new InputStreamResource(file.content()));
    }
}
