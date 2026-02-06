package com.argentafact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.argentafact.model.Empleado;
import com.argentafact.model.Usuario;
import com.argentafact.repository.EmpleadoRepository;
import com.argentafact.repository.UsuarioRepository;
import com.argentafact.service.EmpleadoService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/empleados")
@SessionAttributes("empleadoSesion")
public class EmpleadoController {
    @Autowired
    private EmpleadoService empleadoService;
    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    // listar empleados

    // nuevo empleado con vista

    @ModelAttribute("empleadoSesion")
    public Empleado setUpEmpleadoFormulario() {
        return new Empleado();
    }

    /**
     * Muestra listado de empleados
     */
    @GetMapping("/")

    public String listar(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "5") int tamano,
            Model model) {

        Page<Empleado> paginaEmpleados = empleadoService.buscarTodos(
                PageRequest.of(pagina, tamano));

        // Solo lo esencial al modelo
        model.addAttribute("empleados", paginaEmpleados.getContent());
        model.addAttribute("paginaActual", pagina);
        model.addAttribute("totalPaginas", paginaEmpleados.getTotalPages());
        return "empleado/listar";
    }

    @GetMapping(("/nuevoEmpleado"))
    public String nuevoEmpleado(Model model) {
        model.addAttribute("empleado", new Empleado());
        // model.addAttribute("usuario", new Usuario());
        return "empleado/formulario-registro-empleado";
    }

    @PostMapping("/registrar-empleado")
    public String registrarEmpleado(
            @Valid @ModelAttribute("empleado") Empleado empleado,
            BindingResult resultEmpleado,
            RedirectAttributes redirectAttributes,
            Model model,
            @ModelAttribute("empleadoSesion") Empleado empleadoSesion) {

        // 2. Intentar registrar (validaciones de negocio en servicio)
        // 1. Validar errores de anotaciones
        if (resultEmpleado.hasErrors()) {
            return "empleado/formulario-registro-empleado";
        }
        // model.addAttribute("empleado", empleado);
        Usuario usuario = new Usuario();

        empleadoSesion.setNombre(empleado.getNombre());
        empleadoSesion.setApellido(empleado.getApellido());
        empleadoSesion.setDni(empleado.getDni());
        empleadoSesion.setEmail(empleado.getEmail());
        empleadoSesion.setTelefono(empleado.getTelefono());
        empleadoSesion.setFechaIngreso(empleado.getFechaIngreso());
        empleadoSesion.setDepartamento(empleado.getDepartamento());
        empleadoSesion.setCargo(empleado.getCargo());
        model.addAttribute("usuario", usuario);

        return "empleado/formulario-registro-usuario";
    }

    /**
     * Procesa el registro combinado de Empleado + Usuario
     */
    @PostMapping("/registrar")
    public String registrar(
            @ModelAttribute("empleadoSesion") Empleado empleadoSesion,
            @Valid @ModelAttribute("usuario") Usuario usuario,
            BindingResult resultUsuario,
            RedirectAttributes redirectAttributes,
            Model model) {
        var empleado = empleadoSesion;
        System.out.println("Usuario a registrar: " + usuario);
        System.out.println(" ");
        System.out.println("empleado a registrar: " + empleado);
        // 2. Intentar registrar (validaciones de negocio en servicio)
        // 1. Validar errores de anotaciones
        if (resultUsuario.hasErrors()) {
            return "empleado/formulario-registro-usuario";
        }

        try {
            // empleadoService.registrarEmpleadoConUsuario(usuario.getEmpleado(), usuario);
            // 1. Validaciones de negocio - Empleado
            if (empleadoRepository.existsByDni(empleado.getDni())) {
                throw new IllegalArgumentException("Ya existe un empleado con el DNI: " +
                        empleado.getDni());
            }

            if (empleadoRepository.existsByEmail(empleado.getEmail())) {
                throw new IllegalArgumentException("Ya existe un empleado con el email: " +
                        empleado.getEmail());
            }

            // 2. Validaciones de negocio - Usuario
            if (usuarioRepository.existsByUsername(usuario.getUsername())) {
                throw new IllegalArgumentException("El username '" + usuario.getUsername() +
                        "' ya está en uso");
            }

            if (usuarioRepository.existsByEmail(usuario.getEmail())) {
                throw new IllegalArgumentException("El email de usuario ya está registrado");
            }

            // 3. Validar confirmación de contraseña
            if (!usuario.getPassword().equals(usuario.getConfirmarPassword())) {
                throw new IllegalArgumentException("Las contraseñas no coinciden");
            }

            // 4. Encriptar contraseña
            String passwordEncriptada = passwordEncoder.encode(usuario.getPassword());
            usuario.setPassword(passwordEncriptada);

            // 5. Establecer relación bidireccional
            empleado.setUsuario(usuario);
            // El método setUsuario de Empleado ya establece la relación inversa

            // 6. Guardar (CASCADE persiste también Usuario)
            empleadoRepository.save(empleado);
            redirectAttributes.addFlashAttribute("mensaje", "Empleado registrado exitosamente");
            redirectAttributes.addFlashAttribute("tipo", "success");
            return "redirect:/empleados/";

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "empleado/formulario-registro-usuario";
        }
    }

    /**
     * TODO Muestra detalle de un empleado
     */
    // @GetMapping("/{id}")
    // public String detalle(@PathVariable Long id, Model model, RedirectAttributes
    // redirectAttributes) {
    // return empleadoService.buscarPorId(id)
    // .map(empleado -> {
    // model.addAttribute("empleado", empleado);
    // return "empleados/detalle";
    // })
    // .orElseGet(() -> {
    // redirectAttributes.addFlashAttribute("error", "Empleado no encontrado");
    // return "redirect:/empleados";
    // });
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
