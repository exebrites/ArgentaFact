package com.argentafact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.argentafact.model.Servicio;
import com.argentafact.service.ServicioService;

@Controller
@RequestMapping("/servicios")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    // LISTAR servicios
    @GetMapping("/")
    public String listarServicios(Model model) {
        var servicios = servicioService.buscarTodos();
        model.addAttribute("servicios", servicios);
        return "servicio/listar";
    }

    // MOSTRAR formulario de alta
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("servicio", new Servicio());
        return "servicio/nuevo";
    }

    // GUARDAR servicio
    @PostMapping("/guardar")
    public String guardarServicio(Servicio servicio) {
        servicioService.guardar(servicio);
        return "redirect:/servicios/";
    }
}
