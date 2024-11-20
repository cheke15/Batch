package com.reporte.empleo.entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "Empleado")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Usa Identity para bases de datos como MySQL
    @Column(name = "id_empleado")
    private Integer idEmpleado;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido_P")
    private String apellidoP;

    @Column(name = "apellido_M")
    private String apellidoM;

    @Column(name = "sexo", nullable = false)
    private char sexo;


    @Temporal(TemporalType.DATE)
    @Column(name = "FN")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date FN;

    @ManyToOne
    @JoinColumn(name = "idPuesto", referencedColumnName = "idPuesto")
    private Salario salario;

    @Column(name = "idPuesto", insertable = false, updatable = false)
    private Integer idPuesto;

    @Column(name = "descripcionPuesto")
    private String descripcionPuesto;  // Descripción del puesto

    // Getters y setters
    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
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

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public Date getFN() {
        return FN;
    }

    public void setFN(Date FN) {
        this.FN = FN;
    }

    public Salario getSalario() {
        return salario;
    }

    public void setSalario(Salario salario) {
        this.salario = salario;
    }

    public Integer getIdPuesto() {
        return idPuesto;
    }

    public void setIdPuesto(Integer idPuesto) {
        this.idPuesto = idPuesto;
    }

    // Getter y setter para descripcionPuesto
    public String getDescripcionPuesto() {
        return descripcionPuesto;
    }

    public void setDescripcionPuesto(String descripcionPuesto) {
        this.descripcionPuesto = descripcionPuesto;
    }

    // Método adicional para representar el idEmpleado como String con ceros a la izquierda
    public String getIdEmpleadoAsString() {
        return String.format("%08d", idEmpleado);
    }

    // Método adicional para convertir de String a Integer el idEmpleado
    public void setIdEmpleadoFromString(String idEmpleadoString) {
        this.idEmpleado = Integer.parseInt(idEmpleadoString);
    }
}
