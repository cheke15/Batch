package com.reporte.empleo.bean;

import com.reporte.empleo.dto.SeguridadDTO;
import com.reporte.empleo.service.SeguridadService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.Serializable;

@Named
@ViewScoped
@Getter
@Setter
public class LoginBean implements Serializable {

    @Autowired
    private SeguridadService seguridadService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private String correo;
    private String password;

    public String login() {
        try {
            // Validar campos
            if (!validarCampos()) {
                return null;
            }

            // Intentar autenticación con Spring Security
            Authentication authToken = new UsernamePasswordAuthenticationToken(correo, password);
            Authentication authentication = authenticationManager.authenticate(authToken);

            // Establecer contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Obtener usuario
            SeguridadDTO usuario = seguridadService.findByCorreo(correo);

            // Verificar si el usuario está activo
            if (usuario.getActivo() == 'N') {
                addErrorMessage("Usuario inactivo");
                SecurityContextHolder.clearContext();
                return null;
            }

            // Guardar en sesión
            guardarEnSesion(usuario);

            // Redirección exitosa
            return "/pages/empleados.xhtml?faces-redirect=true";

        } catch (AuthenticationException e) {
            // Manejo de credenciales inválidas
            addErrorMessage("Credenciales inválidas");
            return null;
        } catch (Exception e) {
            // Manejo de otros errores
            addErrorMessage("Error en el inicio de sesión: " + e.getMessage());
            return null;
        }
    }

    public String logout() {
        // Limpiar contexto de seguridad
        SecurityContextHolder.clearContext();

        // Invalidar sesión
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().invalidateSession();

        // Redirigir a página de login
        return "/login.xhtml?faces-redirect=true";
    }

    private boolean validarCampos() {
        boolean valido = true;

        if (correo == null || correo.trim().isEmpty()) {
            addErrorMessage("Correo es requerido");
            valido = false;
        }

        if (password == null || password.trim().isEmpty()) {
            addErrorMessage("Contraseña es requerida");
            valido = false;
        }

        return valido;
    }

    private void guardarEnSesion(SeguridadDTO usuario) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put("usuario", usuario);
    }

    private void addErrorMessage(String mensaje) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", mensaje));
    }
}