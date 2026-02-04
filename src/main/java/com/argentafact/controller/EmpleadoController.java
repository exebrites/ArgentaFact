package com.argentafact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.argentafact.model.Empleado;
import com.argentafact.model.Usuario;
import com.argentafact.service.EmpleadoService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {
    @Autowired
    private EmpleadoService empleadoService;

    // listar empleados
    @GetMapping("/")
    public String guardarEmpleado(Model model) {
        var empleados = empleadoService.buscarTodos();
        model.addAttribute("empleados", empleados);
        return "empleado/listar";
    }

    // nuevo empleado con vista
    @GetMapping(("/nuevoEmpleado"))
    public String nuevoEmpleado(Model model) {
        Empleado empleado = new Empleado();
        model.addAttribute("empleado", empleado);
        return "empleado/nuevoEmpleado";
    }

    // agreagar empleado
    @PostMapping("/")
    public String agregarEmpleado(@ModelAttribute("empleado") Empleado empleado) {
        empleadoService.guardar(empleado);
        return "redirect:/empleados/";
    }


     

    
    /**
     * Muestra listado de empleados
     */
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("empleados", empleadoService.listarTodos());
        return "empleados/listado";
    }
    
    /**
     * Muestra formulario de registro combinado
     */
    @GetMapping("/nuevo")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("empleado", new Empleado());
        model.addAttribute("usuario", new Usuario());
        return "empleados/formulario-registro";
    }
    
    /**
     * Procesa el registro combinado de Empleado + Usuario
     */
    @PostMapping("/registrar")
    public String registrar(
            @Valid @ModelAttribute("empleado") Empleado empleado,
            BindingResult resultEmpleado,
            @Valid @ModelAttribute("usuario") Usuario usuario,
            BindingResult resultUsuario,
            RedirectAttributes redirectAttributes,
            Model model) {
        
        // 1. Validar errores de anotaciones
        if (resultEmpleado.hasErrors() || resultUsuario.hasErrors()) {
            return "empleados/formulario-registro";
        }
        
        // 2. Intentar registrar (validaciones de negocio en servicio)
        try {
            empleadoService.registrarEmpleadoConUsuario(empleado, usuario);
            redirectAttributes.addFlashAttribute("mensaje", "Empleado registrado exitosamente");
            redirectAttributes.addFlashAttribute("tipo", "success");
            return "redirect:/empleados";
            
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "empleados/formulario-registro";
        }
    }
    
    /**
     * TODO Muestra detalle de un empleado
     */
    // @GetMapping("/{id}")
    // public String detalle(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
    //     return empleadoService.buscarPorId(id)
    //         .map(empleado -> {
    //             model.addAttribute("empleado", empleado);
    //             return "empleados/detalle";
    //         })
    //         .orElseGet(() -> {
    //             redirectAttributes.addFlashAttribute("error", "Empleado no encontrado");
    //             return "redirect:/empleados";
    //         });
    // }
    
    /**
     * Desactiva un empleado
     */
    @PostMapping("/{id}/desactivar")
    public String desactivar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            empleadoService.desactivarEmpleado(id);
            redirectAttributes.addFlashAttribute("mensaje", "Empleado desactivado correctamente");
            redirectAttributes.addFlashAttribute("tipo", "warning");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/empleados";
    }
}
