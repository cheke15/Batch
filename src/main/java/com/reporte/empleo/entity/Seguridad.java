package com.reporte.empleo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Seguridad")
public class Seguridad {

    @Id
    @Column(name = "idUsuario")
    private Integer idUsuario;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "activo", nullable = false)
    private char activo;

    @OneToOne
    @JoinColumn(name = "idEmpleado", referencedColumnName = "id_Empleado")
    private Empleado empleado;

    @Column(name = "idEmpleado", insertable = false, updatable = false)
    private Integer idEmpleado;

    // Getters y setters
    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public char getActivo() {
        return activo;
    }

    public void setActivo(char activo) {
        this.activo = activo;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
}
