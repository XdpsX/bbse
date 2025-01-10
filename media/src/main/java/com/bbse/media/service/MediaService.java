package com.bbse.media.service;

import com.bbse.media.dto.CreateMediaDTO;
import com.bbse.media.dto.FileMediaDTO;
import com.bbse.media.dto.ViewMediaDTO;

public interface MediaService {
    ViewMediaDTO createMedia(CreateMediaDTO request);
    void deleteMedia(Long id);
    ViewMediaDTO getMedia(Long id);
    FileMediaDTO getFileMedia(Long id, String fileName);
}
