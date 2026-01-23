package com.argentafact.model;

public class Empresa {
    // TODO definir atributos de la empresa
    private final String nombre = "EMPRESA XXXX";
    private final String direccion = "Félix de Azara 1552. ";
    private final String telefono = " (+54) 0376-4435091. ";
    private final String correoElectronico = "empresa@gmail.com";
    private final String razonSocial = "EMPRESA XXXX";
    private final int puntoVenta = 1;
    private final CondicionFiscal condicionFiscal = CondicionFiscal.RESPONSABLE_INSCRIPTO;

    public Empresa() {
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public int getPuntoVenta() {
        return puntoVenta;
    }

    public CondicionFiscal getCondicionFiscal() {
        return condicionFiscal;
    }

}
