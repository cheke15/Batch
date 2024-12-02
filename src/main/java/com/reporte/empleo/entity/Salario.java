package com.reporte.empleo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;  // Importación añadida

@Entity
@Table(name = "Salario")
public class Salario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPuesto")
    private Integer idPuesto;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "salario", nullable = false)
    private BigDecimal salario;

    @OneToMany(mappedBy = "salario", cascade = CascadeType.ALL)
    private List<Empleado> empleados;

    // Constructor vacío requerido por JPA
    public Salario() {
    }

    // Constructor con parámetros (opcional, por si necesitas crear objetos manualmente)
    public Salario(String descripcion, BigDecimal salario) {
        this.descripcion = descripcion;
        this.salario = salario;
    }

    // Getters y setters
    public Integer getIdPuesto() {
        return idPuesto;
    }

    public void setIdPuesto(Integer idPuesto) {
        this.idPuesto = idPuesto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }
}
