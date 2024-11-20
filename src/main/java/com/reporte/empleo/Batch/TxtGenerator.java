package com.reporte.empleo.Batch;
import com.reporte.empleo.entity.Empleado;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
public class TxtGenerator {

    public void generateTxt(String filePath, List<Empleado> empleados) throws IOException {
        FileWriter writer = new FileWriter(filePath);

        writer.write("Reporte de Empleados\n");
        writer.write("=====================\n");
        for (Empleado empleado : empleados) {
            writer.write(
                    "ID: " + empleado.getIdEmpleado() + ", Nombre: " + empleado.getNombre() +
                            ", Sexo: " + empleado.getSexo() + ", Puesto: " +
                            (empleado.getSalario() != null ? empleado.getSalario().getDescripcion() : "N/A") + "\n"
            );
        }

        writer.close();
        System.out.println("TXT generado: " + filePath);
    }
}
