package site.shazan.lmsbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import site.shazan.lmsbackend.dto.LoginRequest;
import site.shazan.lmsbackend.dto.LoginResponse;
import site.shazan.lmsbackend.model.User;
import site.shazan.lmsbackend.repository.UserRepository;
import site.shazan.lmsbackend.security.JwtTokenService;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;

    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userDetails.getUsername()));

        String token = jwtTokenService.generateToken(
                userDetails,
                Map.of("role", user.getRole().name(), "userId", user.getId())
        );

        return LoginResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .expiresInSeconds(jwtTokenService.getExpirationSeconds())
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();
    }
}

