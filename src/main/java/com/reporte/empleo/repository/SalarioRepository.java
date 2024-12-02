package com.reporte.empleo.repository;

import com.reporte.empleo.entity.Salario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalarioRepository extends JpaRepository<Salario, Integer> {

}
