package com.luisdbb.tarea3AD2024base.services;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.FormacionEmpresa;

/**
 * Servicio encargado de generar informes PDF
 * relacionados con las Formaciones en Empresa (FE).
 */
@Service
public class InformeService {

    /**
     * Genera un informe PDF con todas las FE registradas.
     *
     * @param fes lista de FE
     * @param archivo archivo destino
     * @throws IOException error al generar el PDF
     */
    public void exportarPDF(
            List<FormacionEmpresa> fes,
            File archivo
    ) throws IOException {

        PDDocument document = new PDDocument();

        PDPage page = new PDPage();

        document.addPage(page);

        PDPageContentStream content =
                new PDPageContentStream(document, page);

        // ===== FUENTES =====

        PDType1Font font =
                new PDType1Font(
                        Standard14Fonts.FontName.HELVETICA
                );

        PDType1Font bold =
                new PDType1Font(
                        Standard14Fonts.FontName.HELVETICA_BOLD
                );

        int y = 750;

        // ===== TITULO =====

        content.beginText();

        content.setFont(bold, 22);

        content.newLineAtOffset(50, y);

        content.showText(
                "INFORME DE FORMACIONES EN EMPRESA"
        );

        content.endText();

        y -= 40;

        // ===== FECHA =====

        content.beginText();

        content.setFont(font, 12);

        content.newLineAtOffset(50, y);

        content.showText(
                "Fecha: " + LocalDate.now()
        );

        content.endText();

        y -= 50;

        // ===== FE =====

        for (FormacionEmpresa fe : fes) {

        	String alumno =
        	        fe.getEstudiante() != null
        	        ? fe.getEstudiante().getNombre()
        	            + " "
        	            + fe.getEstudiante().getApellidos()
        	        : "Sin alumno";

            String tutor =
                    fe.getTutor() != null
                    ? fe.getTutor().getNombre()
                        + " "
                        + fe.getTutor().getApellidos()
                    : "Sin tutor";

            String empresa =
                    fe.getEmpresa() != null
                    ? fe.getEmpresa().getNombre()
                    : "-";

            String estado =
                    fe.getEstado() != null
                    ? fe.getEstado().toString()
                    : "-";

            String inicio =
                    fe.getFechaInicio() != null
                    ? fe.getFechaInicio().toString()
                    : "-";

            String fin =
                    fe.getFechaFin() != null
                    ? fe.getFechaFin().toString()
                    : "-";

            // ===== CAJA =====

            content.addRect(
                    45,
                    y - 90,
                    500,
                    80
            );

            content.stroke();

            // ===== ALUMNO =====

            content.beginText();

            content.setFont(bold, 13);

            content.newLineAtOffset(60, y - 20);

            content.showText(
                    "Alumno: " + alumno
            );

            content.endText();

            // ===== EMPRESA =====

            content.beginText();

            content.setFont(font, 11);

            content.newLineAtOffset(60, y - 40);

            content.showText(
                    "Empresa: " + empresa
            );

            content.endText();

            // ===== TUTOR =====

            content.beginText();

            content.newLineAtOffset(60, y - 55);

            content.showText(
                    "Tutor: " + tutor
            );

            content.endText();

            // ===== ESTADO =====

            content.beginText();

            content.newLineAtOffset(320, y - 40);

            content.showText(
                    "Estado: " + estado
            );

            content.endText();

            // ===== FECHAS =====

            content.beginText();

            content.newLineAtOffset(320, y - 55);

            content.showText(
                    "Periodo: "
                    + inicio
                    + " - "
                    + fin
            );

            content.endText();

            y -= 110;

            // ===== NUEVA PAGINA =====

            if (y < 120) {

                content.close();

                page = new PDPage();

                document.addPage(page);

                content =
                        new PDPageContentStream(
                                document,
                                page
                        );

                y = 750;
            }
        }

        // ===== TOTAL =====

        content.beginText();

        content.setFont(bold, 12);

        content.newLineAtOffset(50, 50);

        content.showText(
                "Total FE registradas: "
                + fes.size()
        );

        content.endText();

        content.close();

        document.save(archivo);

        document.close();
    }
}