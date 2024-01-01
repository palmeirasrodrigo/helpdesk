package com.rodrigo.helpdesk.service;

import com.rodrigo.helpdesk.domain.Pessoa;
import com.rodrigo.helpdesk.domain.Tecnico;
import com.rodrigo.helpdesk.domain.dtos.TecnicoDTO;
import com.rodrigo.helpdesk.mapper.TecnicoMapper;
import com.rodrigo.helpdesk.repositories.PessoaRepository;
import com.rodrigo.helpdesk.repositories.TecnicoRepository;
import com.rodrigo.helpdesk.service.exceptions.DataIntegrityViolationException;
import com.rodrigo.helpdesk.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TecnicoService {

    @Autowired
    private TecnicoRepository repository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public TecnicoDTO findById(Integer id) {
        Tecnico tecnico = findTecnico(id);
        return TecnicoMapper.INSTANCE.mapToTecnicoDTO(tecnico);
    }

    public List<TecnicoDTO> findAll() {
        List<Tecnico> tecnicos = repository.findAll();
        return TecnicoMapper.INSTANCE.mapToTecnicosDTO(tecnicos);
    }

    public TecnicoDTO create(TecnicoDTO dto) {
        dto.setSenha(encoder.encode(dto.getSenha()));
        validaPorCpfEEmail(dto);
        Tecnico tecnico = TecnicoMapper.INSTANCE.mapToTecnico(dto);
        Tecnico tecnicoSalvo = repository.save(tecnico);
        return TecnicoMapper.INSTANCE.mapToTecnicoDTO(tecnicoSalvo);
    }

    public TecnicoDTO update(Integer id, TecnicoDTO dto) {
        dto.setId(id);
        dto.setSenha(encoder.encode(dto.getSenha()));
        findTecnico(id);
        validaPorCpfEEmail(dto);
        Tecnico tecnico = TecnicoMapper.INSTANCE.mapToTecnico(dto);
        Tecnico tecnicoSalvo = repository.save(tecnico);
        return TecnicoMapper.INSTANCE.mapToTecnicoDTO(tecnicoSalvo);
    }

    public void delete(Integer id) {
        Tecnico tecnico = findTecnico(id);
        if (!tecnico.getChamados().isEmpty()) {
            throw new DataIntegrityViolationException("O técnico possui ordens de serviço e não pode ser deletado!");
        }
        repository.delete(tecnico);
    }

    private void validaPorCpfEEmail(TecnicoDTO dto) {
        Optional<Pessoa> pessoa = pessoaRepository.findByCpfOrEmail(dto.getCpf(), dto.getEmail());
        if (pessoa.isPresent() && !Objects.equals(pessoa.get().getId(), dto.getId())) {
            throw new DataIntegrityViolationException("CPF ou email já cadastrado no sistema");
        }
    }

    private Tecnico findTecnico(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
    }
}
