package com.pds.pingou.admin.service;

import com.pds.pingou.security.user.User;
import com.pds.pingou.security.user.UserRepository;
import com.pds.pingou.security.user.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AdminService adminService;

    private User userAdmin;
    private User userRegular;

    @BeforeEach
    void setUp() {
        userAdmin = new User();
        userAdmin.setId(1L);
        userAdmin.setEmail("admin@email.com");
        userAdmin.setNome("Admin");
        userAdmin.setSobrenome("Sistema");
        userAdmin.setRole(UserRole.ADMIN);

        userRegular = new User();
        userRegular.setId(2L);
        userRegular.setEmail("user@email.com");
        userRegular.setNome("Usuário");
        userRegular.setSobrenome("Regular");
        userRegular.setRole(UserRole.USER);
    }

    @Test
    @DisplayName("Deve encontrar usuário por email com sucesso")
    void deveEncontrarUsuarioPorEmailComSucesso() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(userAdmin));

        // Act
        Optional<User> result = adminService.findByEmail("admin@email.com");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("admin@email.com", result.get().getEmail());
        assertEquals(UserRole.ADMIN, result.get().getRole());
        verify(userRepository).findByEmail("admin@email.com");
    }

    @Test
    @DisplayName("Deve retornar empty quando usuário não é encontrado por email")
    void deveRetornarEmptyQuandoUsuarioNaoEncontrado() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // Act
        Optional<User> result = adminService.findByEmail("inexistente@email.com");

        // Assert
        assertTrue(result.isEmpty());
        verify(userRepository).findByEmail("inexistente@email.com");
    }

    @Test
    @DisplayName("Deve alterar role do usuário com sucesso")
    void deveAlterarRoleDoUsuarioComSucesso() {
        // Arrange
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userRegular));
        when(userRepository.save(any(User.class))).thenReturn(userRegular);

        // Act
        assertDoesNotThrow(() -> adminService.changeUserRole(2L, UserRole.ADMIN));

        // Assert
        assertEquals(UserRole.ADMIN, userRegular.getRole());
        verify(userRepository).findById(2L);
        verify(userRepository).save(userRegular);
    }

    @Test
    @DisplayName("Deve lançar exceção ao alterar role de usuário inexistente")
    void deveLancarExcecaoAoAlterarRoleUsuarioInexistente() {
        // Arrange
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> 
                adminService.changeUserRole(999L, UserRole.ADMIN));
        
        verify(userRepository).findById(999L);
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve alterar role de ADMIN para USER com sucesso")
    void deveAlterarRoleDeAdminParaUserComSucesso() {
        // Arrange
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userAdmin));
        when(userRepository.save(any(User.class))).thenReturn(userAdmin);

        // Act
        assertDoesNotThrow(() -> adminService.changeUserRole(1L, UserRole.USER));

        // Assert
        assertEquals(UserRole.USER, userAdmin.getRole());
        verify(userRepository).findById(1L);
        verify(userRepository).save(userAdmin);
    }

    @Test
    @DisplayName("Deve manter role quando já é a mesma")
    void deveManterRoleQuandoJaEAMesma() {
        // Arrange
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userAdmin));
        when(userRepository.save(any(User.class))).thenReturn(userAdmin);

        // Act
        assertDoesNotThrow(() -> adminService.changeUserRole(1L, UserRole.ADMIN));

        // Assert
        assertEquals(UserRole.ADMIN, userAdmin.getRole());
        verify(userRepository).findById(1L);
        verify(userRepository).save(userAdmin);
    }

    @Test
    @DisplayName("Deve listar todos os usuários com sucesso")
    void deveListarTodosUsuariosComSucesso() {
        // Arrange
        List<User> usuarios = Arrays.asList(userAdmin, userRegular);
        when(userRepository.findAll()).thenReturn(usuarios);

        // Act
        List<User> result = adminService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(userAdmin));
        assertTrue(result.contains(userRegular));
        verify(userRepository).findAll();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há usuários")
    void deveRetornarListaVaziaQuandoNaoHaUsuarios() {
        // Arrange
        when(userRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<User> result = adminService.findAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository).findAll();
    }

    @Test
    @DisplayName("Deve distinguir diferentes tipos de usuários na listagem")
    void deveDistinguirDiferentesTiposUsuariosNaListagem() {
        // Arrange
        User outroAdmin = new User();
        outroAdmin.setId(3L);
        outroAdmin.setEmail("admin2@email.com");
        outroAdmin.setRole(UserRole.ADMIN);

        List<User> usuarios = Arrays.asList(userAdmin, userRegular, outroAdmin);
        when(userRepository.findAll()).thenReturn(usuarios);

        // Act
        List<User> result = adminService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size());
        
        long adminCount = result.stream()
                .filter(user -> user.getRole() == UserRole.ADMIN)
                .count();
        long userCount = result.stream()
                .filter(user -> user.getRole() == UserRole.USER)
                .count();
        
        assertEquals(2, adminCount);
        assertEquals(1, userCount);
        verify(userRepository).findAll();
    }

    @Test
    @DisplayName("Deve verificar se serviço mantém integridade dos dados do usuário")
    void deveVerificarSeServicoMantemIntegridadeDados() {
        // Arrange
        String emailOriginal = userRegular.getEmail();
        String nomeOriginal = userRegular.getNome();
        UserRole roleOriginal = userRegular.getRole();
        
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userRegular));
        when(userRepository.save(any(User.class))).thenReturn(userRegular);

        // Act
        adminService.changeUserRole(2L, UserRole.ADMIN);

        // Assert
        assertEquals(emailOriginal, userRegular.getEmail()); // Email não deve mudar
        assertEquals(nomeOriginal, userRegular.getNome()); // Nome não deve mudar
        assertNotEquals(roleOriginal, userRegular.getRole()); // Role deve mudar
        assertEquals(UserRole.ADMIN, userRegular.getRole()); // Nova role deve ser ADMIN
        
        verify(userRepository).findById(2L);
        verify(userRepository).save(userRegular);
    }
}