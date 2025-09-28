package com.pds.pingou.planos;

import com.pds.pingou.planos.exception.PlanoNomeDuplicadoException;
import com.pds.pingou.planos.exception.PlanoNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlanoServiceTest {

    @Mock
    private PlanoRepository planoRepository;

    @InjectMocks
    private PlanoService planoService;

    private Plano plano;
    private PlanoRequestDTO planoRequestDTO;
    private PlanoResponseDTO planoResponseDTO;

    @BeforeEach
    void setUp() {
        plano = new Plano();
        plano.setId(1L);
        plano.setNome("Plano Básico");
        plano.setDescricao("Plano para iniciantes");
        plano.setPreco(new BigDecimal("29.90"));
        plano.setMaxProdutosPorMes(2);
        plano.setFrequenciaEntrega("MENSAL");
        plano.setAtivo(true);

        planoRequestDTO = new PlanoRequestDTO();
        planoRequestDTO.setNome("Plano Básico");
        planoRequestDTO.setDescricao("Plano para iniciantes");
        planoRequestDTO.setPreco(new BigDecimal("29.90"));
        planoRequestDTO.setMaxProdutosPorMes(2);
        planoRequestDTO.setFrequenciaEntrega("MENSAL");

        planoResponseDTO = new PlanoResponseDTO();
        planoResponseDTO.setId(1L);
        planoResponseDTO.setNome("Plano Básico");
        planoResponseDTO.setDescricao("Plano para iniciantes");
        planoResponseDTO.setPreco(new BigDecimal("29.90"));
        planoResponseDTO.setMaxProdutosPorMes(2);
        planoResponseDTO.setFrequenciaEntrega("MENSAL");
        planoResponseDTO.setAtivo(true);
    }

    @Test
    @DisplayName("Deve listar todos os planos com sucesso")
    void deveListarTodosPlanosComSucesso() {
        // Arrange
        List<Plano> planos = Arrays.asList(plano);
        when(planoRepository.findAll()).thenReturn(planos);

        // Act
        List<PlanoResponseDTO> result = planoService.listarTodos();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Plano Básico", result.get(0).getNome());
        verify(planoRepository).findAll();
    }

    @Test
    @DisplayName("Deve buscar plano por ID com sucesso")
    void deveBuscarPlanoPorIdComSucesso() {
        // Arrange
        when(planoRepository.findById(anyLong())).thenReturn(Optional.of(plano));

        // Act
        PlanoResponseDTO result = planoService.buscarPorId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Plano Básico", result.getNome());
        verify(planoRepository).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção quando plano não é encontrado por ID")
    void deveLancarExcecaoQuandoPlanoNaoEncontrado() {
        // Arrange
        when(planoRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PlanoNotFoundException.class, () -> 
                planoService.buscarPorId(999L));
        
        verify(planoRepository).findById(999L);
    }

    @Test
    @DisplayName("Deve criar plano com sucesso")
    void deveCriarPlanoComSucesso() {
        // Arrange
        when(planoRepository.existsByNome(anyString())).thenReturn(false);
        when(planoRepository.save(any(Plano.class))).thenReturn(plano);

        // Act
        PlanoResponseDTO result = planoService.criar(planoRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Plano Básico", result.getNome());
        assertEquals(new BigDecimal("29.90"), result.getPreco());
        verify(planoRepository).existsByNome("Plano Básico");
        verify(planoRepository).save(any(Plano.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando nome do plano já existe")
    void deveLancarExcecaoQuandoNomeJaExiste() {
        // Arrange
        when(planoRepository.existsByNome(anyString())).thenReturn(true);

        // Act & Assert
        assertThrows(PlanoNomeDuplicadoException.class, () -> 
                planoService.criar(planoRequestDTO));
        
        verify(planoRepository).existsByNome("Plano Básico");
        verify(planoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve atualizar plano com sucesso")
    void deveAtualizarPlanoComSucesso() {
        // Arrange
        when(planoRepository.findById(anyLong())).thenReturn(Optional.of(plano));
        when(planoRepository.save(any(Plano.class))).thenReturn(plano);

        planoRequestDTO.setNome("Plano Atualizado");
        planoRequestDTO.setPreco(new BigDecimal("39.90"));

        // Act
        PlanoResponseDTO result = planoService.atualizar(1L, planoRequestDTO);

        // Assert
        assertNotNull(result);
        verify(planoRepository).findById(1L);
        verify(planoRepository).save(plano);
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar plano inexistente")
    void deveLancarExcecaoAoAtualizarPlanoInexistente() {
        // Arrange
        when(planoRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PlanoNotFoundException.class, () -> 
                planoService.atualizar(999L, planoRequestDTO));
        
        verify(planoRepository).findById(999L);
        verify(planoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve deletar plano com sucesso")
    void deveDeletarPlanoComSucesso() {
        // Arrange
        when(planoRepository.existsById(anyLong())).thenReturn(true);

        // Act
        assertDoesNotThrow(() -> planoService.deletar(1L));

        // Assert
        verify(planoRepository).existsById(1L);
        verify(planoRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar plano inexistente")
    void deveLancarExcecaoAoDeletarPlanoInexistente() {
        // Arrange
        when(planoRepository.existsById(anyLong())).thenReturn(false);

        // Act & Assert
        assertThrows(PlanoNotFoundException.class, () -> 
                planoService.deletar(999L));
        
        verify(planoRepository).existsById(999L);
        verify(planoRepository, never()).deleteById(anyLong());
    }
}