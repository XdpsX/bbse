package com.bbse.media.mapper;

import com.bbse.media.dto.ViewMediaDTO;
import com.bbse.media.model.Media;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MediaMapper {
    MediaMapper INSTANCE = Mappers.getMapper(MediaMapper.class);

    ViewMediaDTO toViewMediaDTO(Media media);
}
