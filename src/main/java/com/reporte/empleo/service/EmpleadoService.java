package com.reporte.empleo.service;

import com.reporte.empleo.dto.EmpleadoDTO;
import com.reporte.empleo.entity.Empleado;
import com.reporte.empleo.entity.Salario;
import com.reporte.empleo.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    // Método para obtener todos los empleados como DTOs
    @Transactional(readOnly = true)
    public List<EmpleadoDTO> obtenerTodosLosEmpleados() {
        return empleadoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Método para buscar empleados con filtros
    @Transactional(readOnly = true)
    public List<EmpleadoDTO> buscarEmpleados(String nombre, String sexo) {
        List<Empleado> empleados;

        if (nombre != null && !nombre.isEmpty() && sexo != null && !sexo.isEmpty()) {
            empleados = empleadoRepository.findByNombreContainingIgnoreCaseAndSexo(nombre, sexo.charAt(0));
        } else if (nombre != null && !nombre.isEmpty()) {
            empleados = empleadoRepository.findByNombreContainingIgnoreCase(nombre);
        } else if (sexo != null && !sexo.isEmpty()) {
            empleados = empleadoRepository.findBySexo(sexo.charAt(0));
        } else {
            empleados = empleadoRepository.findAll();
        }

        return empleados.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Método para guardar o actualizar un empleado
    @Transactional
    public EmpleadoDTO saveEmpleado(EmpleadoDTO empleadoDTO) {
        // Validaciones
        if (empleadoDTO == null) {
            throw new IllegalArgumentException("El empleado no puede ser nulo");
        }

        if (empleadoDTO.getNombre() == null || empleadoDTO.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }

        // Convertir DTO a Entidad
        Empleado empleado = convertToEntity(empleadoDTO);

        // Guardar y convertir de vuelta a DTO
        Empleado empleadoGuardado = empleadoRepository.save(empleado);
        return convertToDTO(empleadoGuardado);
    }

    // Método para eliminar un empleado por ID
    @Transactional
    public void deleteEmpleadoById(String idEmpleado) {
        Integer id = Integer.parseInt(idEmpleado);
        if (!empleadoRepository.existsById(id)) {
            throw new RuntimeException("Empleado no encontrado");
        }
        empleadoRepository.deleteById(id);
    }

    // Método para obtener un empleado por ID
    @Transactional(readOnly = true)
    public EmpleadoDTO getEmpleadoById(String idEmpleado) {
        Integer id = Integer.parseInt(idEmpleado);
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        return convertToDTO(empleado);
    }

    // Métodos de conversión
    public EmpleadoDTO convertToDTO(Empleado empleado) {
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

    public Empleado convertToEntity(EmpleadoDTO dto) {
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