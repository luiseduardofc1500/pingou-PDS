package com.pds.pingou.produto.cachaca;

import com.pds.pingou.produto.Produto;
import com.pds.pingou.produto.cachaca.exception.CachacaNotFoundException;
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
class CachacaServiceTest {

    @Mock
    private CachacaRepository cachacaRepository;

    @InjectMocks
    private CachacaService cachacaService;

    private Cachaca cachaca;
    private CachacaRequestDTO cachacaRequestDTO;

    @BeforeEach
    void setUp() {
        cachaca = new Cachaca();
        cachaca.setId(1L);
        cachaca.setNome("Cachaça Artesanal");
        cachaca.setDescricao("Cachaça premium de Minas Gerais");
        cachaca.setPreco(new BigDecimal("45.90"));
        cachaca.setUrlImagem("http://example.com/cachaca.jpg");
        cachaca.setAtivo(true);
        cachaca.setRegiao("Minas Gerais");
        cachaca.setTeorAlcoolico(new BigDecimal("38.5"));
        cachaca.setVolume(700);
        cachaca.setTipoCachaca(TipoCachaca.ENVELHECIDA);
        cachaca.setTipoEnvelhecimento(TipoEnvelhecimento.ENVELHECIDA);
        cachaca.setTempoEnvelhecimentoMeses(12);
        cachaca.setAnoProducao(2022);

        cachacaRequestDTO = new CachacaRequestDTO();
        cachacaRequestDTO.setNome("Cachaça Artesanal");
        cachacaRequestDTO.setDescricao("Cachaça premium de Minas Gerais");
        cachacaRequestDTO.setPreco(new BigDecimal("45.90"));
        cachacaRequestDTO.setUrlImagem("http://example.com/cachaca.jpg");
        cachacaRequestDTO.setRegiao("Minas Gerais");
        cachacaRequestDTO.setTeorAlcoolico(new BigDecimal("38.5"));
        cachacaRequestDTO.setVolume(700);
        cachacaRequestDTO.setTipoCachaca(TipoCachaca.ENVELHECIDA);
        cachacaRequestDTO.setTipoEnvelhecimento(TipoEnvelhecimento.ENVELHECIDA);
        cachacaRequestDTO.setTempoEnvelhecimentoMeses(12);
        cachacaRequestDTO.setAnoProducao(2022);
    }

    @Test
    @DisplayName("Deve listar todas as cachaças com sucesso")
    void deveListarTodasCachacasComSucesso() {
        // Arrange
        List<Produto> cachacas = Arrays.asList(cachaca);
        when(cachacaRepository.findAll()).thenReturn(cachacas);

        // Act
        List<CachacaResponseDTO> result = cachacaService.listarTodas();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Cachaça Artesanal", result.get(0).getNome());
        verify(cachacaRepository).findAll();
    }

    @Test
    @DisplayName("Deve listar cachaças ativas com sucesso")
    void deveListarCachacasAtivasComSucesso() {
        // Arrange
        List<Produto> cachacas = Arrays.asList(cachaca);
        when(cachacaRepository.findByAtivoTrue()).thenReturn(cachacas);

        // Act
        List<CachacaResponseDTO> result = cachacaService.listarAtivas();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Cachaça Artesanal", result.get(0).getNome());
        assertTrue(result.get(0).getAtivo());
        verify(cachacaRepository).findByAtivoTrue();
    }

    @Test
    @DisplayName("Deve buscar cachaça por ID com sucesso")
    void deveBuscarCachacaPorIdComSucesso() {
        // Arrange
        when(cachacaRepository.findById(anyLong())).thenReturn(Optional.of(cachaca));

        // Act
        CachacaResponseDTO result = cachacaService.buscarPorId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Cachaça Artesanal", result.getNome());
        assertEquals("Minas Gerais", result.getRegiao());
        verify(cachacaRepository).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção quando cachaça não é encontrada por ID")
    void deveLancarExcecaoQuandoCachacaNaoEncontrada() {
        // Arrange
        when(cachacaRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CachacaNotFoundException.class, () -> 
                cachacaService.buscarPorId(999L));
        
        verify(cachacaRepository).findById(999L);
    }

    @Test
    @DisplayName("Deve criar cachaça com sucesso")
    void deveCriarCachacaComSucesso() {
        // Arrange
        when(cachacaRepository.save(any(Cachaca.class))).thenReturn(cachaca);

        // Act
        CachacaResponseDTO result = cachacaService.criar(cachacaRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Cachaça Artesanal", result.getNome());
        assertEquals("Minas Gerais", result.getRegiao());
        assertEquals(new BigDecimal("45.90"), result.getPreco());
        verify(cachacaRepository).save(any(Cachaca.class));
    }

    @Test
    @DisplayName("Deve atualizar cachaça com sucesso")
    void deveAtualizarCachacaComSucesso() {
        // Arrange
        when(cachacaRepository.findById(anyLong())).thenReturn(Optional.of(cachaca));
        when(cachacaRepository.save(any(Cachaca.class))).thenReturn(cachaca);

        cachacaRequestDTO.setNome("Cachaça Atualizada");
        cachacaRequestDTO.setPreco(new BigDecimal("55.90"));

        // Act
        CachacaResponseDTO result = cachacaService.atualizar(1L, cachacaRequestDTO);

        // Assert
        assertNotNull(result);
        verify(cachacaRepository).findById(1L);
        verify(cachacaRepository).save(cachaca);
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar cachaça inexistente")
    void deveLancarExcecaoAoAtualizarCachacaInexistente() {
        // Arrange
        when(cachacaRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CachacaNotFoundException.class, () -> 
                cachacaService.atualizar(999L, cachacaRequestDTO));
        
        verify(cachacaRepository).findById(999L);
        verify(cachacaRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve deletar cachaça com sucesso (soft delete)")
    void deveDeletarCachacaComSucesso() {
        // Arrange
        when(cachacaRepository.findById(anyLong())).thenReturn(Optional.of(cachaca));
        when(cachacaRepository.save(any(Cachaca.class))).thenReturn(cachaca);

        // Act
        assertDoesNotThrow(() -> cachacaService.deletar(1L));

        // Assert
        verify(cachacaRepository).findById(1L);
        verify(cachacaRepository).save(cachaca);
        assertFalse(cachaca.getAtivo()); // Verifica se foi marcada como inativa
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar cachaça inexistente")
    void deveLancarExcecaoAoDeletarCachacaInexistente() {
        // Arrange
        when(cachacaRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CachacaNotFoundException.class, () -> 
                cachacaService.deletar(999L));
        
        verify(cachacaRepository).findById(999L);
        verify(cachacaRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve buscar cachaças por região com sucesso")
    void deveBuscarCachacasPorRegiaoComSucesso() {
        // Arrange
        List<Cachaca> cachacas = Arrays.asList(cachaca);
        when(cachacaRepository.findByRegiaoAndAtivoTrue(anyString())).thenReturn(cachacas);

        // Act
        List<CachacaResponseDTO> result = cachacaService.buscarPorRegiao("Minas Gerais");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Minas Gerais", result.get(0).getRegiao());
        verify(cachacaRepository).findByRegiaoAndAtivoTrue("Minas Gerais");
    }

    @Test
    @DisplayName("Deve buscar cachaças por tipo com sucesso")
    void deveBuscarCachacasPorTipoComSucesso() {
        // Arrange
        List<Cachaca> cachacas = Arrays.asList(cachaca);
        when(cachacaRepository.findByTipoCachacaAndAtivoTrue(any(TipoCachaca.class))).thenReturn(cachacas);

        // Act
        List<CachacaResponseDTO> result = cachacaService.buscarPorTipo(TipoCachaca.ENVELHECIDA);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(TipoCachaca.ENVELHECIDA, result.get(0).getTipoCachaca());
        verify(cachacaRepository).findByTipoCachacaAndAtivoTrue(TipoCachaca.ENVELHECIDA);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não encontrar cachaças por região")
    void deveRetornarListaVaziaQuandoNaoEncontrarCachacasPorRegiao() {
        // Arrange
        when(cachacaRepository.findByRegiaoAndAtivoTrue(anyString())).thenReturn(Arrays.asList());

        // Act
        List<CachacaResponseDTO> result = cachacaService.buscarPorRegiao("São Paulo");

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(cachacaRepository).findByRegiaoAndAtivoTrue("São Paulo");
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não encontrar cachaças por tipo")
    void deveRetornarListaVaziaQuandoNaoEncontrarCachacasPorTipo() {
        // Arrange
        when(cachacaRepository.findByTipoCachacaAndAtivoTrue(any(TipoCachaca.class))).thenReturn(Arrays.asList());

        // Act
        List<CachacaResponseDTO> result = cachacaService.buscarPorTipo(TipoCachaca.PREMIUM);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(cachacaRepository).findByTipoCachacaAndAtivoTrue(TipoCachaca.PREMIUM);
    }
}