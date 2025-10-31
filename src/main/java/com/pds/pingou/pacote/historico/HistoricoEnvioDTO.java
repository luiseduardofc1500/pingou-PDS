package com.pds.pingou.pacote.historico;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HistoricoEnvioDTO {
    private Long id;
    private Long userId;
    private String userEmail;
    private String userNome;
    private Long pacoteId;
    private Long planoId;
    private String planoNome;
    private Long produtoId;
    private String produtoNome;
    private Integer quantidade;
    private LocalDateTime dataEnvio;
}


