package com.bbse.identity.security;

import com.bbse.identity.config.SecurityProperties;
import com.bbse.identity.model.User;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {
    private final SecurityProperties securityProperties;

    public String generateAccessToken(User user) {
        return generateToken(user, securityProperties.getAccessSecret(), securityProperties.getAccessExpirationSeconds());
    }

    public String generateRefreshToken(User user) {
        return generateToken(user, securityProperties.getRefreshSecret(), securityProperties.getRefreshExpirationSeconds());
    }

    private String generateToken(User user, String secretKey, long expirationSeconds) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer("xdpsx.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(expirationSeconds, ChronoUnit.SECONDS).toEpochMilli()
                ))
                .claim("scope", user.getRole().getName())
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(secretKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }
}
