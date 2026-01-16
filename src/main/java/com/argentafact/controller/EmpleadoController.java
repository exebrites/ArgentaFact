package com.argentafact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.argentafact.model.Empleado;
import com.argentafact.service.EmpleadoService;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {
    @Autowired
    private EmpleadoService empleadoService;
    @GetMapping("/")
    public String guardarEmpleado(Model model) {
        empleadoService.guardar(new Empleado("Juan"));
        var empleados =  empleadoService.buscarTodos();
        model.addAttribute("empleados", empleados);
        return "empleado/listar";
    }
}
