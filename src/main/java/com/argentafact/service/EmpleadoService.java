package com.argentafact.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

        // 1. Validaciones de negocio - Empleado
        if (empleadoRepository.existsByDni(empleado.getDni())) {
            throw new IllegalArgumentException("Ya existe un empleado con el DNI: " + empleado.getDni());
        }

        if (empleadoRepository.existsByEmail(empleado.getEmail())) {
            throw new IllegalArgumentException("Ya existe un empleado con el email: " + empleado.getEmail());
        }

        // 2. Validaciones de negocio - Usuario
        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new IllegalArgumentException("El username '" + usuario.getUsername() + "' ya está en uso");
        }

        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("El email de usuario ya está registrado");
        }

        // 3. Validar confirmación de contraseña
        if (!usuario.getPassword().equals(usuario.getConfirmarPassword())) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }

        // 4. Encriptar contraseña
        String passwordEncriptada = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(passwordEncriptada);

        // 5. Establecer relación bidireccional
        empleado.setUsuario(usuario);
        // El método setUsuario de Empleado ya establece la relación inversa

        // 6. Guardar (CASCADE persiste también Usuario)
        empleadoRepository.save(empleado);
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

    public  Empleado buscarPorId(Long id) {
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

}
