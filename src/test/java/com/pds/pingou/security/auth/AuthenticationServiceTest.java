package com.pds.pingou.security.auth;

import com.pds.pingou.security.auth.dto.AuthenticationResponseDto;
import com.pds.pingou.security.auth.dto.LoginRequestDTO;
import com.pds.pingou.security.auth.dto.RegisterRequestDTO;
import com.pds.pingou.security.config.JwtService;
import com.pds.pingou.security.exception.UserDuplicatedException;
import com.pds.pingou.security.exception.UserNotFoundException;
import com.pds.pingou.security.user.User;
import com.pds.pingou.security.user.UserMapper;
import com.pds.pingou.security.user.UserRepository;
import com.pds.pingou.security.user.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private AuthenticationService authenticationService;

    private RegisterRequestDTO registerRequestDTO;
    private LoginRequestDTO loginRequestDTO;
    private User user;

    @BeforeEach
    void setUp() {
        registerRequestDTO = new RegisterRequestDTO(
                "João",
                "Silva",
                "joao@email.com",
                "password123"
        );

        loginRequestDTO = new LoginRequestDTO(
                "joao@email.com",
                "password123"
        );

        user = new User(
                "joao@email.com",
                "João",
                "Silva",
                "encodedPassword",
                UserRole.USER
        );
        user.setId(1L);
    }

    @Test
    @DisplayName("Deve registrar um novo usuário com sucesso")
    void deveRegistrarNovoUsuarioComSucesso() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userMapper.toEntity(any(RegisterRequestDTO.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtService.geradorToken(any(User.class))).thenReturn("accessToken");
        when(jwtService.geradorRefreshToken(any(User.class))).thenReturn("refreshToken");

        // Act
        AuthenticationResponseDto response = authenticationService.register(registerRequestDTO);

        // Assert
        assertNotNull(response);
        assertEquals("accessToken", response.access_token());
        assertEquals("refreshToken", response.refresh_token());
        verify(userRepository).findByEmail("joao@email.com");
        verify(userRepository).save(user);
        verify(jwtService).geradorToken(user);
        verify(jwtService).geradorRefreshToken(user);
    }

    @Test
    @DisplayName("Deve lançar exceção quando email já existe no registro")
    void deveLancarExcecaoQuandoEmailJaExiste() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        // Act & Assert
        assertThrows(UserDuplicatedException.class, () -> 
                authenticationService.register(registerRequestDTO));
        
        verify(userRepository).findByEmail("joao@email.com");
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve fazer login com sucesso")
    void deveFazerLoginComSucesso() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(jwtService.geradorToken(any(User.class))).thenReturn("accessToken");
        when(jwtService.geradorRefreshToken(any(User.class))).thenReturn("refreshToken");

        // Act
        AuthenticationResponseDto response = authenticationService.login(loginRequestDTO);

        // Assert
        assertNotNull(response);
        assertEquals("accessToken", response.access_token());
        assertEquals("refreshToken", response.refresh_token());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByEmail("joao@email.com");
    }

    @Test
    @DisplayName("Deve lançar exceção quando credenciais são inválidas")
    void deveLancarExcecaoQuandoCredenciaisSaoInvalidas() {
        // Arrange
        doThrow(new BadCredentialsException("Invalid credentials"))
                .when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> 
                authenticationService.login(loginRequestDTO));
        
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, never()).findByEmail(anyString());
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não é encontrado no login")
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> 
                authenticationService.login(loginRequestDTO));
        
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByEmail("joao@email.com");
    }
}