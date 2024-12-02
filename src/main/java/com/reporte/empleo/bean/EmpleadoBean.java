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
public class EmpleadoBean implements Serializable {

    @Autowired
    private EmpleadoService empleadoService;

    private List<EmpleadoDTO> empleados;
    private EmpleadoDTO nuevoEmpleado;
    private String filtroSexo;
    private String filtroBusqueda;

    @PostConstruct
    public void init() {
        nuevoEmpleado = new EmpleadoDTO();
        cargarEmpleados();
    }

    public void cargarEmpleados() {
        try {
            empleados = empleadoService.buscarEmpleados(filtroBusqueda, filtroSexo);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error", "No se pudieron cargar los empleados"));
        }
    }

    public void guardarEmpleado() {
        try {
            empleadoService.saveEmpleado(nuevoEmpleado);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Éxito", "Empleado guardado correctamente"));

            nuevoEmpleado = new EmpleadoDTO();
            cargarEmpleados();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error", "No se pudo guardar el empleado: " + e.getMessage()));
        }
    }

    public void eliminarEmpleado(String idEmpleado) {
        try {
            empleadoService.deleteEmpleadoById(idEmpleado);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Éxito", "Empleado eliminado correctamente"));

            cargarEmpleados();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error", "No se pudo eliminar el empleado"));
        }
    }

    public void filtrar() {
        cargarEmpleados();
    }

    public void limpiarFiltros() {
        filtroBusqueda = null;
        filtroSexo = null;
        cargarEmpleados();
    }
}