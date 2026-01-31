package com.argentafact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.argentafact.model.Empleado;
import com.argentafact.service.EmpleadoService;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {
    @Autowired
    private EmpleadoService empleadoService;

    // TODO listar empleados 
    @GetMapping("/")
    public String guardarEmpleado(Model model) {
        var empleados =  empleadoService.buscarTodos();
        model.addAttribute("empleados", empleados);
        return "empleado/listar";
    }


    // TODO nuevo empleado con vista 
    @GetMapping(("/nuevoEmpleado"))
    public String nuevoEmpleado(Model model) {
        Empleado empleado = new Empleado();
        model.addAttribute("empleado", empleado);
        return "empleado/nuevoEmpleado";
    }
    // TODO  agreagar empleado 
    @PostMapping("/")
    public String agregarEmpleado(@ModelAttribute("empleado") Empleado empleado) {
        empleadoService.guardar(empleado);
        return "redirect:/empleados/";
    }
}
