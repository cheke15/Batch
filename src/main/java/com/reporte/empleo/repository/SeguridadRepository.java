package com.reporte.empleo.repository;

import com.reporte.empleo.entity.Seguridad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeguridadRepository extends JpaRepository<Seguridad, Integer> {
   // Buscar por correo
   Seguridad findByCorreo(String correo);
}