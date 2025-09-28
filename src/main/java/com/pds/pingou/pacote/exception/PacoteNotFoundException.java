package com.pds.pingou.pacote.exception;

/**
 * Exceção lançada quando um pacote não é encontrado no sistema.
 * 
 * Esta exceção é utilizada nas operações de busca, atualização e
 * exclusão de pacotes quando o ID fornecido não corresponde a
 * nenhum pacote existente no banco de dados.
 * 
 * @author Pingou Team
 * @version 1.0
 * @since 1.0
 */
public class PacoteNotFoundException extends RuntimeException {
    
    /**
     * Construtor que cria a exceção com uma mensagem padrão.
     * 
     * @param id ID do pacote que não foi encontrado
     */
    public PacoteNotFoundException(Long id) {
        super("Pacote não encontrado com id: " + id);
    }
}