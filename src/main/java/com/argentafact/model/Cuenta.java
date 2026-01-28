package com.argentafact.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cuentas")
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCuenta;

    @Column(nullable = false)
    private BigDecimal saldo = BigDecimal.ZERO;

    @OneToOne
    @JoinColumn(name = "idCliente", nullable = false, unique = true)
    private Cliente cliente;

    protected Cuenta() {}

    public Cuenta(Cliente cliente) {
        this.cliente = cliente;
        this.saldo = BigDecimal.ZERO;
    }

    public Long getIdCuenta() {
        return idCuenta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void registrarPago(BigDecimal monto) {
        debitar(monto);
    }

    public void acreditar(BigDecimal monto) {
        validarMonto(monto);
        this.saldo = this.saldo.add(monto);
    }

    public void debitar(BigDecimal monto) {
        validarMonto(monto);
        this.saldo = this.saldo.subtract(monto);
    }

    private void validarMonto(BigDecimal monto) {
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("El monto debe ser mayor a cero");
        }
    }
}
