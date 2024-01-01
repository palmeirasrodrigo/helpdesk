package com.rodrigo.helpdesk.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChamadoDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 3658865396012044538L;

    private Integer id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Builder.Default
    private LocalDate dataAbertura = LocalDate.now();

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataFechamento;

    @NotNull(message = "O campo prioridade é requerido")
    private Integer prioridade;

    @NotNull(message = "O campo status é requerido")
    private Integer status;

    @NotBlank(message = "O campo titulo é requerido")
    private String titulo;

    @NotBlank(message = "O campo observações é requerido")
    private String observacoes;

    @NotNull(message = "O campo técnico é requerido")
    private Integer tecnico;

    @NotNull(message = "O campo cliente é requerido")
    private Integer cliente;

    private String nomeTecnico;
    private String nomeCliente;
}
