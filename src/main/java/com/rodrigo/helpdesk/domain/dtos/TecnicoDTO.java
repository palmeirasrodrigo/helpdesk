package com.rodrigo.helpdesk.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rodrigo.helpdesk.domain.enums.Perfil;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TecnicoDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -5623454207705233567L;

    private Integer id;

    @NotBlank(message = "O campo nome é requerido")
    private String nome;

    @NotBlank(message = "O campo cpf é requerido")
    private String cpf;

    @NotBlank(message = "O campo email é requerido")
    private String email;

    @NotBlank(message = "O campo senha é requerido")
    private String senha;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataCriacao = LocalDate.now();

    @Builder.Default
    private Set<Perfil> perfis = new HashSet<>();
}
