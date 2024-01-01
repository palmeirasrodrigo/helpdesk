package com.rodrigo.helpdesk.mapper;

import com.rodrigo.helpdesk.domain.Cliente;
import com.rodrigo.helpdesk.domain.dtos.ClienteDTO;
import com.rodrigo.helpdesk.domain.enums.Perfil;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    List<ClienteDTO> mapToClientesDTO(List<Cliente> cliente);

    @Mapping(target = "perfis", source = "perfis", qualifiedByName = "mapToPerfis")
    ClienteDTO mapToClienteDTO(Cliente cliente);

    @Mapping(target = "perfis", source = "perfis", qualifiedByName = "mapToCodigoPerfis")
    Cliente mapToCliente(ClienteDTO cliente);

    @Named("mapToPerfis")
    default Set<Perfil> mapToPerfis(Set<Integer> perfis) {
        return perfis.stream().map(Perfil::toEnum).collect(Collectors.toSet());
    }

    @Named("mapToCodigoPerfis")
    default Set<Integer> mapToCodigoPerfis(Set<Perfil> perfis) {
        return perfis.stream().map(Perfil::getCodigo).collect(Collectors.toSet());
    }

    @AfterMapping
    default void addDefaultPerfil(@MappingTarget Cliente cliente) {
        cliente.addPerfil(Perfil.CLIENTE);
    }
}
