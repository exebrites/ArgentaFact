package com.argentafact.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.argentafact.model.Usuario;
import com.argentafact.service.UsuarioService;

import jakarta.validation.Valid;

@Controller
public class RegistroController {

    private final UsuarioService usuarioService;

    public RegistroController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Muestra el formulario de registro
     */
    @GetMapping("/register")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "register";
    }

    /**
     * Procesa el registro de un nuevo usuario
     */
    @PostMapping("/register")
    public String registrarUsuario(
            @Valid @ModelAttribute("usuario") Usuario usuario,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model) {

        // Verificar si hay errores de validación de anotaciones
        if (result.hasErrors()) {
            return "register";
        }

        // Validar que las contraseñas coincidan
        if (!usuario.getPassword().equals(usuario.getConfirmarPassword())) {
            model.addAttribute("errorPassword", "Las contraseñas no coinciden");
            return "register";
        }

        // Validar que el username no exista
        if (usuarioService.existeUsername(usuario.getUsername())) {
            model.addAttribute("errorUsername",
                    "El nombre de usuario '" + usuario.getUsername() + "' ya está en uso");
            return "register";
        }

        // Validar que el email no exista
        if (usuarioService.existeEmail(usuario.getEmail())) {
            model.addAttribute("errorEmail",
                    "El email '" + usuario.getEmail() + "' ya está registrado");
            return "register";
        }

        try {
            // Registrar el usuario
            Usuario usuarioRegistrado = usuarioService.registrarUsuario(usuario);

            // Redirigir al login con mensaje de éxito
            redirectAttributes.addFlashAttribute("registroExitoso",
                    "¡Registro exitoso! Por favor, inicie sesión con sus credenciales.");
            redirectAttributes.addFlashAttribute("usernameRegistrado",
                    usuarioRegistrado.getUsername());

            return "redirect:/login";

        } catch (Exception e) {
            // Manejar errores inesperados
            model.addAttribute("errorGeneral",
                    "Ha ocurrido un error al registrar el usuario. Por favor, intente nuevamente.");
            return "register";
        }
    }
}
