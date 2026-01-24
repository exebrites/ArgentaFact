package com.argentafact.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.argentafact.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Cliente findByIdCliente(Long id);

    List<Cliente> findByIdClienteNotIn(List<Long> idsClientesConCuenta);

}
