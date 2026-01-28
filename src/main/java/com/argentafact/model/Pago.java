package com.argentafact.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "pagos")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPago;

    private LocalDate fecha;

    @Column(nullable = false)
    private BigDecimal monto;

    @Enumerated(EnumType.STRING)
    private TipoPago tipoPago;

    @Enumerated(EnumType.STRING)
    private EstadoPago estadoPago;

    //Relaciones

    @ManyToOne
    @JoinColumn(name = "idFactura", nullable = false)
    private Factura factura;

    @ManyToOne
    @JoinColumn(name = "idEmpleado", nullable = false)
    private Empleado empleado;

    //Constructores

    protected Pago() {}

    public Pago(BigDecimal monto, TipoPago tipoPago, Factura factura, Empleado empleado) {
        this.fecha = LocalDate.now();
        this.monto = monto;
        this.tipoPago = tipoPago;
        this.factura = factura;
        this.empleado = empleado;
        this.estadoPago = EstadoPago.REGISTRADO;
    }

    // Getters
    public Long getIdPago() {
        return idPago; 
    }

    public BigDecimal getMonto() { 
        return monto; 
    }
    
    public Factura getFactura() {
        return factura; 

    }
    
    public EstadoPago getEstadoPago() { 
        return estadoPago; 
    }
    
    public LocalDate getFecha() {
        return fecha;
    }

    public TipoPago getTipoPago() {
        return tipoPago;
    }
}
