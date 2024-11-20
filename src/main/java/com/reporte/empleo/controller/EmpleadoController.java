package com.reporte.empleo.controller;

import com.reporte.empleo.dto.EmpleadoDTO;
import com.reporte.empleo.service.EmpleadoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping
    public String listarEmpleados(@RequestParam(value = "search", required = false) String search,
                                  @RequestParam(value = "sexo", required = false) String sexo,
                                  Model model,
                                  HttpSession session) {

        List<EmpleadoDTO> empleados = empleadoService.buscarEmpleados(search, sexo);
        model.addAttribute("empleados", empleados);
        model.addAttribute("search", search);
        model.addAttribute("sexo", sexo);
        model.addAttribute("empleado", new EmpleadoDTO());

        return "empleados";
    }

    @GetMapping("/agregar")
    public String agregarEmpleadoForm(Model model, HttpSession session) {
        model.addAttribute("empleado", new EmpleadoDTO());
        return "formEmpleado";
    }

    @GetMapping("/editar/{id}")
    public String editarEmpleadoForm(@PathVariable("id") String id, Model model, HttpSession session) {
        EmpleadoDTO empleadoDTO = empleadoService.getEmpleadoById(id);
        model.addAttribute("empleado", empleadoDTO);
        return "formEmpleado";
    }

    @PostMapping("/guardar")
    public ResponseEntity<Map<String, Object>> guardarEmpleado(@RequestBody EmpleadoDTO empleadoDTO, HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        try {
            empleadoService.saveEmpleado(empleadoDTO);
            response.put("success", true);
            response.put("message", empleadoDTO.getIdEmpleado () != null
                    ? "Empleado actualizado exitosamente"
                    : "Empleado agregado exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Hubo un error al procesar la solicitud: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/eliminar")
    public ResponseEntity<Map<String, Object>> eliminarEmpleado(@RequestParam("idEmpleado") String idEmpleado, HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        try {
            empleadoService.deleteEmpleadoById(idEmpleado);
            response.put("success", true);
            response.put("message", "Empleado eliminado exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar el empleado");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}