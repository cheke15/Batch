package com.reporte.empleo.repository;

import com.reporte.empleo.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {

    // Buscar empleados por nombre (case-insensitive)
    List<Empleado> findByNombreContainingIgnoreCase(String nombre);

    // Buscar empleados por sexo (carácter)
    List<Empleado> findBySexo(char sexo);

    // Buscar empleados por nombre y sexo
    List<Empleado> findByNombreContainingIgnoreCaseAndSexo(String nombre, char sexo);
}
