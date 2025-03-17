package com.itsqmet.formularioHC.Servicio;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itsqmet.formularioHC.Entidad.Libro;
import com.itsqmet.formularioHC.Entidad.Prestamo;
import com.itsqmet.formularioHC.Repositorio.PrestamoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.Font;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class PrestamoServicio {

    @Autowired
    PrestamoRepositorio prestamoRepositorio;

    public List<Prestamo> listarPrestamos() {
        return prestamoRepositorio.findAll();
    }

    public Optional<Prestamo> buscarPrestamo(Long id){
        return prestamoRepositorio.findById(id);
    }

    public Prestamo guardarPrestamo(Prestamo prestamo){

        return prestamoRepositorio.save(prestamo);
    }

    public void eliminarPrestamo(Long id){
        prestamoRepositorio.deleteById(id);
    }

    public String generarPPdf() throws DocumentException, IOException {
        List<Prestamo> prestamos = prestamoRepositorio.findAll();
        Document document = new Document();
        String rutaPdf = Paths.get("prestamos.pdf").toAbsolutePath().toString();
        try (FileOutputStream fos = new FileOutputStream(rutaPdf)) {
            PdfWriter.getInstance(document, fos);
            document.open();
            document.add(new Paragraph("Lista de Prestamos", FontFactory.getFont("Arial", 14, Font.BOLD)));
            PdfPTable tabla = new PdfPTable(6); // Número de columnas ajustado a 4
            tabla.setWidthPercentage(100);
            tabla.addCell(new PdfPCell(new Phrase("Codigo", FontFactory.getFont("Arial", 12))));
            tabla.addCell(new PdfPCell(new Phrase("Descripción", FontFactory.getFont("Arial", 12))));
            tabla.addCell(new PdfPCell(new Phrase("Fecha Prestamo", FontFactory.getFont("Arial", 12))));
            tabla.addCell(new PdfPCell(new Phrase("Fecha Devolución", FontFactory.getFont("Arial", 12))));
            tabla.addCell(new PdfPCell(new Phrase("Usuario", FontFactory.getFont("Arial", 12))));
            tabla.addCell(new PdfPCell(new Phrase("Libro", FontFactory.getFont("Arial", 12))));

            for (Prestamo prestamo : prestamos) {
                tabla.addCell(new PdfPCell(new Phrase(String.valueOf(prestamo.getId()), FontFactory.getFont("Arial", 11))));
                tabla.addCell(new PdfPCell(new Phrase(prestamo.getDescripcion(), FontFactory.getFont("Arial", 11))));
                tabla.addCell(new PdfPCell(new Phrase(String.valueOf(prestamo.getFechaPrestamo()), FontFactory.getFont("Arial", 11))));
                tabla.addCell(new PdfPCell(new Phrase(String.valueOf(prestamo.getFechaDevolucion()), FontFactory.getFont("Arial", 11))));
                tabla.addCell(new PdfPCell(new Phrase(String.valueOf(prestamo.getBibliotecario().getNombre()), FontFactory.getFont("Arial", 11))));
                tabla.addCell(new PdfPCell(new Phrase(String.valueOf(prestamo.getLibro().getTitulo()), FontFactory.getFont("Arial", 11))));
            }
            document.add(tabla);
            document.close();
        } catch (IOException | DocumentException e) {
            throw new IOException("No se puede generar el pdf", e);
        }
        return rutaPdf;
    }
}
