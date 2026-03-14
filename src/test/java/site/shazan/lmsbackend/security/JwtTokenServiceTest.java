package site.shazan.lmsbackend.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtTokenServiceTest {

    @Test
    void generatesAndValidatesToken() {
        JwtTokenService tokenService = new JwtTokenService(
                "this-is-a-test-secret-key-with-minimum-32-chars",
                60000,
                "test-issuer"
        );

        UserDetails user = User.withUsername("tester")
                .password("encoded")
                .authorities("ROLE_USER")
                .build();

        String token = tokenService.generateToken(user, Map.of("role", "USER"));

        assertEquals("tester", tokenService.extractUsername(token));
        assertTrue(tokenService.isTokenValid(token, user));
    }
}

