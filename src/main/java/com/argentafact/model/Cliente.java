package com.argentafact.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    @Column(nullable = false)
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    private String apellido;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "El CUIT/DNI es obligatorio")
    private String cuit;

    @Column(nullable = true)
    private String direccion;
    @Column(nullable = true)
    private String telefono;

    @Column(nullable = false)
    private CondicionFiscal condicionFiscal;

    @Column(nullable = true)
    private Departamento localidad;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServicioContratado> servicioContratados = new ArrayList<>();

    public Cliente() {
    }

    public Cliente(String nombre, String apellido, String cuit,
            CondicionFiscal condicionFiscal) {

        this.nombre = nombre.toUpperCase().trim();
        this.apellido = apellido.toUpperCase().trim();
        this.cuit = cuit;

        this.condicionFiscal = condicionFiscal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre.toUpperCase().trim();
        ;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido.toUpperCase().trim();
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public CondicionFiscal getCondicionFiscal() {
        return condicionFiscal;
    }

    public void setCondicionFiscal(CondicionFiscal condicionFiscal) {
        this.condicionFiscal = condicionFiscal;
    }

    public Departamento getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Departamento localidad) {
        this.localidad = localidad;
    }

    @Override
    public String toString() {
        return "Cliente [idCliente=" + idCliente + ", nombre=" + nombre + ", apellido=" + apellido + ", cuil=" + cuit
                + ", direccion=" + direccion + ", telefono=" + telefono + ", condicionFiscal="
                + condicionFiscal + "]";
    }

    public List<ServicioContratado> getServicioContratados() {
        return servicioContratados;
    }

    public void setServicioContratados(List<ServicioContratado> servicioContratados) {
        this.servicioContratados = servicioContratados;
    }

    public String getFullName() {
        return this.nombre + " " + this.apellido;
    }
}
