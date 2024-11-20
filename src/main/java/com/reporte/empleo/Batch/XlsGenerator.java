package com.reporte.empleo.Batch;
import com.reporte.empleo.entity.Empleado;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class XlsGenerator {

    public void generateXls(String filePath, List<Empleado> empleados) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Empleados");

        // Encabezados
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Nombre");
        header.createCell(2).setCellValue("Sexo");
        header.createCell(3).setCellValue("Puesto");

        // Filas de datos
        int rowNum = 1;
        for (Empleado empleado : empleados) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(empleado.getIdEmpleado());
            row.createCell(1).setCellValue(empleado.getNombre());
            row.createCell(2).setCellValue(String.valueOf(empleado.getSexo()));
            row.createCell(3).setCellValue(
                    empleado.getSalario() != null ? empleado.getSalario().getDescripcion() : "N/A"
            );
        }

        // Escribir en archivo
        FileOutputStream fileOut = new FileOutputStream(filePath);
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();

        System.out.println("XLS generado: " + filePath);
    }
}
