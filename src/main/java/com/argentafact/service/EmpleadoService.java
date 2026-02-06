package com.argentafact.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.argentafact.model.Empleado;
import com.argentafact.model.Usuario;
import com.argentafact.repository.EmpleadoRepository;
import com.argentafact.repository.UsuarioRepository;

@Service
public class EmpleadoService {
    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public void guardar(Empleado empleado) {
        empleadoRepository.save(empleado);
    }

    public List<Empleado> buscarTodos() {
        return empleadoRepository.findAll();
    }

    /**
     * Registra un empleado junto con su usuario en una transacción única
     * 
     * @param empleado Entidad Empleado con datos validados
     * @param usuario  Entidad Usuario con datos validados
     * @throws IllegalArgumentException si hay errores de validación de negocio
     */

    public void registrarEmpleadoConUsuario(Empleado empleado, Usuario usuario) {

    }

    /**
     * Busca todos los empleados
     */

    public List<Empleado> listarTodos() {
        return empleadoRepository.findAll();
    }

    /**
     * Busca un empleado por ID
     */

    public Empleado buscarPorId(Long id) {
        return empleadoRepository.findByIdEmpleado(id);
    }

    /**
     * Busca un empleado por DNI
     */

    public Optional<Empleado> buscarPorDni(String dni) {
        return empleadoRepository.findByDni(dni);
    }

    /**
     * Actualiza datos de un empleado (sin modificar usuario)
     */

    public void actualizarEmpleado(Empleado empleado) {
        if (!empleadoRepository.existsById(empleado.getIdEmpleado())) {
            throw new IllegalArgumentException("Empleado no encontrado");
        }
        empleadoRepository.save(empleado);
    }

    /**
     * Desactiva un empleado (soft delete via Usuario)
     */

    public void desactivarEmpleado(Long idEmpleado) {
        Empleado empleado = empleadoRepository.findById(idEmpleado)
                .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado"));

        if (empleado.getUsuario() != null) {
            empleado.getUsuario().setActivo(false);
            empleadoRepository.save(empleado);
        }
    }

    public Page<Empleado> buscarTodos(PageRequest of) {
        return empleadoRepository.findAll(of);
    }

}
