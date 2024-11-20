package com.reporte.empleo.Batch;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import com.reporte.empleo.entity.Empleado;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class PdfGenerator {

    public void generatePdf(String filePath, List<Empleado> empleados) throws IOException {
        // Crear un nuevo documento PDF
        try (PDDocument document = new PDDocument()) {
            // Crear una nueva página
            PDPage page = new PDPage();
            document.addPage(page);

            // Declarar el flujo de contenido fuera del bloque try
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            try {
                // Título del reporte
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
                contentStream.newLineAtOffset(200, 750); // Ajusta la posición según sea necesario
                contentStream.showText("Reporte de Empleados");
                contentStream.endText();

                // Agregar los datos de los empleados al documento PDF
                int yPosition = 700; // Posición inicial en el eje Y
                for (Empleado empleado : empleados) {
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    contentStream.newLineAtOffset(50, yPosition);
                    contentStream.showText(
                            "ID: " + empleado.getIdEmpleado() + ", Nombre: " + empleado.getNombre() +
                                    ", Sexo: " + empleado.getSexo() + ", Puesto: " + (empleado.getSalario() != null ? empleado.getSalario().getDescripcion() : "N/A")
                    );
                    contentStream.endText();
                    yPosition -= 15; // Espacio entre líneas

                    // Verificar si se necesita una nueva página
                    if (yPosition < 50) { // Si la posición Y es menor a 50, crear una nueva página
                        contentStream.close(); // Cerrar el flujo de contenido anterior
                        page = new PDPage();
                        document.addPage(page);
                        contentStream = new PDPageContentStream(document, page); // Crear un nuevo flujo de contenido
                        yPosition = 750; // Reiniciar la posición Y
                    }
                }
            } finally {
                // Asegurarse de cerrar el flujo de contenido al final
                contentStream.close();
            }

            // Guardar el documento en el archivo especificado
            document.save(filePath);
            System.out.println("PDF generado: " + filePath);
        }
    }
}