package com.argentafact.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.argentafact.service.ClienteService;
import com.argentafact.service.FacturaService;
import com.argentafact.service.CuentaService;
import com.argentafact.model.Cuenta;

@Controller
@RequestMapping("/cuentas/")
public class CuentaController {

    private final FacturaService facturaService;
    private final CuentaService cuentaService;
    private final ClienteService clienteService;

    public CuentaController(CuentaService cuentaService,
                            ClienteService clienteService,
                            FacturaService facturaService) {
        this.cuentaService = cuentaService;
        this.clienteService = clienteService;
        this.facturaService = facturaService;
    }

    @GetMapping("")
    public String listar(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "5") int tamano,
            Model model) {

        Page<Cuenta> paginaCuenta = cuentaService.listarTodas(
                PageRequest.of(pagina, tamano));

        model.addAttribute("cuentas", paginaCuenta.getContent());
        model.addAttribute("paginaActual", pagina);
        model.addAttribute("totalPaginas", paginaCuenta.getTotalPages());

        return "cuenta/listar";
    }

    @GetMapping("/{id}")
    public String ver(@PathVariable Long id, Model model) {

        var cuenta = cuentaService.obtenerPorId(id);
        var cliente = cuenta.getCliente();
        var facturas = facturaService.obtenerFacturasPorCliente(cliente.getIdCliente());
        var saldoPendiente = facturaService.calcularSaldoPendienteCliente(cliente.getIdCliente());

        model.addAttribute("cuenta", cuenta);
        model.addAttribute("cliente", cliente);
        model.addAttribute("facturas", facturas);
        model.addAttribute("saldoPendiente", saldoPendiente);

        return "cuenta/verCuenta";
    }

    @GetMapping("/nueva")
    public String nueva(Model model) {
        model.addAttribute("clientes", clienteService.buscarClientesSinCuenta());
        return "cuenta/nuevaCuenta";
    }

    @PostMapping("/guardar")
    public String guardar(@RequestParam Long idCliente,
                          RedirectAttributes ra) {
        try {
            cuentaService.crearCuenta(idCliente);
            ra.addFlashAttribute("mensaje", "Cuenta creada correctamente");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/cuentas/";
    }
}
