package com.reporte.empleo.service;

import com.reporte.empleo.entity.Seguridad;
import com.reporte.empleo.repository.SeguridadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeguridadService {

    @Autowired
    private SeguridadRepository seguridadRepository;

    // Buscar Seguridad por idUsuario (convertido a Integer)
    public Seguridad findById(String idUsuario) {
        Integer idUsuarioInt = Integer.parseInt(idUsuario);  // Convertimos el String a Integer
        return seguridadRepository.findById(idUsuarioInt).orElse(null);  // Buscamos con el id convertido
    }

    // Guardar una nueva seguridad en la base de datos
    public Seguridad save(Seguridad seguridad) {
        return seguridadRepository.save(seguridad);
    }
}
