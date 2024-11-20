package com.reporte.empleo.Batch;

import com.reporte.empleo.entity.Empleado;
import com.reporte.empleo.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class ReporteBatch {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private PdfGenerator pdfGenerator;

    @Autowired
    private XlsGenerator xlsGenerator;

    @Autowired
    private TxtGenerator txtGenerator;

    public void generateReports() throws IOException {
        List<Empleado> empleados = empleadoRepository.findAll();
        String outputDir = "reports/";

        // Limpiar la carpeta de reportes si ya existe
        File dir = new File(outputDir);
        if (dir.exists()) {
            for (File file : dir.listFiles()) {
                file.delete();
            }
        } else {
            dir.mkdirs();
        }

        // Generar reportes
        pdfGenerator.generatePdf(outputDir + "empleados.pdf", empleados);
        xlsGenerator.generateXls(outputDir + "empleados.xlsx", empleados);
        txtGenerator.generateTxt(outputDir + "empleados.txt", empleados);

        // Verificar que los reportes fueron generados
        if (new File(outputDir + "empleados.pdf").exists() &&
                new File(outputDir + "empleados.xlsx").exists() &&
                new File(outputDir + "empleados.txt").exists()) {

            // Crear archivo ZIP
            zipFiles(outputDir);
        } else {
            System.err.println("Error: Algunos reportes no se generaron correctamente.");
        }
    }

    private void zipFiles(String outputDir) throws IOException {
        String zipFilePath = "reports.zip";

        try (FileOutputStream fos = new FileOutputStream(zipFilePath);
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            File dir = new File(outputDir);
            for (File file : dir.listFiles()) {
                if (file.isFile()) {  // Verifica si es un archivo y no un directorio
                    ZipEntry entry = new ZipEntry(file.getName());
                    zos.putNextEntry(entry);
                    zos.write(java.nio.file.Files.readAllBytes(file.toPath()));
                    zos.closeEntry();
                }
            }

        }
        System.out.println("ZIP generado: " + zipFilePath);
    }
}
