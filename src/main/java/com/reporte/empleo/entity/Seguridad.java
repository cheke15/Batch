package com.reporte.empleo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Seguridad")
public class Seguridad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private Integer idUsuario;

    @Column(name = "correo", nullable = false, unique = true)
    private String correo;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "activo", nullable = false)
    private Character activo; // Cambiado a Character para evitar problemas con valores nulos

    @OneToOne
    @JoinColumn(name = "idEmpleado", referencedColumnName = "id_empleado", nullable = false)
    private Empleado empleado;

    // Constructor vacío requerido por JPA
    public Seguridad() {
    }

    // Constructor parametrizado (opcional)
    public Seguridad(String correo, String password, Character activo, Empleado empleado) {
        this.correo = correo;
        this.password = password;
        this.activo = activo;
        this.empleado = empleado;
    }

    // Getters y setters
    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Character getActivo() {
        return activo;
    }

    public void setActivo(Character activo) {
        this.activo = activo;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
}
