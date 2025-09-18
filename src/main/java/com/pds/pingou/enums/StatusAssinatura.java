package com.pds.pingou.enums;

public enum StatusAssinatura {
    ATIVA("Ativa"),
    INATIVA("Inativa"),
    CANCELADA("Cancelada"),
    EXPIRADA("Expirada");

    private final String descricao;

    StatusAssinatura(String descricao) {
        this.descricao = descricao;
    }

    // TODO: excluir esses método se não for usado
    public boolean isAtiva() {
        return this == ATIVA;
    }

    public boolean podeRenovar() {
        return this == EXPIRADA || this == INATIVA;
    }

    public boolean podeUsar() {
        return this == ATIVA;
    }

}
