package com.argentafact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.argentafact.model.Servicio;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {

    Servicio findByIdServicio(Long id);
}
