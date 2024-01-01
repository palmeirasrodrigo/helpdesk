package com.rodrigo.helpdesk.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum Perfil {
    ADMIN(0, "ROLE_ADMIN"), CLIENTE(1, "ROLE_CLIENTE"), TECNICO(2, "ROLE_TECNICO");

    private final Integer codigo;
    private final String descricao;

    public static Perfil toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }

        return Arrays.stream(Perfil.values())
                .filter(perfil -> cod.equals(perfil.getCodigo()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Perfil inválido"));
    }
}
