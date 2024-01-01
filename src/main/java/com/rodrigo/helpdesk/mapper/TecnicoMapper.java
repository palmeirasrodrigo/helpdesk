package com.rodrigo.helpdesk.mapper;

import com.rodrigo.helpdesk.domain.Tecnico;
import com.rodrigo.helpdesk.domain.dtos.TecnicoDTO;
import com.rodrigo.helpdesk.domain.enums.Perfil;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface TecnicoMapper {

    TecnicoMapper INSTANCE = Mappers.getMapper(TecnicoMapper.class);

    List<TecnicoDTO> mapToTecnicosDTO(List<Tecnico> tecnico);

    @Mapping(target = "perfis", source = "perfis", qualifiedByName = "mapToPerfis")
    TecnicoDTO mapToTecnicoDTO(Tecnico tecnico);

    @Mapping(target = "perfis", source = "perfis", qualifiedByName = "mapToCodigoPerfis")
    Tecnico mapToTecnico(TecnicoDTO tecnico);

    @Named("mapToPerfis")
    default Set<Perfil> mapToPerfis(Set<Integer> perfis) {
        return perfis.stream().map(Perfil::toEnum).collect(Collectors.toSet());
    }

    @Named("mapToCodigoPerfis")
    default Set<Integer> mapToCodigoPerfis(Set<Perfil> perfis) {
        return perfis.stream().map(Perfil::getCodigo).collect(Collectors.toSet());
    }

    @AfterMapping
    default void addDefaultPerfil(@MappingTarget Tecnico tecnico) {
        tecnico.addPerfil(Perfil.CLIENTE);
    }
}
