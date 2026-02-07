package com.argentafact.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.argentafact.utils.DateFormatterUtil;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "facturas")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFactura;
    private String numeroFactura;
    private LocalDate fechaEmision;
    private TipoFactura tipoFactura;
    private BigDecimal total;
    private EstadoFactura estado;

    private BigDecimal saldoPendiente;

     
    private boolean baja;
    
    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_empleado", nullable = false)
    private Empleado empleado;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleFactura> detalleFacturas = new ArrayList<>();

    public void AgregarDetalle(DetalleFactura detalle) {
        if (!this.detalleFacturas.contains(detalle)) {
            this.detalleFacturas.add(detalle);
            detalle.setFactura(this);
        }
    }

    public void removerDetalle(DetalleFactura detalle) {
        this.detalleFacturas.remove(detalle);
        detalle.setFactura(null);
    }

    public Factura() {
    }

    public Factura(String numeroFactura, LocalDate fechaEmision, TipoFactura tipoFactura, BigDecimal total,
            EstadoFactura estado) {
        this.numeroFactura = numeroFactura;
        this.fechaEmision = fechaEmision;
        this.tipoFactura = tipoFactura;
        this.total = total;
        this.estado = EstadoFactura.PENDIENTE;
        this.baja = false;
        this.saldoPendiente = total;
    }

    public Long getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Long idFactura) {
        this.idFactura = idFactura;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public String getFechaEmisionConFormato() {
        var fecha = getFechaEmision();
        return fecha != null ? String.format("%02d/%02d/%04d", fechaEmision.getDayOfMonth(),
                fechaEmision.getMonthValue(), fechaEmision.getYear()) : "";
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public TipoFactura getTipoFactura() {
        return tipoFactura;
    }

    public void setTipoFactura(TipoFactura tipoFactura) {
        this.tipoFactura = tipoFactura;
    }

    public BigDecimal getTotal() {
        return total;
    }
    public BigDecimal getTotalConIva(){
        return getTotal();
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public EstadoFactura getEstado() {
        return estado;
    }

    public BigDecimal getSaldoPendiente() {
        return saldoPendiente; 
    }

    public void setSaldoPendiente(BigDecimal saldoPendiente) {
        this.saldoPendiente = saldoPendiente;
    }

    public void setEstado(EstadoFactura estado) {
        this.estado = estado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<DetalleFactura> getDetalleFacturas() {
        return detalleFacturas;
    }

    public void setDetalleFacturas(List<DetalleFactura> detalleFacturas) {
        this.detalleFacturas = detalleFacturas;
    }

    public BigDecimal getIva(){
        return getTotal().multiply(BigDecimal.valueOf(0.21));
    }
    public boolean isBaja() {
        return baja;
    }
    public void setBaja(boolean baja) {
        this.baja = baja;
    }
    public String getFechaFormateada(){
        return DateFormatterUtil.format(fechaEmision);
    }
    public String getNumeroFacturaConMontoFormateado(){
        return this.numeroFactura + " - $" + this.saldoPendiente;
    }

    public void aplicarPago(BigDecimal monto) {
        if (estado == EstadoFactura.ANULADA) {
            throw new IllegalStateException("Factura anulada");
        }

        if (monto.compareTo(saldoPendiente) > 0) {
            throw new IllegalArgumentException("Monto excede saldo");
        }

        saldoPendiente = saldoPendiente.subtract(monto);

        if (saldoPendiente.compareTo(BigDecimal.ZERO) == 0) {
            estado = EstadoFactura.PAGADA;
        } else {
            estado = EstadoFactura.PARCIALMENTE_PAGADA;
        }
    }

    public boolean tieneSaldoPendiente() {
        return saldoPendiente != null && saldoPendiente.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean estaActiva() {   
        return estado != EstadoFactura.ANULADA && !baja;
    }

    @Override
    public String toString() {
        return "Factura [idFactura=" + idFactura + ", numeroFactura=" + numeroFactura + ", fechaEmision=" + fechaEmision
                + ", tipoFactura=" + tipoFactura + ", total=" + total + ", estado=" + estado + ", cliente=" + cliente
                + ", empleado=" + empleado + ", detalleFacturas=" + detalleFacturas + "]";
    }

}
