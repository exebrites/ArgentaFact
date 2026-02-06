package com.argentafact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.argentafact.model.NotaDebito;

@Repository
public interface NotaDebitoRepository extends JpaRepository<NotaDebito, Long> {

    NotaDebito findByIdNotaDebito(Long id);

}
