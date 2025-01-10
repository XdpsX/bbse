package com.bbse.media.service.impl;

import com.bbse.media.constant.MessageCode;
import com.bbse.media.dto.CreateMediaDTO;
import com.bbse.media.dto.FileMediaDTO;
import com.bbse.media.dto.ViewMediaDTO;
import com.bbse.media.exception.NotFoundException;
import com.bbse.media.mapper.MediaMapper;
import com.bbse.media.model.Media;
import com.bbse.media.repository.FileSystemRepository;
import com.bbse.media.repository.MediaRepository;
import com.bbse.media.service.MediaService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {

    private final MediaRepository mediaRepository;
    private final FileSystemRepository fileSystemRepository;

    @SneakyThrows(IOException.class)
    @Override
    public ViewMediaDTO createMedia(CreateMediaDTO request) {
        Media media = Media.builder()
                .caption(request.caption())
                .fileType(request.file().getContentType())
                .build();
        if (StringUtils.hasText(request.fileNameOverride())) {
            media.setFileName(request.fileNameOverride().trim());
        } else {
            media.setFileName(request.file().getOriginalFilename());
        }
        String filePath = fileSystemRepository.persistFile(request.file().getBytes(), media.getFileName());
        media.setFilePath(filePath);

        return MediaMapper.INSTANCE.toViewMediaDTO(mediaRepository.save(media));
    }

    @Override
    public void deleteMedia(Long id) {
        Media media = mediaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MessageCode.MEDIA_NOT_FOUND, id));
        mediaRepository.deleteById(id);
        fileSystemRepository.deleteFile(media.getFilePath());
    }

    @Override
    public ViewMediaDTO getMedia(Long id) {
        Media media = mediaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MessageCode.MEDIA_NOT_FOUND, id));
        return MediaMapper.INSTANCE.toViewMediaDTO(media);
    }

    @Override
    public FileMediaDTO getFileMedia(Long id, String fileName) {
        Media media = mediaRepository.findByIdAndFileName(id, fileName)
                .orElseThrow(() -> new NotFoundException(MessageCode.MEDIA_NOT_FOUND, id));
        InputStream fileContent = fileSystemRepository.getFile(media.getFilePath());
        MediaType mediaType = MediaType.valueOf(media.getFileType());

        return new FileMediaDTO(fileContent, mediaType);
    }
}
