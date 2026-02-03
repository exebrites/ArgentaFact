package com.argentafact.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.argentafact.model.Cuenta;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

    Optional<Cuenta> findByClienteIdCliente(Long idCliente);

    boolean existsByClienteIdCliente(Long idCliente);
}
