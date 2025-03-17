package com.itsqmet.formularioHC.Servicio;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itsqmet.formularioHC.Entidad.Autor;
import com.itsqmet.formularioHC.Entidad.Prestamo;
import com.itsqmet.formularioHC.Repositorio.AutorRepositorio;
import jakarta.transaction.Transactional;
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
public class AutorServicio {
    @Autowired
    AutorRepositorio autorRepositorio;

    public List<Autor> listarAutores() {

        return autorRepositorio.findAll();
    }

    public List<Autor> buscarAutorNombre(String buscarAutor) {
        if (buscarAutor == null || buscarAutor.isEmpty()) {
            return autorRepositorio.findAll();
        } else {
            return autorRepositorio.findByNombreContainingIgnoreCase(buscarAutor);
        }
    }

    public Optional<Autor> buscarAutor(Long id) {

        return autorRepositorio.findById(id);
    }

    public Autor guardarAutor(Autor autor) {

        return autorRepositorio.save(autor);
    }

    public void eliminarAutor(Long id) {
        autorRepositorio.deleteById(id);
    }

    //METODO PARA OBTENER PROVEEDOR CON PRODUCTOS
    @Transactional
    public Autor obtenerAutorConLibros(Long id) {
        Autor autor = autorRepositorio.findById(id).orElseThrow();
        return autor;
    }

    public String generarAPdf() throws DocumentException, IOException {
        List<Autor> autores = autorRepositorio.findAll();
        Document document = new Document();
        String rutaPdf = Paths.get("autores.pdf").toAbsolutePath().toString();
        try (FileOutputStream fos = new FileOutputStream(rutaPdf)) {
            PdfWriter.getInstance(document, fos);
            document.open();
            document.add(new Paragraph("Lista de Autores", FontFactory.getFont("Arial", 14, Font.BOLD)));
            PdfPTable tabla = new PdfPTable(4); // Número de columnas ajustado a 4
            tabla.setWidthPercentage(100);
            tabla.addCell(new PdfPCell(new Phrase("Codigo", FontFactory.getFont("Arial", 12))));
            tabla.addCell(new PdfPCell(new Phrase("Ci", FontFactory.getFont("Arial", 12))));
            tabla.addCell(new PdfPCell(new Phrase("Nombre", FontFactory.getFont("Arial", 12))));
            tabla.addCell(new PdfPCell(new Phrase("Apellido", FontFactory.getFont("Arial", 12))));

            for (Autor autor : autores) {
                tabla.addCell(new PdfPCell(new Phrase(String.valueOf(autor.getId()), FontFactory.getFont("Arial", 11))));
                tabla.addCell(new PdfPCell(new Phrase(autor.getCi(), FontFactory.getFont("Arial", 11))));
                tabla.addCell(new PdfPCell(new Phrase(String.valueOf(autor.getNombre()), FontFactory.getFont("Arial", 11))));
                tabla.addCell(new PdfPCell(new Phrase(String.valueOf(autor.getApellido()), FontFactory.getFont("Arial", 11))));
            }
            document.add(tabla);
            document.close();
        } catch (IOException | DocumentException e) {
            throw new IOException("No se puede generar el pdf", e);
        }
        return rutaPdf;
    }
}

