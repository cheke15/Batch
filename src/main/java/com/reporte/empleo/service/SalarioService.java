package com.reporte.empleo.service;

import com.reporte.empleo.entity.Salario;
import com.reporte.empleo.repository.SalarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalarioService {
    @Autowired
    private SalarioRepository salarioRepository;

    // Obtener todos los registros de Salario
    public List<Salario> findAll() {
        return salarioRepository.findAll();
    }

    // Guardar o actualizar un Salario
    public Salario save(Salario salario) {
        return salarioRepository.save(salario);
    }

    // Buscar un Salario por su ID
    public Salario findById(Integer idPuesto) {
        return salarioRepository.findById(idPuesto).orElse(null);
    }

    // Eliminar un Salario por su ID
    public void deleteById(Integer idPuesto) {
        salarioRepository.deleteById(idPuesto);
    }
}

