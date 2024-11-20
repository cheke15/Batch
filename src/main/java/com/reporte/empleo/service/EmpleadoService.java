package com.reporte.empleo.service;

import com.reporte.empleo.dto.EmpleadoDTO;
import com.reporte.empleo.entity.Empleado;
import com.reporte.empleo.entity.Salario;
import com.reporte.empleo.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public List<EmpleadoDTO> buscarEmpleados(String search, String sexo) {
        List<Empleado> empleados;

        if (search != null && !search.isEmpty() && sexo != null && !sexo.isEmpty()) {
            if (sexo.length() == 1 && (sexo.equals("M") || sexo.equals("F"))) {
                empleados = empleadoRepository.findByNombreContainingIgnoreCaseAndSexo(search, sexo.charAt(0));
            } else {
                throw new IllegalArgumentException("Sexo inválido. Debe ser 'M' o 'F'");
            }
        } else if (search != null && !search.isEmpty()) {
            empleados = empleadoRepository.findByNombreContainingIgnoreCase(search);
        } else if (sexo != null && !sexo.isEmpty()) {
            if (sexo.length() == 1 && (sexo.equals("M") || sexo.equals("F"))) {
                empleados = empleadoRepository.findBySexo(sexo.charAt(0));
            } else {
                throw new IllegalArgumentException("Sexo inválido. Debe ser 'M' o 'F'");
            }
        } else {
            empleados = empleadoRepository.findAll();
        }

        return empleados.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public EmpleadoDTO getEmpleadoById(String id) {
        Empleado empleado = empleadoRepository.findById(Integer.parseInt(id))
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        return convertToDTO(empleado);
    }

    public EmpleadoDTO saveEmpleado(EmpleadoDTO empleadoDTO) {
        if (empleadoDTO.getFN() == null) {
            throw new IllegalArgumentException("La fecha de nacimiento (FN) no puede ser nula");
        }

        Empleado empleado = convertToEntity(empleadoDTO);
        empleado = empleadoRepository.save(empleado);
        return convertToDTO(empleado);
    }

    public void deleteEmpleadoById(String idEmpleado) {
        empleadoRepository.deleteById(Integer.parseInt(idEmpleado));
    }

    private EmpleadoDTO convertToDTO(Empleado empleado) {
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setIdEmpleado(empleado.getIdEmpleado().toString());
        dto.setNombre(empleado.getNombre());
        dto.setApellidoP(empleado.getApellidoP());
        dto.setApellidoM(empleado.getApellidoM());
        dto.setSexo(String.valueOf(empleado.getSexo()));
        dto.setFN(empleado.getFN());


        if (empleado.getSalario() != null) {
            dto.setSalario(empleado.getSalario().getSalario().doubleValue());
            dto.setDescripcionPuesto(empleado.getSalario().getDescripcion());
            dto.setIdPuesto(empleado.getSalario().getIdPuesto());
        }

        return dto;
    }

    private Empleado convertToEntity(EmpleadoDTO dto) {
        Empleado empleado = new Empleado();
        if (dto.getIdEmpleado() != null && !dto.getIdEmpleado().isEmpty()) {
            empleado.setIdEmpleado(Integer.parseInt(dto.getIdEmpleado()));
        }
        empleado.setNombre(dto.getNombre());
        empleado.setApellidoP(dto.getApellidoP());
        empleado.setApellidoM(dto.getApellidoM());
        empleado.setSexo(dto.getSexo().charAt(0));
        empleado.setFN(dto.getFN());

        if (dto.getSalario() != null) {
            Salario salario = new Salario();
            salario.setSalario(BigDecimal.valueOf(dto.getSalario()));
            salario.setDescripcion(dto.getDescripcionPuesto());
            salario.setIdPuesto(dto.getIdPuesto());
            empleado.setSalario(salario);
        }

        return empleado;
    }
}