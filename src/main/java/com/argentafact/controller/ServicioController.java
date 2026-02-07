package com.argentafact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.argentafact.model.Servicio;
import com.argentafact.service.ServicioService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;


@Controller
@RequestMapping("/servicios/")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    // LISTAR servicios (PAGINADO)
    @GetMapping({ "", "/" })
    public String listarServicios(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "5") int tamano,
            Model model) {

        Page<Servicio> paginaServicio = servicioService.buscarTodos(
                PageRequest.of(pagina, tamano));

        model.addAttribute("servicios", paginaServicio.getContent());
        model.addAttribute("paginaActual", pagina);
        model.addAttribute("totalPaginas", paginaServicio.getTotalPages());

        return "servicio/listar";
    }

    // FORMULARIO NUEVO
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("servicio", new Servicio());
        return "servicio/nuevo";
    }

    // GUARDAR
    @PostMapping("/guardar")
    public String guardarServicio(@Valid @ModelAttribute("servicio") Servicio servicio,
            BindingResult result) {
        if (result.hasErrors()) {
            // Retorna a la vista con los errores
            return "servicio/nuevo";
        }

        servicioService.guardar(servicio);

        return "redirect:/servicios/";
    }

    @GetMapping("/{id}/eliminar")
    public String eliminarServicio(@PathVariable Long id, Model model) {
        var servicio = servicioService.findById(id);
        model.addAttribute("servicio", servicio);
        return "servicio/eliminarServicio";
    }

    @DeleteMapping("/{id}")
    public String bajaServicio(@PathVariable Long id, Model model) {
        try {
            servicioService.eliminar(id);
        } catch (Exception e) {
            model.addAttribute("error", "Error al eliminar el servicio: " + e.getMessage());
            return "servicio/eliminarServicio";
        }
        return "redirect:/servicios/";
    }
    @GetMapping("{id}")
    public String verServicio(@PathVariable("id") Long id, Model model) {
        var servicio = servicioService.findById(id);
        model.addAttribute("servicio", servicio);
        return "servicio/verServicio";
    }

    @GetMapping("{id}/editar")
    public String editarServicio(@PathVariable("id") Long id, Model model) {
        var servicio = servicioService.findById(id);
        model.addAttribute("servicio", servicio);
        return "servicio/editarServicio";
    }
    @PutMapping("{id}")
    public String actualizarServicio(@PathVariable("id") Long id,
            @ModelAttribute("servicio") Servicio servicio) {
        servicioService.actualizarServicioPorId(id, servicio);
        return "redirect:/servicios/";
    }
    
}
