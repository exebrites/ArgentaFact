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
@RequestMapping("/servicios/")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    // LISTAR servicios
    @GetMapping
    public String listarServicios(Model model) {
        model.addAttribute("servicios", servicioService.buscarTodos());
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
    public String guardarServicio(Servicio servicio) {
        servicioService.guardar(servicio);
        return "redirect:/servicios";
    }
}