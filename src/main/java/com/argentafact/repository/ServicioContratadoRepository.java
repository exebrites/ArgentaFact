package com.argentafact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.argentafact.model.ServicioContratado;

@Repository
public interface ServicioContratadoRepository extends JpaRepository<ServicioContratado, Long> {
    
    ServicioContratado findByIdServicioContratado(Long servicioContratado_id);
}
