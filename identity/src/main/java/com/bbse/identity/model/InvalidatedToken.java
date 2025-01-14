package com.bbse.identity.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.ZonedDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invalidated_tokens")
public class InvalidatedToken {
    @Id
    private String token;

    private ZonedDateTime expiredTime;
}
