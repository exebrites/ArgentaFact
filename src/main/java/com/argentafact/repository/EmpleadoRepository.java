package com.argentafact.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.argentafact.model.Empleado;
@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    Empleado findByIdEmpleado(Long id);
	
     boolean existsByDni(String dni);
    
    boolean existsByEmail(String email);
    
    Optional<Empleado> findByDni(String dni);
    
    Optional<Empleado> findByEmail(String email);
}
