package com.argentafact.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.argentafact.model.Usuario;
import com.argentafact.repository.UsuarioRepository;

@Configuration
public class DataInitializer {

    /**
     * Crea usuarios de prueba al iniciar la aplicación
     */
    @Bean
    CommandLineRunner initDatabase(UsuarioRepository repository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            // Verificar si ya existen usuarios
            if (repository.count() == 0) {
                // Crear usuario admin
                // Usuario admin = new Usuario();
                // admin.setUsername("admin");
                // admin.setPassword(passwordEncoder.encode("admin123"));
                // admin.setEmail("admin@sistema.com");
                // admin.setActivo(true);
                // repository.save(admin);

                // // Crear usuario de prueba
                // Usuario user = new Usuario();
                // user.setUsername("usuario");
                // user.setPassword(passwordEncoder.encode("usuario123"));
                // user.setEmail("usuario@sistema.com");
                // user.setActivo(true);
                // repository.save(user);

                System.out.println("Usuarios de prueba creados:");
                System.out.println("- admin / admin123");
                System.out.println("- usuario / usuario123");
            }
        };
    }
}
