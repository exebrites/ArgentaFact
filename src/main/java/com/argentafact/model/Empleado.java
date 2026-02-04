package com.argentafact.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "empleados")
public class Empleado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmpleado;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    @Column(nullable = false, length = 50)
    private String nombre;
    
    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    @Column(nullable = false, length = 50)
    private String apellido;
    
    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(regexp = "^[0-9]{7,8}$", message = "DNI inválido (7-8 dígitos)")
    @Column(nullable = false, unique = true, length = 8)
    private String dni;
    
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Email inválido")
    @Column(nullable = false, unique = true, length = 100)
    private String email;
    
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Teléfono inválido")
    @Column(length = 15)
    private String telefono;
    
    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;
    
    @Column(length = 100)
    private String departamento;
    
    @Column(length = 100)
    private String cargo;
    
    // Relación bidireccional 1:1 con Usuario
    // CASCADE: al guardar/eliminar Empleado, afecta Usuario
    // ORPHAN REMOVAL: si se desvincula el Usuario, se elimina
    @OneToOne(mappedBy = "empleado", cascade = CascadeType.ALL, orphanRemoval = true)
    private Usuario usuario;
    
    // Constructores
    public Empleado() {
        this.fechaIngreso = LocalDate.now();
    }
    
    // Método de conveniencia para vincular bidireccionalmente
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        if (usuario != null) {
            usuario.setEmpleado(this);
        }
    }
    
    // Getters y Setters
    public Long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }
    
    @Override
    public String toString() {
        return "Empleado [idEmpleado=" + idEmpleado + ", nombre=" + nombre + ", apellido=" + apellido + ", dni=" + dni
                + ", email=" + email + ", telefono=" + telefono + ", fechaIngreso=" + fechaIngreso + ", departamento="
                + departamento + ", cargo=" + cargo + "]";
    }
}