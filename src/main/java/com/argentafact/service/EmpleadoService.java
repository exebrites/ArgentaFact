package com.argentafact.service;

 
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
    public Object buscarTodos() {
        return empleadoRepository.findAll();
    }
}
