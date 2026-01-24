package com.argentafact.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.argentafact.model.Cliente;
import com.argentafact.model.Empleado;
import com.argentafact.model.HistoricoFiscal;
import com.argentafact.repository.HistoricoFiscalRepository;

@Service
public class HistoricoFiscalService {

    private final HistoricoFiscalRepository historicoRepository;
    private final ClienteService clienteService;
    private final EmpleadoService empleadoService;

    public HistoricoFiscalService(HistoricoFiscalRepository historicoRepository,
                                  ClienteService clienteService,
                                  EmpleadoService empleadoService) {
        this.historicoRepository = historicoRepository;
        this.clienteService = clienteService;
        this.empleadoService = empleadoService;
    }


    public void registrarOperacion(Long idEmpleado,
                                   Long idCliente,
                                   String tipoOperacion,
                                   String descripcion) {

        Empleado empleado = empleadoService.buscarPorId(idEmpleado);
        Cliente cliente = clienteService.buscarPorId(idCliente);

        HistoricoFiscal historico = new HistoricoFiscal(
                LocalDate.now(),
                empleado,
                cliente,
                tipoOperacion,
                descripcion
        );

        historicoRepository.save(historico);
    }

    public List<HistoricoFiscal> listarTodos() {
        return historicoRepository.findAll();
    }

    public HistoricoFiscal obtenerPorId(Long idHistorico) {
        return historicoRepository.findById(idHistorico)
                .orElseThrow(() -> new RuntimeException(
                        "Histórico fiscal no encontrado (id=" + idHistorico + ")"
                ));
    }
}
