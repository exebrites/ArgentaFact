package com.argentafact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.argentafact.model.Alicuota;
import com.argentafact.service.AlicuotaService;

@Controller
@RequestMapping("/alicuotas")
public class AlicuotaController {

    @Autowired
    private AlicuotaService alicuotaService;

    @GetMapping("/")
    public String listarAlicuotas(Model model) {
        var alicuotas = alicuotaService.buscarTodas();
        model.addAttribute("alicuotas", alicuotas);
        return "alicuota/listar";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("alicuota", new Alicuota());
        return "alicuota/nuevo";
    }

    @PostMapping("/guardar")
    public String guardarAlicuota(Alicuota alicuota) {
        alicuotaService.guardar(alicuota);
        return "redirect:/alicuotas/";
    }
}
