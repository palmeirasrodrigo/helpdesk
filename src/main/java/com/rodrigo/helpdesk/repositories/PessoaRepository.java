package com.rodrigo.helpdesk.repositories;

import com.rodrigo.helpdesk.domain.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

    Optional<Pessoa> findByCpfOrEmail(String cpf, String email);
    Optional<Pessoa> findByEmail(String email);
}
