package com.rodrigo.helpdesk.mapper;

import com.rodrigo.helpdesk.domain.Chamado;
import com.rodrigo.helpdesk.domain.dtos.ChamadoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ChamadoMapper {

    ChamadoMapper INSTANCE = Mappers.getMapper(ChamadoMapper.class);

    List<ChamadoDTO> mapToChamadosDTO(List<Chamado> cliente);

    @Mapping(target = "nomeTecnico", source = "tecnico.nome")
    @Mapping(target = "nomeCliente", source = "cliente.nome")
    @Mapping(target = "tecnico", source = "tecnico.id")
    @Mapping(target = "cliente", source = "cliente.id")
    @Mapping(target = "status", expression = "java(chamado.getStatus().getCodigo())")
    @Mapping(target = "prioridade", expression = "java(chamado.getPrioridade().getCodigo())")
    ChamadoDTO mapToChamadoDTO(Chamado chamado);

    @Mapping(target = "tecnico.id", source = "tecnico")
    @Mapping(target = "cliente.id", source = "cliente")
    @Mapping(target = "status", expression = "java(com.rodrigo.helpdesk.domain.enums.Status.toEnum(chamado.getStatus()))")
    @Mapping(target = "prioridade", expression = "java(com.rodrigo.helpdesk.domain.enums.Prioridade.toEnum(chamado.getPrioridade()))")
    Chamado mapToChamado(ChamadoDTO chamado);
}
