package com.argentafact.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.argentafact.model.DetalleFactura;
import com.argentafact.model.EstadoServicioContratado;
import com.argentafact.model.Factura;
import com.argentafact.model.ServicioContratado;
import com.argentafact.repository.ServicioContratadoRepository;

@Service
public class ServicioContratadoService {
    private final ServicioContratadoRepository servicioContratadoRepository;

    public ServicioContratadoService(ServicioContratadoRepository servicioContratadoRepository) {
        this.servicioContratadoRepository = servicioContratadoRepository;
    }

    public void guardar(ServicioContratado servicioContratado) {
        this.servicioContratadoRepository.save(servicioContratado);
    }

    public List<ServicioContratado> buscarTodos() {
        return this.servicioContratadoRepository.findAll();
    }

    public ServicioContratado findByIdServicioContratado(Long servicioContratado_id) {
        return this.servicioContratadoRepository.findByIdServicioContratado(servicioContratado_id);
    }

    public List<ServicioContratado> obtenerServiciosContratadosActivosDelMesActual() {
        var serviciosActuales = this.buscarTodos();
        LocalDate fechaActual = LocalDate.now();
        // filtrar por servicios activos
        // filtrar por mes actual
        List<ServicioContratado> serviciosAFacturar = new ArrayList<>();
        for (var servicioContratado : serviciosActuales) {
            if (servicioContratado.getEstado() == EstadoServicioContratado.ACTIVO) {
                if (servicioContratado.getFechaAlta().getMonth() == fechaActual.getMonth()) {
                    serviciosAFacturar.add(servicioContratado);
                }
            }
        }
        return serviciosAFacturar;
    }

    public List<ServicioContratado> obtenerServiciosAFacturar(List<ServicioContratado> serviciosActuales,
            List<Factura> facturasDelMes) {
        // filtrar los servicios que no tengan facturas en el mes actual
        // iterar sobre facturas del mes
        // iterar sobre servicioContratoMes
        // si el cliente de la factura coincide con el cliente del servicio continuar
        // iterar sobre detalles de factura
        // obtener el id del servicio en el detalle de factura y comparar con el
        // servicio contratado
        // si el id del servicio contratado no esta en los detalles de factura, facturar
        // ese servicio
        List<ServicioContratado> serviciosAFacturar = new ArrayList<>();
        for (Factura factura : facturasDelMes) {
            for (ServicioContratado servicioContrato : serviciosActuales) {
                if (factura.getCliente().getIdCliente() == servicioContrato.getCliente().getIdCliente()) {
                    boolean encontrado = false;
                    for (DetalleFactura detalle : factura.getDetalleFacturas()) {
                        if (detalle.getServicio().getIdServicio() == servicioContrato.getServicio().getIdServicio()) {
                            encontrado = true;
                            break;
                        }
                    }
                    if (!encontrado) {
                        // serviciosAFacturar.add(servicioContrato);
                        // generar factura para este servicio
                        if (servicioContrato.getEstado() == EstadoServicioContratado.ACTIVO) {

                            System.out.println("Generando factura para el servicio: " +
                                    servicioContrato.getServicio().getNombreServicio());
                            serviciosAFacturar.add(servicioContrato);
                        }
                    }
                }
            }
        }
        if (serviciosAFacturar.isEmpty()) {
            serviciosAFacturar = serviciosActuales;
        }
        // eliminar duplicados
        // eliminar duplicados
        HashSet<ServicioContratado> serviciosAFacturarHS = new HashSet<>(serviciosAFacturar);
        serviciosAFacturar.clear();
        serviciosAFacturar.addAll(serviciosAFacturarHS);
        return serviciosAFacturar;

    }

    public Page<ServicioContratado> buscarTodos(PageRequest of) {
        // obtener contratos activos
        var contratos = this.servicioContratadoRepository.findAll(of);
        List<ServicioContratado> contratosActivosList = contratos.stream()
                .filter(servicioContratado -> servicioContratado.getEstado() == EstadoServicioContratado.ACTIVO)
                .collect(Collectors.toList());
        Page<ServicioContratado> contratosActivos = new PageImpl<>(contratosActivosList);
        return contratosActivos;
    }

    public ServicioContratado findById(Long id) {
        return this.servicioContratadoRepository.findByIdServicioContratado(id);
    }
}
