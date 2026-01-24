package com.argentafact.service;

 import java.util.List;

import org.springframework.stereotype.Service;
import com.argentafact.model.Empleado;
import com.argentafact.repository.EmpleadoRepository;

@Service
public class EmpleadoService {
   
    private final EmpleadoRepository empleadoRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }
    public void guardar(Empleado empleado) {
        empleadoRepository.save(empleado);
    }
    public List<Empleado> buscarTodos() {
        return empleadoRepository.findAll();
    }
    
    public Empleado buscarPorId(Long id) {
        return empleadoRepository.findByIdEmpleado(id); 
    }

}
