package com.argentafact.repository;

import com.argentafact.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

 
@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {

    Servicio findByIdServicio(Long id);
}
