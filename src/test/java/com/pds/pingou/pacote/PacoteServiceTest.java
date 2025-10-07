package com.pds.pingou.pacote;

import com.pds.pingou.pacote.exception.PacoteNotFoundException;
import com.pds.pingou.planos.Plano;
import com.pds.pingou.planos.PlanoRepository;
import com.pds.pingou.planos.exception.PlanoNotFoundException;
import com.pds.pingou.produto.Produto;
import com.pds.pingou.produto.ProdutoRepository;
import com.pds.pingou.produto.cachaca.Cachaca;
import com.pds.pingou.produto.cachaca.TipoCachaca;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PacoteServiceTest {

    @Mock
    private PacoteRepository pacoteRepository;

    @Mock
    private PlanoRepository planoRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ItemPacoteRepository itemPacoteRepository;

    @InjectMocks
    private PacoteService pacoteService;

    private Plano plano;
    private Produto produto;
    private Pacote pacote;
    private ItemPacote itemPacote;
    private PacoteRequestDTO pacoteRequestDTO;
    private ItemPacoteRequestDTO itemPacoteRequestDTO;

    @BeforeEach
    void setUp() {
        plano = new Plano();
        plano.setId(1L);
        plano.setNome("Plano Básico");
        plano.setMaxProdutosPorMes(3);
        plano.setPreco(new BigDecimal("29.90"));

        produto = new Cachaca();
        produto.setId(1L);
        produto.setNome("Cachaça Artesanal");
        produto.setPreco(new BigDecimal("45.90"));
        produto.setAtivo(true);

        pacote = new Pacote();
        pacote.setId(1L);
        pacote.setNome("Pacote Janeiro");
        pacote.setDescricao("Pacote do mês de janeiro");
        pacote.setDataEntrega(LocalDate.of(2025, 1, 15));
        pacote.setMes(1);
        pacote.setAno(2025);
        pacote.setPlano(plano);
        pacote.setAtivo(true);

        itemPacote = new ItemPacote();
        itemPacote.setId(1L);
        itemPacote.setPacote(pacote);
        itemPacote.setProduto(produto);
        itemPacote.setQuantidade(2);
        itemPacote.setObservacoes("Produto premium");

        pacoteRequestDTO = new PacoteRequestDTO();
        pacoteRequestDTO.setNome("Pacote Janeiro");
        pacoteRequestDTO.setDescricao("Pacote do mês de janeiro");
        pacoteRequestDTO.setDataEntrega(LocalDate.of(2025, 1, 15));
        pacoteRequestDTO.setMes(1);
        pacoteRequestDTO.setAno(2025);
        pacoteRequestDTO.setPlanoId(1L);

        itemPacoteRequestDTO = new ItemPacoteRequestDTO();
        itemPacoteRequestDTO.setProdutoId(1L);
        itemPacoteRequestDTO.setQuantidade(2);
        itemPacoteRequestDTO.setObservacoes("Produto premium");
    }

    @Test
    @DisplayName("Deve listar todos os pacotes com sucesso")
    void deveListarTodosPacotesComSucesso() {
        // Arrange
        List<Pacote> pacotes = Arrays.asList(pacote);
        when(pacoteRepository.findAll()).thenReturn(pacotes);

        // Act
        List<PacoteResponseDTO> result = pacoteService.listarTodos();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Pacote Janeiro", result.get(0).getNome());
        verify(pacoteRepository).findAll();
    }

    @Test
    @DisplayName("Deve buscar pacote por ID com sucesso")
    void deveBuscarPacotePorIdComSucesso() {
        // Arrange
        when(pacoteRepository.findById(anyLong())).thenReturn(Optional.of(pacote));

        // Act
        PacoteResponseDTO result = pacoteService.buscarPorId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Pacote Janeiro", result.getNome());
        verify(pacoteRepository).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção quando pacote não é encontrado por ID")
    void deveLancarExcecaoQuandoPacoteNaoEncontrado() {
        // Arrange
        when(pacoteRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PacoteNotFoundException.class, () -> 
                pacoteService.buscarPorId(999L));
        
        verify(pacoteRepository).findById(999L);
    }

    @Test
    @DisplayName("Deve buscar pacotes por plano com sucesso")
    void deveBuscarPacotesPorPlanoComSucesso() {
        // Arrange
        List<Pacote> pacotes = Arrays.asList(pacote);
        when(planoRepository.findById(anyLong())).thenReturn(Optional.of(plano));
        when(pacoteRepository.findByPlanoAndAtivoTrue(any(Plano.class))).thenReturn(pacotes);

        // Act
        List<PacoteResponseDTO> result = pacoteService.buscarPorPlano(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(planoRepository).findById(1L);
        verify(pacoteRepository).findByPlanoAndAtivoTrue(plano);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar pacotes por plano inexistente")
    void deveLancarExcecaoAoBuscarPacotesPorPlanoInexistente() {
        // Arrange
        when(planoRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PlanoNotFoundException.class, () -> 
                pacoteService.buscarPorPlano(999L));
        
        verify(planoRepository).findById(999L);
    }

    @Test
    @DisplayName("Deve buscar pacotes por mês e ano com sucesso")
    void deveBuscarPacotesPorMesEAnoComSucesso() {
        // Arrange
        List<Pacote> pacotes = Arrays.asList(pacote);
        when(pacoteRepository.findByMesAndAnoAndAtivoTrue(anyInt(), anyInt())).thenReturn(pacotes);

        // Act
        List<PacoteResponseDTO> result = pacoteService.buscarPorMesEAno(1, 2025);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(pacoteRepository).findByMesAndAnoAndAtivoTrue(1, 2025);
    }

    @Test
    @DisplayName("Deve criar pacote sem itens com sucesso")
    void deveCriarPacoteSemItensComSucesso() {
        // Arrange
        when(planoRepository.findById(anyLong())).thenReturn(Optional.of(plano));
        when(pacoteRepository.save(any(Pacote.class))).thenReturn(pacote);

        // Act
        PacoteResponseDTO result = pacoteService.criar(pacoteRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Pacote Janeiro", result.getNome());
        verify(planoRepository).findById(1L);
        verify(pacoteRepository, times(1)).save(any(Pacote.class));
    }

    @Test
    @DisplayName("Deve criar pacote com itens com sucesso")
    void deveCriarPacoteComItensComSucesso() {
        // Arrange
        pacoteRequestDTO.setItens(Arrays.asList(itemPacoteRequestDTO));
        when(planoRepository.findById(anyLong())).thenReturn(Optional.of(plano));
        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(produto));
        when(pacoteRepository.save(any(Pacote.class))).thenReturn(pacote);

        // Act
        PacoteResponseDTO result = pacoteService.criar(pacoteRequestDTO);

        // Assert
        assertNotNull(result);
        verify(planoRepository).findById(1L);
        verify(produtoRepository).findById(1L);
        verify(pacoteRepository, times(2)).save(any(Pacote.class)); // Uma vez inicial, outra com itens
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar pacote com produto inativo")
    void deveLancarExcecaoAoCriarPacoteComProdutoInativo() {
        // Arrange
        produto.setAtivo(false);
        pacoteRequestDTO.setItens(Arrays.asList(itemPacoteRequestDTO));
        when(planoRepository.findById(anyLong())).thenReturn(Optional.of(plano));
        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(produto));
        when(pacoteRepository.save(any(Pacote.class))).thenReturn(pacote);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> 
                pacoteService.criar(pacoteRequestDTO));
        
        verify(produtoRepository).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar pacote excedendo limite do plano")
    void deveLancarExcecaoAoCriarPacoteExcedendoLimitePlano() {
        // Arrange
        itemPacoteRequestDTO.setQuantidade(5); // Excede o limite de 3 do plano
        pacoteRequestDTO.setItens(Arrays.asList(itemPacoteRequestDTO));
        when(planoRepository.findById(anyLong())).thenReturn(Optional.of(plano));
        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(produto));
        when(pacoteRepository.save(any(Pacote.class))).thenReturn(pacote);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> 
                pacoteService.criar(pacoteRequestDTO));
        
        verify(produtoRepository).findById(1L);
    }

    @Test
    @DisplayName("Deve atualizar pacote com sucesso")
    void deveAtualizarPacoteComSucesso() {
        // Arrange
        when(pacoteRepository.findById(anyLong())).thenReturn(Optional.of(pacote));
        when(pacoteRepository.save(any(Pacote.class))).thenReturn(pacote);

        // Act
        PacoteResponseDTO result = pacoteService.atualizar(1L, pacoteRequestDTO);

        // Assert
        assertNotNull(result);
        verify(pacoteRepository).findById(1L);
        verify(pacoteRepository).save(pacote);
    }

    @Test
    @DisplayName("Deve deletar pacote com sucesso (soft delete)")
    void deveDeletarPacoteComSucesso() {
        // Arrange
        when(pacoteRepository.findById(anyLong())).thenReturn(Optional.of(pacote));
        when(pacoteRepository.save(any(Pacote.class))).thenReturn(pacote);

        // Act
        assertDoesNotThrow(() -> pacoteService.deletar(1L));

        // Assert
        verify(pacoteRepository).findById(1L);
        verify(pacoteRepository).save(pacote);
        assertFalse(pacote.getAtivo());
    }

    @Test
    @DisplayName("Deve adicionar item ao pacote com sucesso")
    void deveAdicionarItemAoPacoteComSucesso() {
        // Arrange
        when(pacoteRepository.findById(anyLong())).thenReturn(Optional.of(pacote));
        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(produto));
        when(pacoteRepository.save(any(Pacote.class))).thenReturn(pacote);

        // Act
        PacoteResponseDTO result = pacoteService.adicionarItem(1L, itemPacoteRequestDTO);

        // Assert
        assertNotNull(result);
        verify(pacoteRepository).findById(1L);
        verify(produtoRepository).findById(1L);
        verify(pacoteRepository).save(pacote);
    }

    @Test
    @DisplayName("Deve remover item do pacote com sucesso")
    void deveRemoverItemDoPacoteComSucesso() {
        // Arrange
        when(pacoteRepository.findById(anyLong())).thenReturn(Optional.of(pacote));
        when(itemPacoteRepository.findById(anyLong())).thenReturn(Optional.of(itemPacote));
        when(pacoteRepository.save(any(Pacote.class))).thenReturn(pacote);

        // Act
        PacoteResponseDTO result = pacoteService.removerItem(1L, 1L);

        // Assert
        assertNotNull(result);
        verify(pacoteRepository).findById(1L);
        verify(itemPacoteRepository).findById(1L);
        verify(itemPacoteRepository).delete(itemPacote);
        verify(pacoteRepository).save(pacote);
    }

    @Test
    @DisplayName("Deve listar itens do pacote com sucesso")
    void deveListarItensDoPacoteComSucesso() {
        // Arrange
        pacote.setItens(Arrays.asList(itemPacote));
        when(pacoteRepository.findById(anyLong())).thenReturn(Optional.of(pacote));

        // Act
        List<ItemPacoteResponseDTO> result = pacoteService.listarItensDoPacote(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(pacoteRepository).findById(1L);
    }

    @Test
    @DisplayName("Deve atualizar quantidade do item com sucesso")
    void deveAtualizarQuantidadeDoItemComSucesso() {
        // Arrange
        pacote.setItens(Arrays.asList(itemPacote));
        when(pacoteRepository.findById(anyLong())).thenReturn(Optional.of(pacote));
        when(itemPacoteRepository.findById(anyLong())).thenReturn(Optional.of(itemPacote));
        when(itemPacoteRepository.save(any(ItemPacote.class))).thenReturn(itemPacote);

        // Act
        PacoteResponseDTO result = pacoteService.atualizarQuantidadeItem(1L, 1L, 1);

        // Assert
        assertNotNull(result);
        verify(pacoteRepository).findById(1L);
        verify(itemPacoteRepository).findById(1L);
        verify(itemPacoteRepository).save(itemPacote);
        assertEquals(1, itemPacote.getQuantidade());
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar quantidade inválida")
    void deveLancarExcecaoAoAtualizarQuantidadeInvalida() {
        // Arrange
        when(pacoteRepository.findById(anyLong())).thenReturn(Optional.of(pacote));
        when(itemPacoteRepository.findById(anyLong())).thenReturn(Optional.of(itemPacote));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> 
                pacoteService.atualizarQuantidadeItem(1L, 1L, 0));
        
        verify(pacoteRepository).findById(1L);
        verify(itemPacoteRepository).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção quando item não pertence ao pacote")
    void deveLancarExcecaoQuandoItemNaoPertenceAoPacote() {
        // Arrange
        ItemPacote itemPacoteOutroPacote = new ItemPacote();
        itemPacoteOutroPacote.setId(2L);
        Pacote outroPacote = new Pacote();
        outroPacote.setId(2L);
        itemPacoteOutroPacote.setPacote(outroPacote);

        when(pacoteRepository.findById(anyLong())).thenReturn(Optional.of(pacote));
        when(itemPacoteRepository.findById(anyLong())).thenReturn(Optional.of(itemPacoteOutroPacote));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> 
                pacoteService.atualizarQuantidadeItem(1L, 2L, 1));
        
        verify(pacoteRepository).findById(1L);
        verify(itemPacoteRepository).findById(2L);
    }
}