package com.bbse.media.repository;

import com.bbse.media.model.Media;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MediaRepository extends CrudRepository<Media, Long> {
    Optional<Media> findByIdAndFileName(Long id, String fileName);
}
