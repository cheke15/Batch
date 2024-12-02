package com.reporte.empleo.service;

import com.reporte.empleo.dto.SeguridadDTO;
import com.reporte.empleo.dto.EmpleadoDTO;
import com.reporte.empleo.entity.Seguridad;
import com.reporte.empleo.entity.Empleado;
import com.reporte.empleo.repository.SeguridadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class SeguridadService {

    @Autowired
    private SeguridadRepository seguridadRepository;
    // Método para encontrar por ID
    @Transactional(readOnly = true)
    public SeguridadDTO findById(String idUsuario) {
        // Convertir String a Integer
        Integer id = Integer.parseInt(idUsuario);

        // Buscar en el repositorio
        Seguridad seguridad = seguridadRepository.findById(id)
                .orElse(null);

        if (seguridad == null) {
            return null;
        }

        return convertToDTO(seguridad);
    }

    @Transactional(readOnly = true)
    public SeguridadDTO findByCorreo(String correo) {
        Seguridad seguridad = seguridadRepository.findByCorreo(correo);

        if (seguridad == null) {
            return null;
        }

        return convertToDTO(seguridad);
    }

    public SeguridadDTO convertToDTO(Seguridad seguridad) {
        if (seguridad == null) {
            return null;
        }

        SeguridadDTO dto = new SeguridadDTO();
        dto.setIdUsuario(seguridad.getIdUsuario().toString());
        dto.setPassword(seguridad.getPassword());
        dto.setActivo(seguridad.getActivo());

        // Configurar información del empleado
        if (seguridad.getEmpleado() != null) {
            EmpleadoDTO empleadoDTO = convertEmpleadoToDTO(seguridad.getEmpleado());
            dto.setIdEmpleado(empleadoDTO.getIdEmpleado());
            dto.setNombreEmpleado(buildFullName(seguridad.getEmpleado()));
        }

        return dto;
    }

    // Método para construir nombre completo
    private String buildFullName(Empleado empleado) {
        StringBuilder fullName = new StringBuilder();

        if (empleado.getNombre() != null) {
            fullName.append(empleado.getNombre());
        }

        if (empleado.getApellidoP() != null) {
            fullName.append(" ").append(empleado.getApellidoP());
        }

        if (empleado.getApellidoM() != null) {
            fullName.append(" ").append(empleado.getApellidoM());
        }

        return fullName.toString().trim();
    }

    // Método para convertir Empleado a EmpleadoDTO
    private EmpleadoDTO convertEmpleadoToDTO(Empleado empleado) {
        if (empleado == null) {
            return null;
        }

        EmpleadoDTO dto = new EmpleadoDTO();
        // Usar el método getIdEmpleadoAsString() de tu entidad
        dto.setIdEmpleado(empleado.getIdEmpleadoAsString());
        dto.setNombre(empleado.getNombre());
        dto.setApellidoP(empleado.getApellidoP());
        dto.setApellidoM(empleado.getApellidoM());
        dto.setSexo(String.valueOf(empleado.getSexo()));
        dto.setFN(empleado.getFN());

        // Configurar información de salario/puesto
        if (empleado.getSalario() != null) {
            dto.setDescripcionPuesto(empleado.getSalario().getDescripcion());
            dto.setIdPuesto(empleado.getSalario().getIdPuesto());

            // Conversión explícita de BigDecimal a Double
            BigDecimal salarioBigDecimal = empleado.getSalario().getSalario();
            dto.setSalario(salarioBigDecimal != null ? salarioBigDecimal.doubleValue() : null);
        }

        return dto;
    }

    @Transactional
    public Seguridad save(SeguridadDTO dto) {
        Seguridad seguridad = convertToEntity(dto);
        return seguridadRepository.save(seguridad);
    }

    public Seguridad convertToEntity(SeguridadDTO dto) {
        if (dto == null) {
            return null;
        }

        Seguridad seguridad = new Seguridad();

        if (dto.getIdUsuario() != null && !dto.getIdUsuario().isEmpty()) {
            seguridad.setIdUsuario(Integer.parseInt(dto.getIdUsuario()));
        }

        seguridad.setPassword(dto.getPassword());
        seguridad.setActivo(dto.getActivo());

        // Configurar empleado
        if (dto.getIdEmpleado() != null && !dto.getIdEmpleado().isEmpty()) {
            Empleado empleado = new Empleado();
            empleado.setIdEmpleadoFromString(dto.getIdEmpleado());
            seguridad.setEmpleado(empleado);
        }

        return seguridad;
    }

    public boolean validarCredenciales(String correo, String password) {
        SeguridadDTO usuario = findByCorreo(correo);

        if (usuario == null) {
            return false;
        }

        // Comparar contraseñas (considera usar BCrypt en producción)
        return usuario.getPassword().equals(password);
    }
}