package com.argentafact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.argentafact.model.Servicio;
import com.argentafact.service.ServicioService;

@Controller
@RequestMapping("/servicios")
public class ServicioController {
    @Autowired
    private ServicioService servicioService;

    @GetMapping("/")
    public String listarServicios(Model model) {
        // crear servicio
        var servicio = new Servicio("Limpieza");
        servicioService.guardar(servicio);
        // listar
        var servicios = servicioService.buscarTodos();
        model.addAttribute("servicios", servicios);
        return "servicio/listar";
    }
}
