package com.pds.pingou.produto.cachaca.exception;

/**
 * Exceção lançada quando uma cachaça não é encontrada no sistema.
 * 
 * Esta exceção é utilizada nas operações de busca, atualização e
 * exclusão de cachaças quando o ID fornecido não corresponde a
 * nenhuma cachaça existente no banco de dados.
 * 
 * @author Pingou Team
 * @version 1.0
 * @since 1.0
 */
public class CachacaNotFoundException extends RuntimeException {
    
    /**
     * Construtor que cria a exceção com uma mensagem padrão.
     * 
     * @param id ID da cachaça que não foi encontrada
     */
    public CachacaNotFoundException(Long id) {
        super("Cachaça não encontrada com id: " + id);
    }
}