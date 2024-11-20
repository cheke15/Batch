package com.reporte.empleo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class EmpleadoDTO {
    private String idEmpleado;
    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private String sexo;

    @JsonFormat(pattern = "yyyy-MM-dd")  // Asegura el formato correcto de la fecha
    private Date FN;

    private String descripcionPuesto;
    private Integer idPuesto;
    private Double salario;

    // Getters y setters
    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoP() {
        return apellidoP;
    }

    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }

    public String getApellidoM() {
        return apellidoM;
    }

    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getFN() {
        return FN;
    }

    public void setFN(Date FN) {
        this.FN = FN;
    }

    public String getDescripcionPuesto() {
        return descripcionPuesto;
    }

    public void setDescripcionPuesto(String descripcionPuesto) {
        this.descripcionPuesto = descripcionPuesto;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public Integer getIdPuesto() {
        return idPuesto;
    }

    public void setIdPuesto(Integer idPuesto) {
        this.idPuesto = idPuesto;
    }
}
