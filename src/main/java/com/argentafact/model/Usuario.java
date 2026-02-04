package com.argentafact.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "usuarios")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El username es obligatorio")
    @Size(min = 4, max = 20, message = "El username debe tener entre 4 y 20 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "Username solo puede contener letras, números, puntos, guiones y guiones bajos")
    @Column(nullable = false, unique = true, length = 20)
    private String username;
    
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Column(nullable = false)
    private String password;
    
    // Campo transient solo para validación en formulario
    @Transient
    private String confirmarPassword;
    
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Email inválido")
    @Column(nullable = false, unique = true, length = 100)
    private String email;
    
    @Column(nullable = false)
    private Boolean activo = true;
    
    // Relación bidireccional 1:1 con Empleado
    // El lado "owner" de la relación (quien tiene la FK)
    @OneToOne
    @JoinColumn(name = "empleado_id", nullable = false, unique = true)
    private Empleado empleado;
    
    // Constructores
    public Usuario() {
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmarPassword() {
        return confirmarPassword;
    }

    public void setConfirmarPassword(String confirmarPassword) {
        this.confirmarPassword = confirmarPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean isActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
    
    // toString
    @Override
    public String toString() {
        return "Usuario [id=" + id + ", username=" + username + ", email=" + email + ", activo=" + activo + "]";
    }

}