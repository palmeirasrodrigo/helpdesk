package com.rodrigo.helpdesk.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum Status {
    ABERTO(0, "ABERTO"), ANDAMENTO(1, "ANDAMENTO"), ENCERRADO(2, "ENCERRADO");

    private final Integer codigo;
    private final String descricao;

    public static Status toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }

        return Arrays.stream(Status.values())
                .filter(perfil -> cod.equals(perfil.getCodigo()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Status inválido"));
    }
}
