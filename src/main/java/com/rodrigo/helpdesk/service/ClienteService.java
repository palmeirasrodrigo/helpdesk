package com.rodrigo.helpdesk.service;

import com.rodrigo.helpdesk.domain.Cliente;
import com.rodrigo.helpdesk.domain.Pessoa;
import com.rodrigo.helpdesk.domain.dtos.ClienteDTO;
import com.rodrigo.helpdesk.mapper.ClienteMapper;
import com.rodrigo.helpdesk.repositories.ClienteRepository;
import com.rodrigo.helpdesk.repositories.PessoaRepository;
import com.rodrigo.helpdesk.service.exceptions.DataIntegrityViolationException;
import com.rodrigo.helpdesk.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public ClienteDTO findById(Integer id) {
        Cliente cliente = findCliente(id);
        return ClienteMapper.INSTANCE.mapToClienteDTO(cliente);
    }

    public List<ClienteDTO> findAll() {
        List<Cliente> clientes = repository.findAll();
        return ClienteMapper.INSTANCE.mapToClientesDTO(clientes);
    }

    public ClienteDTO create(ClienteDTO dto) {
        dto.setSenha(encoder.encode(dto.getSenha()));
        validaPorCpfEEmail(dto);
        Cliente cliente = ClienteMapper.INSTANCE.mapToCliente(dto);
        Cliente clienteSalvo = repository.save(cliente);
        return ClienteMapper.INSTANCE.mapToClienteDTO(clienteSalvo);
    }

    public ClienteDTO update(Integer id, ClienteDTO dto) {
        dto.setId(id);
        dto.setSenha(encoder.encode(dto.getSenha()));
        findCliente(id);
        validaPorCpfEEmail(dto);
        Cliente cliente = ClienteMapper.INSTANCE.mapToCliente(dto);
        Cliente clienteSalvo = repository.save(cliente);
        return ClienteMapper.INSTANCE.mapToClienteDTO(clienteSalvo);
    }

    public void delete(Integer id) {
        Cliente cliente = findCliente(id);
        if (!cliente.getChamados().isEmpty()) {
            throw new DataIntegrityViolationException("O cliente possui ordens de serviço e não pode ser deletado!");
        }
        repository.delete(cliente);
    }

    private void validaPorCpfEEmail(ClienteDTO dto) {
        Optional<Pessoa> pessoa = pessoaRepository.findByCpfOrEmail(dto.getCpf(), dto.getEmail());
        if (pessoa.isPresent() && !Objects.equals(pessoa.get().getId(), dto.getId())) {
            throw new DataIntegrityViolationException("CPF ou email já cadastrado no sistema");
        }
    }

    private Cliente findCliente(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
    }
}
