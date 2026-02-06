package com.argentafact.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.argentafact.model.Cliente;
import com.argentafact.model.Cuenta;
import com.argentafact.repository.CuentaRepository;

@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final ClienteService clienteService;

    public CuentaService(CuentaRepository cuentaRepository,
                         ClienteService clienteService) {
        this.cuentaRepository = cuentaRepository;
        this.clienteService = clienteService;
    }

    public List<Cuenta> listarTodas() {
        return cuentaRepository.findAll();
    }

    public Page<Cuenta> listarTodas(PageRequest of) {
        return cuentaRepository.findAll(of);
    }

    public Cuenta obtenerPorId(Long idCuenta) {
        return cuentaRepository.findById(idCuenta)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
    }

    @Transactional
    public Cuenta crearCuenta(Long idCliente) {
        Cliente cliente = clienteService.buscarPorId(idCliente);

        if (cuentaRepository.existsByClienteIdCliente(idCliente)) {
            throw new RuntimeException("El cliente ya posee una cuenta");
        }

        Cuenta cuenta = new Cuenta(cliente);
        return cuentaRepository.save(cuenta);
    }

    @Transactional
    public void acreditar(Long idCuenta, BigDecimal monto) {
        validarMonto(monto);
        Cuenta cuenta = obtenerPorId(idCuenta);
        cuenta.acreditar(monto);
    }

    @Transactional
    public void debitar(Long idCuenta, BigDecimal monto) {
        validarMonto(monto);
        Cuenta cuenta = obtenerPorId(idCuenta);
        cuenta.debitar(monto);
    }

    private void validarMonto(BigDecimal monto) {
        if (monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("El monto debe ser positivo");
        }
    }

    @Transactional(readOnly = true)
    public Cuenta obtenerCuentaPorCliente(Long idCliente) {
        return cuentaRepository.findByClienteIdCliente(idCliente)
                .orElseThrow(() -> new RuntimeException("El cliente no tiene cuenta"));
    }
}
