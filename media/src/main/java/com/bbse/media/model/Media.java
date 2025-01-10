package com.bbse.media.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "media")
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String caption;

    private String fileName;

    private String filePath;

    private String fileType;

    public String getUrl(){
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(String.format("/media/%1$s/file/%2$s", id, fileName))
                .build().toUriString();
    }
}
