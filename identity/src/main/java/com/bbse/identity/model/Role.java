package com.bbse.identity.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    @Id
    private String name;

    private String description;

    public final static String ADMIN = "ADMIN";
    public final static String USER = "USER";

    public final static String PREFIX = "ROLE_";

    public String getAuthority() {
        return PREFIX + name;
    }
}
