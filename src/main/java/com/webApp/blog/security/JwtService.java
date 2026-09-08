package com.webApp.blog.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webApp.blog.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class JwtService {

    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private static final Base64.Encoder URL_ENCODER = Base64.getUrlEncoder().withoutPadding();
    private static final Base64.Decoder URL_DECODER = Base64.getUrlDecoder();

    private final ObjectMapper objectMapper;
    private final String secret;
    private final long expirationSeconds;

    public JwtService(
            ObjectMapper objectMapper,
            @Value("${app.security.jwt.secret:change-this-development-secret-before-production}") String secret,
            @Value("${app.security.jwt.expiration-minutes:60}") long expirationMinutes
    ) {
        this.objectMapper = objectMapper;
        this.secret = secret;
        this.expirationSeconds = Math.max(1, expirationMinutes) * 60;
    }

    public String generateToken(User user) {
        Instant now = Instant.now();
        Map<String, Object> header = new LinkedHashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");

        Map<String, Object> claims = new LinkedHashMap<>();
        claims.put("sub", user.getUsername());
        claims.put("uid", user.getId());
        claims.put("role", user.getRole());
        claims.put("iat", now.getEpochSecond());
        claims.put("exp", now.plusSeconds(expirationSeconds).getEpochSecond());

        String headerSegment = encodeJson(header);
        String payloadSegment = encodeJson(claims);
        String signatureSegment = URL_ENCODER.encodeToString(sign(headerSegment + "." + payloadSegment));

        return headerSegment + "." + payloadSegment + "." + signatureSegment;
    }

    public String extractUsername(String token) {
        Object subject = parseClaims(token).get("sub");
        if (!(subject instanceof String username) || username.isBlank()) {
            throw new BadCredentialsException("Token subject is missing");
        }
        return username;
    }

    public boolean isTokenValid(String token, String username) {
        return username.equals(extractUsername(token));
    }

    public long getExpirationSeconds() {
        return expirationSeconds;
    }

    private Map<String, Object> parseClaims(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new BadCredentialsException("Invalid token format");
        }

        verifySignature(parts[0], parts[1], parts[2]);

        try {
            Map<String, Object> claims = objectMapper.readValue(
                    URL_DECODER.decode(parts[1]),
                    new TypeReference<>() {
                    }
            );
            verifyExpiration(claims);
            return claims;
        } catch (IllegalArgumentException | IOException ex) {
            throw new BadCredentialsException("Invalid token payload", ex);
        }
    }

    private void verifySignature(String header, String payload, String signature) {
        try {
            byte[] expected = sign(header + "." + payload);
            byte[] actual = URL_DECODER.decode(signature);
            if (!MessageDigest.isEqual(expected, actual)) {
                throw new BadCredentialsException("Invalid token signature");
            }
        } catch (IllegalArgumentException ex) {
            throw new BadCredentialsException("Invalid token signature", ex);
        }
    }

    private void verifyExpiration(Map<String, Object> claims) {
        Object expiresAt = claims.get("exp");
        if (!(expiresAt instanceof Number expirationTimestamp)) {
            throw new BadCredentialsException("Token expiration is missing");
        }
        if (Instant.now().getEpochSecond() >= expirationTimestamp.longValue()) {
            throw new CredentialsExpiredException("Token has expired");
        }
    }

    private String encodeJson(Map<String, Object> value) {
        try {
            return URL_ENCODER.encodeToString(objectMapper.writeValueAsBytes(value));
        } catch (IOException ex) {
            throw new IllegalStateException("Unable to encode JWT", ex);
        }
    }

    private byte[] sign(String value) {
        try {
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), HMAC_ALGORITHM));
            return mac.doFinal(value.getBytes(StandardCharsets.UTF_8));
        } catch (Exception ex) {
            throw new IllegalStateException("Unable to sign JWT", ex);
        }
    }
}
