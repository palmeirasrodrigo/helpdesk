package com.rodrigo.helpdesk.service;

import com.rodrigo.helpdesk.domain.Chamado;
import com.rodrigo.helpdesk.domain.dtos.ChamadoDTO;
import com.rodrigo.helpdesk.mapper.ChamadoMapper;
import com.rodrigo.helpdesk.repositories.ChamadoRepository;
import com.rodrigo.helpdesk.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChamadoService {

    @Autowired
    private ChamadoRepository repository;

    @Autowired
    private TecnicoService tecnicoService;

    @Autowired
    private ClienteService clienteService;

    public ChamadoDTO findById(Integer id) {
        Chamado chamado = findChamado(id);
        return ChamadoMapper.INSTANCE.mapToChamadoDTO(chamado);
    }

    public List<ChamadoDTO> findAll() {
        List<Chamado> chamados = repository.findAll();
        return ChamadoMapper.INSTANCE.mapToChamadosDTO(chamados);
    }

    public ChamadoDTO create(ChamadoDTO dto) {
        return newChamado(dto);
    }

    public ChamadoDTO update(Integer id, ChamadoDTO dto) {
        dto.setId(id);
        findChamado(id);
        return newChamado(dto);
    }

    public void delete(Integer id) {
        Chamado chamado = findChamado(id);
        repository.delete(chamado);
    }

    private Chamado findChamado(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Chamado não encontrado! Id: " + id));
    }

    private ChamadoDTO newChamado(ChamadoDTO dto) {
        tecnicoService.findById(dto.getTecnico());
        clienteService.findById(dto.getCliente());

        Chamado chamado = ChamadoMapper.INSTANCE.mapToChamado(dto);
        Chamado chamadoSalvo = repository.save(chamado);
        return ChamadoMapper.INSTANCE.mapToChamadoDTO(chamadoSalvo);
    }
}
