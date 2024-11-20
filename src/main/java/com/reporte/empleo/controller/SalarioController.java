package com.reporte.empleo.controller;

import com.reporte.empleo.entity.Salario;
import com.reporte.empleo.service.SalarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salarios")
public class SalarioController {
    @Autowired
    private SalarioService salarioService;

    // Obtener todos los registros de Salario
    @GetMapping
    public List<Salario> getAllSalarios() {
        return salarioService.findAll();
    }

    // Crear o actualizar un Salario
    @PostMapping
    public Salario createOrUpdateSalario(@RequestBody Salario salario) {
        return salarioService.save(salario);
    }

    // Obtener un Salario por ID
    @GetMapping("/{id}")
    public Salario getSalarioById(@PathVariable Integer id) {
        return salarioService.findById(id);
    }

    // Eliminar un Salario por ID
    @DeleteMapping("/{id}")
    public void deleteSalario(@PathVariable Integer id) {
        salarioService.deleteById(id);
    }
}
