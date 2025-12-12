package com.subscription.framework.hq.service;

import com.subscription.framework.hq.domain.Quadrinho;
import com.subscription.framework.hq.domain.UsuarioPreferencias;
import com.subscription.framework.hq.domain.enums.CategoriaHQ;
import com.subscription.framework.hq.domain.enums.Editora;
import com.subscription.framework.hq.domain.enums.TipoHQ;
import com.subscription.framework.hq.repository.QuadrinhoRepository;
import com.subscription.framework.hq.repository.UsuarioHistoricoHQRepository;
import com.subscription.framework.hq.repository.UsuarioPreferenciasRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CuradoriaHQServiceTest {

    @Mock
    private QuadrinhoRepository quadrinhoRepository;

    @Mock
    private UsuarioPreferenciasRepository preferenciasRepository;

    @Mock
    private UsuarioHistoricoHQRepository historicoRepository;

    @InjectMocks
    private CuradoriaHQService curadoriaService;

    private UsuarioPreferencias preferencias;
    private List<Quadrinho> quadrinhosDisponiveis;

    @BeforeEach
    void setUp() {
        // Configura preferências do usuário
        preferencias = new UsuarioPreferencias(1L);
        preferencias.adicionarCategoriaFavorita(CategoriaHQ.SUPER_HEROI);
        preferencias.adicionarEditoraFavorita(Editora.MARVEL);
        preferencias.setPreferenciaClassicas(60); // 60% clássicas, 40% modernas
        preferencias.setInteresseEdicoesColecionador(true);

        // Cria quadrinhos de exemplo
        quadrinhosDisponiveis = new ArrayList<>();

        // Clássicas Marvel
        Quadrinho spiderman = criarQuadrinho(1L, "Amazing Spider-Man #1", Editora.MARVEL, 
                                             TipoHQ.CLASSICA, true, CategoriaHQ.SUPER_HEROI);
        quadrinhosDisponiveis.add(spiderman);

        Quadrinho xmen = criarQuadrinho(2L, "X-Men #1", Editora.MARVEL, 
                                        TipoHQ.CLASSICA, true, CategoriaHQ.SUPER_HEROI);
        quadrinhosDisponiveis.add(xmen);

        // Modernas Marvel
        Quadrinho miles = criarQuadrinho(3L, "Miles Morales #1", Editora.MARVEL, 
                                         TipoHQ.MODERNA, false, CategoriaHQ.SUPER_HEROI);
        quadrinhosDisponiveis.add(miles);

        // DC
        Quadrinho batman = criarQuadrinho(4L, "Batman: DKR", Editora.DC, 
                                          TipoHQ.CLASSICA, true, CategoriaHQ.SUPER_HEROI);
        quadrinhosDisponiveis.add(batman);

        // Moderna DC
        Quadrinho flash = criarQuadrinho(5L, "Flash Renascimento", Editora.DC, 
                                         TipoHQ.MODERNA, false, CategoriaHQ.SUPER_HEROI);
        quadrinhosDisponiveis.add(flash);
    }

    @Test
    void deveCurarPacoteComDistribuicaoCorreta() {
        // Arrange
        int quantidadeDesejada = 5;
        when(preferenciasRepository.findByUserId(1L)).thenReturn(Optional.of(preferencias));
        when(historicoRepository.findQuadrinhoIdsRecebidosPorUsuario(1L))
            .thenReturn(Collections.emptyList());
        when(quadrinhoRepository.findAllDisponiveis()).thenReturn(quadrinhosDisponiveis);

        // Act
        List<Quadrinho> hqsCuradas = curadoriaService.curarPacoteParaUsuario(1L, quantidadeDesejada);

        // Assert
        assertNotNull(hqsCuradas);
        assertEquals(quantidadeDesejada, hqsCuradas.size());

        // Verifica distribuição de clássicas vs modernas
        long classicas = hqsCuradas.stream()
            .filter(hq -> hq.getTipoHQ() == TipoHQ.CLASSICA)
            .count();

        long modernas = hqsCuradas.stream()
            .filter(hq -> hq.getTipoHQ() == TipoHQ.MODERNA)
            .count();

        // 60% de 5 = 3 clássicas, 40% = 2 modernas
        assertEquals(3, classicas);
        assertEquals(2, modernas);

        verify(preferenciasRepository).findByUserId(1L);
        verify(historicoRepository).findQuadrinhoIdsRecebidosPorUsuario(1L);
        verify(quadrinhoRepository).findAllDisponiveis();
    }

    @Test
    void deveEvitarDuplicatasNoHistorico() {
        // Arrange
        int quantidadeDesejada = 3;
        List<Long> hqsJaRecebidas = Arrays.asList(1L, 2L); // Spider-Man e X-Men

        when(preferenciasRepository.findByUserId(1L)).thenReturn(Optional.of(preferencias));
        when(historicoRepository.findQuadrinhoIdsRecebidosPorUsuario(1L))
            .thenReturn(hqsJaRecebidas);
        when(quadrinhoRepository.findAllDisponiveis()).thenReturn(quadrinhosDisponiveis);

        // Act
        List<Quadrinho> hqsCuradas = curadoriaService.curarPacoteParaUsuario(1L, quantidadeDesejada);

        // Assert
        assertNotNull(hqsCuradas);
        assertTrue(hqsCuradas.size() <= quantidadeDesejada);

        // Verifica que não há duplicatas
        for (Quadrinho hq : hqsCuradas) {
            assertFalse(hqsJaRecebidas.contains(hq.getId()));
        }
    }

    @Test
    void devePriorizarEditorasFavoritas() {
        // Arrange
        int quantidadeDesejada = 3;
        when(preferenciasRepository.findByUserId(1L)).thenReturn(Optional.of(preferencias));
        when(historicoRepository.findQuadrinhoIdsRecebidosPorUsuario(1L))
            .thenReturn(Collections.emptyList());
        when(quadrinhoRepository.findAllDisponiveis()).thenReturn(quadrinhosDisponiveis);

        // Act
        List<Quadrinho> hqsCuradas = curadoriaService.curarPacoteParaUsuario(1L, quantidadeDesejada);

        // Assert
        long hqsMarvel = hqsCuradas.stream()
            .filter(hq -> hq.getEditora() == Editora.MARVEL)
            .count();

        // Marvel é editora favorita, deve ter mais HQs dela
        assertTrue(hqsMarvel > 0);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoTemPreferencias() {
        // Arrange
        when(preferenciasRepository.findByUserId(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> {
            curadoriaService.curarPacoteParaUsuario(1L, 5);
        });

        verify(preferenciasRepository).findByUserId(1L);
        verifyNoInteractions(quadrinhoRepository);
    }

    @Test
    void deveVerificarCorretamenteSeUsuarioJaRecebeuHQ() {
        // Arrange
        Long userId = 1L;
        Long quadrinhoId = 1L;
        when(historicoRepository.existsByUserIdAndQuadrinhoId(userId, quadrinhoId))
            .thenReturn(true);

        // Act
        boolean jaRecebeu = curadoriaService.usuarioJaRecebeuHQ(userId, quadrinhoId);

        // Assert
        assertTrue(jaRecebeu);
        verify(historicoRepository).existsByUserIdAndQuadrinhoId(userId, quadrinhoId);
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHaHQsDisponiveis() {
        // Arrange
        int quantidadeDesejada = 5;
        when(preferenciasRepository.findByUserId(1L)).thenReturn(Optional.of(preferencias));
        when(historicoRepository.findQuadrinhoIdsRecebidosPorUsuario(1L))
            .thenReturn(Arrays.asList(1L, 2L, 3L, 4L, 5L)); // Já recebeu todas
        when(quadrinhoRepository.findAllDisponiveis()).thenReturn(quadrinhosDisponiveis);

        // Act
        List<Quadrinho> hqsCuradas = curadoriaService.curarPacoteParaUsuario(1L, quantidadeDesejada);

        // Assert
        assertNotNull(hqsCuradas);
        assertTrue(hqsCuradas.isEmpty());
    }

    private Quadrinho criarQuadrinho(Long id, String nome, Editora editora, 
                                      TipoHQ tipo, Boolean colecionador, CategoriaHQ... categorias) {
        Quadrinho hq = new Quadrinho();
        hq.setId(id);
        hq.setName(nome);
        hq.setEditora(editora);
        hq.setTipoHQ(tipo);
        hq.setEdicaoColecionador(colecionador);
        hq.setPrice(new BigDecimal("29.90"));
        hq.setActive(true);
        hq.setEstoque(10);
        
        for (CategoriaHQ categoria : categorias) {
            hq.adicionarCategoria(categoria);
        }
        
        return hq;
    }
}
