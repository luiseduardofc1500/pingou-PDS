package com.pds.pingou.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pds.pingou.security.user.User;
import com.pds.pingou.security.user.UserMapper;
import com.pds.pingou.security.config.JwtService;
import com.pds.pingou.security.user.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    public AuthenticationService(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, UserMapper userMapper) {
        this.repository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
    }

    public AuthenticationResponseDto register(RegisterRequestDTO request) {
        if (repository.findByEmail(request.email()).isPresent()) {
            throw new EntityExistsException(request.email());
        }
        User user = userMapper.toEntity(request);
        repository.save(user);

        String jwtToken = jwtService.geradorToken(user);
        String jwtRefreshToken = jwtService.geradorRefreshToken(user);

        return new AuthenticationResponseDto(jwtToken, jwtRefreshToken);
    }

    public AuthenticationResponseDto login(LoginRequestDTO request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("Credenciais inválidas");
        }
        var user = repository.findByEmail(request.email())
                .orElseThrow(() -> new EntityNotFoundException(request.email()));

        String jwtToken = jwtService.geradorToken(user);
        String jwtRefreshToken = jwtService.geradorRefreshToken(user);
        return new AuthenticationResponseDto(jwtToken, jwtRefreshToken);
    }




    public void refreshToken(HttpServletRequest request, HttpServletResponse response ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extraiUsernameRefreshToken(refreshToken);
        if (userEmail != null) {
            var userDetails = this.repository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValidRefresh(refreshToken, userDetails)) {
                response.setContentType("application/json");
                var accessToken = jwtService.geradorToken(userDetails);
                var authResponse = new AuthenticationResponseDto(
                        accessToken,
                        refreshToken
                );
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);

            }
        }
    }
}
