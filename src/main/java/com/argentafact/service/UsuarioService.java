package com.argentafact.service;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.argentafact.model.Usuario;
import com.argentafact.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UsuarioService(UsuarioRepository usuarioRepository, 
                         PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    /**
     * Registra un nuevo usuario en el sistema
     * 
     * @param usuario Usuario a registrar (con contraseña sin encriptar)
     * @return Usuario registrado
     */
    @Transactional
    public Usuario registrarUsuario(Usuario usuario) {
        // Encriptar la contraseña antes de guardar
        String passwordEncriptada = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(passwordEncriptada);
        
        // Establecer estado activo
        usuario.setActivo(true);
        
        // Guardar en base de datos
        return usuarioRepository.save(usuario);
    }
    
    /**
     * Verifica si un username ya existe
     */
    public boolean existeUsername(String username) {
        return usuarioRepository.existsByUsername(username);
    }
    
    /**
     * Verifica si un email ya existe
     */
    public boolean existeEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }
    
    /**
     * Busca un usuario por username
     */
    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public Page<Usuario> buscarTodos(PageRequest of){
        return usuarioRepository.findAll(of);
    }

    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }
    
}
