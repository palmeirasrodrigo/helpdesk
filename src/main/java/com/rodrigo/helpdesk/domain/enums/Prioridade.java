package com.rodrigo.helpdesk.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum Prioridade {
    BAIXA(0, "BAIXA"), MEDIA(1, "MEDIA"), ALTA(2, "ALTA");

    private final Integer codigo;
    private final String descricao;

    public static Prioridade toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }

        return Arrays.stream(Prioridade.values())
                .filter(perfil -> cod.equals(perfil.getCodigo()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Prioridade inválida"));
    }
}
