package com.webApp.blog.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webApp.blog.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtServiceTest {

    private final JwtService jwtService = new JwtService(
            new ObjectMapper(),
            "test-secret-with-enough-length-for-hmac",
            15
    );

    @Test
    void generatedTokenContainsSubjectAndValidatesUsername() {
        User user = new User();
        user.setId(1L);
        user.setUsername("author");
        user.setRole("POSTER");

        String token = jwtService.generateToken(user);

        assertEquals("author", jwtService.extractUsername(token));
        assertTrue(jwtService.isTokenValid(token, "author"));
    }

    @Test
    void rejectsTamperedPayload() {
        User user = new User();
        user.setId(1L);
        user.setUsername("author");
        user.setRole("POSTER");

        String token = jwtService.generateToken(user);
        String[] parts = token.split("\\.");
        String tamperedPayload = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString("{\"sub\":\"admin\",\"uid\":1,\"role\":\"ADMIN\",\"iat\":0,\"exp\":9999999999}"
                        .getBytes(StandardCharsets.UTF_8));

        String tamperedToken = parts[0] + "." + tamperedPayload + "." + parts[2];

        assertThrows(BadCredentialsException.class, () -> jwtService.extractUsername(tamperedToken));
    }
}
