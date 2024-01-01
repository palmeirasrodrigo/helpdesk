package com.rodrigo.helpdesk.service;

import com.rodrigo.helpdesk.domain.Chamado;
import com.rodrigo.helpdesk.domain.Cliente;
import com.rodrigo.helpdesk.domain.Tecnico;
import com.rodrigo.helpdesk.domain.enums.Perfil;
import com.rodrigo.helpdesk.domain.enums.Prioridade;
import com.rodrigo.helpdesk.domain.enums.Status;
import com.rodrigo.helpdesk.repositories.ChamadoRepository;
import com.rodrigo.helpdesk.repositories.ClienteRepository;
import com.rodrigo.helpdesk.repositories.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DBService {

    @Autowired
    private TecnicoRepository tecnicoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ChamadoRepository chamadoRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public void instanciaDB() {
        Tecnico tecnico1 = new Tecnico(null, "Valdir Cezar", "24077239028", "valdir@email.com", encoder.encode("123"));
        tecnico1.addPerfil(Perfil.ADMIN);

        Cliente cliente1 = new Cliente(null, "Linus Torvalds", "51254867015", "torvalds@email.com",  encoder.encode("1234"));

        Chamado chamado1 = new Chamado(null, Prioridade.BAIXA, Status.ANDAMENTO, "chamdo 01", "primeiro chamado", tecnico1, cliente1);

        tecnicoRepository.saveAll(List.of(tecnico1));
        clienteRepository.saveAll(List.of(cliente1));
        chamadoRepository.saveAll(List.of(chamado1));
    }
}
