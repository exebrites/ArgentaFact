package com.argentafact.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.argentafact.model.Empleado;
import com.argentafact.model.Usuario;
import com.argentafact.repository.EmpleadoRepository;
import com.argentafact.repository.UsuarioRepository;

import java.time.LocalDate;

@Configuration
public class DataInitializer {
    
    /**
     * Crea empleados con usuarios de prueba al iniciar la aplicación
     * Respeta la relación obligatoria 1:1 entre Empleado y Usuario
     */
    @Bean
    CommandLineRunner initDatabase(
            EmpleadoRepository empleadoRepository,
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {
        
        return args -> {
            // Verificar si ya existen empleados
            if (empleadoRepository.count() == 0) {
                
                System.out.println("========================================");
                System.out.println("Creando empleados de prueba...");
                System.out.println("========================================");
                
                // ============================================
                // EMPLEADO 1: ADMINISTRADOR
                // ============================================
                Empleado empleadoAdmin = new Empleado();
                empleadoAdmin.setNombre("Carlos");
                empleadoAdmin.setApellido("Rodríguez");
                empleadoAdmin.setDni("12345678");
                empleadoAdmin.setEmail("carlos.rodriguez@argentafact.com");
                empleadoAdmin.setTelefono("1123456789");
                empleadoAdmin.setDepartamento("Administración");
                empleadoAdmin.setCargo("Administrador de Sistemas");
                empleadoAdmin.setFechaIngreso(LocalDate.of(2020, 1, 15));
                
                Usuario usuarioAdmin = new Usuario();
                usuarioAdmin.setUsername("admin");
                usuarioAdmin.setPassword(passwordEncoder.encode("admin123"));
                usuarioAdmin.setEmail("admin@argentafact.com");
                usuarioAdmin.setActivo(true);
                
                // Establecer relación bidireccional
                empleadoAdmin.setUsuario(usuarioAdmin);
                
                // Guardar (CASCADE persiste el Usuario automáticamente)
                empleadoRepository.save(empleadoAdmin);
                
              
                
                // ============================================
                // RESUMEN
                // ============================================
                System.out.println("\n✅ Empleados de prueba creados exitosamente:");
                System.out.println("----------------------------------------");
                System.out.println("1. Admin:      admin / admin123");
                System.out.println("   Empleado:   Carlos Rodríguez (DNI: 12345678)");
                System.out.println("   Cargo:      Administrador de Sistemas");
            
                
            } else {
                System.out.println("⚠️  Base de datos ya contiene empleados. No se crean datos de prueba.");
            }
        };
    }
}