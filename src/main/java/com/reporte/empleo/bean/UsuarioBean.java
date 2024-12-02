package com.reporte.empleo.bean;

import com.reporte.empleo.dto.EmpleadoDTO;
import com.reporte.empleo.service.EmpleadoService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
@ViewScoped
@Getter
@Setter
public class UsuarioBean implements Serializable {

    @Autowired
    private EmpleadoService empleadoService;

    private List<EmpleadoDTO> empleados;
    private EmpleadoDTO nuevoEmpleado;
    private EmpleadoDTO empleadoSeleccionado;
    private String filtroNombre;
    private String filtroSexo;

    @PostConstruct
    public void init() {
        nuevoEmpleado = new EmpleadoDTO();
        empleadoSeleccionado = new EmpleadoDTO();
        cargarEmpleados();
    }

    public void cargarEmpleados() {
        try {
            empleados = empleadoService.buscarEmpleados(filtroNombre, filtroSexo);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error", "No se pudieron cargar los empleados"));
        }
    }

    public void guardarEmpleado() {
        try {
            // Si hay un ID, estamos editando; de lo contrario, creando
            if (empleadoSeleccionado != null && empleadoSeleccionado. getIdEmpleado() != null) {
                empleadoService.saveEmpleado(empleadoSeleccionado);
            } else {
                empleadoService.saveEmpleado(nuevoEmpleado);
            }
            cargarEmpleados();
            nuevoEmpleado = new EmpleadoDTO();
            empleadoSeleccionado = new EmpleadoDTO();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Éxito", "Empleado guardado correctamente"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error", "No se pudo guardar el empleado"));
        }
    }

    public void eliminarEmpleado() {
        try {
            if (empleadoSeleccionado != null && empleadoSeleccionado.getIdEmpleado() != null) {
                empleadoService.deleteEmpleadoById(empleadoSeleccionado.getIdEmpleado());
                cargarEmpleados();
                empleadoSeleccionado = new EmpleadoDTO();
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Éxito", "Empleado eliminado correctamente"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error", "No se pudo eliminar el empleado"));
        }
    }

    public void seleccionarEmpleado(EmpleadoDTO empleado) {
        this.empleadoSeleccionado = empleado;
    }
}