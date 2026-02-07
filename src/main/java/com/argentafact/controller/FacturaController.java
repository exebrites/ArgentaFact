package com.argentafact.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.argentafact.model.CondicionFiscal;
import com.argentafact.model.DetalleDeFacturaFormulario;
import com.argentafact.model.DetalleFactura;
import com.argentafact.model.Empleado;
import com.argentafact.model.EstadoFactura;
import com.argentafact.model.Factura;
import com.argentafact.model.FacturaSesion;
import com.argentafact.model.Linea;
import com.argentafact.model.ServicioContratado;
import com.argentafact.model.TipoFactura;
import com.argentafact.model.Usuario;
import com.argentafact.repository.UsuarioRepository;
import com.argentafact.service.ClienteService;
import com.argentafact.service.FacturaService;
import com.argentafact.service.ServicioContratadoService;
import com.argentafact.service.ServicioService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/facturas")
@SessionAttributes({ "detalleFactura", "facturaSesion" })
public class FacturaController {
    @Autowired
    private FacturaService facturaService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ServicioService servicioService;
    @Autowired
    private ServicioContratadoService servicioContratadoService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @ModelAttribute("detalleFactura")
    public DetalleDeFacturaFormulario setUpDetalleFacturaFormulario() {
        return new DetalleDeFacturaFormulario();
    }

    @ModelAttribute("facturaSesion")
    public FacturaSesion setUpDeFacturaSesion() {
        return new FacturaSesion();
    }

    @GetMapping("/")

    public String listarFacturas(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "5") int tamano,
            Model model) {

        Page<Factura> paginaFactura = facturaService.buscarTodos(
                PageRequest.of(pagina, tamano));

        // Solo lo esencial al modelo
        model.addAttribute("facturas", paginaFactura.getContent());
        model.addAttribute("paginaActual", pagina);
        model.addAttribute("totalPaginas", paginaFactura.getTotalPages());

        return "factura/listar";
    }

    @GetMapping("/crear")
    public String nuevaFactura(
            @ModelAttribute("detalleFactura") DetalleDeFacturaFormulario detalleFactura,
            Model model) {

        Factura factura = new Factura();
        var clientes = clienteService.buscarTodos();
        var servicios = servicioService.buscarTodos();
        model.addAttribute("factura", factura);
        model.addAttribute("clientes", clientes);
        model.addAttribute("servicios", servicios);

        return "factura/nuevaFactura";
    }

    @PostMapping("/")
    public String agregarFactura(@ModelAttribute("detalleFactura") DetalleDeFacturaFormulario detalleFactura,
            @ModelAttribute("factura") Factura factura, Model model,
            @ModelAttribute("facturaSesion") FacturaSesion facturaSesion, RedirectAttributes redirectAttributes) {
        // obtener empleado autenticado

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // 2. Buscar el usuario en la BD
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // 3. Obtener el empleado relacionado
        Empleado empleado = usuario.getEmpleado();
        factura.setEmpleado(empleado);
        String nroFactura = facturaService.generarNumeroFactura();
        factura.setNumeroFactura(nroFactura);
        factura.setTotal(detalleFactura.getTotal());
        factura.setEstado(EstadoFactura.PENDIENTE);

        // determinar el tipo de factura segun la condicion fiscal del cliente.
        if ((factura.getCliente().getCondicionFiscal().equals(CondicionFiscal.RESPONSABLE_INSCRIPTO))
                || (factura.getCliente().getCondicionFiscal().equals(CondicionFiscal.MONOTRIBUTISTA))) {
            factura.setTipoFactura(TipoFactura.A);
        } else {
            factura.setTipoFactura(TipoFactura.B);
        }
        // control de detalle vacio
        if (detalleFactura.getServiciosSeleccionados().isEmpty()) {
            redirectAttributes.addFlashAttribute("mensajeError", "Debe agregar un servicio a  la factura");
            return "redirect:/facturas/crear";
        }

        for (var linea : detalleFactura.getServiciosSeleccionados()) {
            DetalleFactura detalle = new DetalleFactura();
            var servicio = servicioService.findById(linea.getIdServicio());
            detalle.setServicio(servicio);
            detalle.setFactura(factura);
            detalle.setSubtotal(linea.getPrecio());
            factura.AgregarDetalle(detalle);
        }

        // factura sesion representa los mismos campos que factura con la funcion de ser
        // rederizable desde otra vista
        facturaSesion.setNumeroFactura(factura.getNumeroFactura());
        facturaSesion.setFechaEmision(factura.getFechaEmision());
        facturaSesion.setTipoFactura(factura.getTipoFactura());
        facturaSesion.setTotal(factura.getTotal());
        facturaSesion.setEstado(factura.getEstado());
        facturaSesion.setCliente(factura.getCliente());
        facturaSesion.setEmpleado(factura.getEmpleado());
        facturaSesion.setDetalleFacturas(factura.getDetalleFacturas());

        model.addAttribute("factura", factura);
        return "factura/previewFactura";
    }

    @GetMapping("/detalleFactura")
    public String verDetalleProducto(@ModelAttribute("detalleFactura") DetalleDeFacturaFormulario detalle,
            Model model) {
        var servicios = servicioService.buscarTodos();
        model.addAttribute("servicios", servicios);
        return "factura/vistaDetalleFactura";
    }

    @GetMapping("/agregarDetalleFactura")
    public String agregarDetalleFactura(@ModelAttribute("idServicio") Long idServicio,
            @ModelAttribute("detalleFactura") DetalleDeFacturaFormulario detalle,
            RedirectAttributes redirectAttributes) {

        var servicio = servicioService.findById(idServicio);
        // TODO crear servicio de detalle de facturacion sesion
        var linea = new Linea();
        linea.setNombre(servicio.getNombreServicio());
        linea.setPrecio(servicio.getPrecio());
        linea.setDescripcion(servicio.getDescripcion());
        linea.setIdServicio(servicio.getIdServicio());

        for (Linea detalleLinea : detalle.getServiciosSeleccionados()) {
            if (detalleLinea.getIdServicio() == idServicio) {
                redirectAttributes.addFlashAttribute("mensajeError", "Este servicio ya fue agregado");
                return "redirect:/facturas/crear";
            }
        }
        detalle.agregarServicio(linea);

        return "redirect:/facturas/crear";
    }

    @GetMapping("/confirmarDatos")
    public String confirmarDatos(@ModelAttribute("facturaSesion") FacturaSesion facturaSesion, SessionStatus status) {

        Factura factura = new Factura();

        factura.setNumeroFactura(facturaSesion.getNumeroFactura());
        factura.setFechaEmision(facturaSesion.getFechaEmision());
        factura.setTotal(facturaSesion.getTotal());
        factura.setEstado(facturaSesion.getEstado());
        factura.setCliente(facturaSesion.getCliente());
        factura.setEmpleado(facturaSesion.getEmpleado());
        factura.setDetalleFacturas(facturaSesion.getDetalleFacturas());
        factura.setTipoFactura(facturaSesion.getTipoFactura());
        // relacionar detalle con factura
        for (DetalleFactura detalle : facturaSesion.getDetalleFacturas()) {
            detalle.setFactura(factura);
        }
        facturaService.guardarFactura(factura);
        // Esto limpia TODOS los atributos de @SessionAttributes
        status.setComplete();

        return "redirect:/facturas/";
    }

    @GetMapping("/limpiarDetalle")
    public String eliminarLinea(
            @ModelAttribute("detalleFactura") DetalleDeFacturaFormulario detalleFactura,
            HttpSession session) {

        detalleFactura.limpiar();

        return "redirect:/facturas/crear";
    }
    // eliminar un servicio de detalle de factura

    @GetMapping("/eliminarDetalle/{idServicio}")
    public String eliminarDetalleFactura(@PathVariable Long idServicio,
            @ModelAttribute("detalleFactura") DetalleDeFacturaFormulario detalle,
            RedirectAttributes redirectAttributes) {

        detalle.eliminarServicio(idServicio);

        return "redirect:/facturas/crear";
    }

    @GetMapping("/facturarServicioContratado")
    public String facturarServicioContratado(Model model) {

        // obtener todos los contratos activos del mes actual

        var serviciosActuales = servicioContratadoService.obtenerServiciosContratadosActivosDelMesActual();

        // verificar si tienen que no tengan facturas en el mes actual
        // todas las facturas de este mes
        var facturasDelMes = facturaService
                .obtenerFacturasDelMesActual();

        // obtener servicios a facturar que no tengan facturas
        List<ServicioContratado> serviciosAFacturar = servicioContratadoService.obtenerServiciosAFacturar(
                serviciosActuales,
                facturasDelMes);
        System.out.println("servicios a facturas \n");
        System.out.println(serviciosAFacturar);
        model.addAttribute("servicioContratados", serviciosAFacturar);
        return "factura/facturarServicioContratado";
    }

    @PostMapping("/facturarServicioContratado")
    public String guardarFacturaPorServicio(@ModelAttribute("servicioContratado_id") Long servicioContratado_id) {
        var servicioContratado = servicioContratadoService.findByIdServicioContratado(servicioContratado_id);
        // facturar
        var factura = new Factura();
        // obtener empleado autenticado
        // 1. Obtener el username del usuario autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // 2. Buscar el usuario en la BD
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // 3. Obtener el empleado relacionado
        Empleado empleado = usuario.getEmpleado();
        factura.setEmpleado(empleado);
        String nroFactura = facturaService.generarNumeroFactura();
        factura.setNumeroFactura(nroFactura);
        factura.setTotal(servicioContratado.getPrecioAcordado());
        factura.setEstado(EstadoFactura.PENDIENTE);
        factura.setCliente(servicioContratado.getCliente());
        factura.setFechaEmision(LocalDate.now());
        // // determinar el tipo de factura segun la condicion fiscal del cliente.
        if ((servicioContratado.getCliente().getCondicionFiscal().equals(CondicionFiscal.RESPONSABLE_INSCRIPTO))
                || (factura.getCliente().getCondicionFiscal().equals(CondicionFiscal.MONOTRIBUTISTA))) {
            factura.setTipoFactura(TipoFactura.A);
        } else {
            factura.setTipoFactura(TipoFactura.B);
        }

        // generar los detalles de factura
        var detalleFactura = new DetalleFactura(factura, servicioContratado.getServicio(),
                servicioContratado.getPrecioAcordado());

        factura.AgregarDetalle(detalleFactura);
        facturaService.guardarFactura(factura);
        return "redirect:/facturas/";
    }

}
