package com.bbse.identity.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app.security")
public class SecurityProperties {

    private String accessSecret;
    private Long accessExpirationSeconds;
    private String refreshSecret;
    private Long refreshExpirationSeconds;
}
