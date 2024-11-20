package com.reporte.empleo.controller;

import com.reporte.empleo.entity.Seguridad;
import com.reporte.empleo.service.SeguridadService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/seguridad")
public class SeguridadController {

    @Autowired
    private SeguridadService seguridadService;

    @GetMapping("/login")
    public String loginForm(Model model) {
        return "login";  // Vista de login
    }

    @PostMapping("/login")
    public String login(@RequestParam("idUsuario") String idUsuario,
                        @RequestParam("password") String password,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {

        Seguridad user = seguridadService.findById(idUsuario);
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Usuario no encontrado");
            return "redirect:/seguridad/login";
        }

        if (!user.getPassword().equals(password)) {
            redirectAttributes.addFlashAttribute("error", "Contraseña incorrecta");
            return "redirect:/seguridad/login";
        }

        if (user.getActivo() == 'N') {
            redirectAttributes.addFlashAttribute("error", "Usuario inactivo");
            return "redirect:/seguridad/login";
        }

        // Establecer al usuario en la sesión
        session.setAttribute("user", user);
        return "redirect:/empleados";  // Redirigir a la lista de empleados
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();  // Invalidar sesión
        return "redirect:/seguridad/login";  // Redirigir al login
    }
}
