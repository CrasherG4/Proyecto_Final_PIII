package com.itsqmet.formularioHC.Servicio;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itsqmet.formularioHC.Entidad.Libro;
import com.itsqmet.formularioHC.Repositorio.LibroRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.Font;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class LibroServicio {
    @Autowired
    LibroRepositorio libroRepositorio;

    public List<Libro> listarLibros(){

        return libroRepositorio.findAll();
    }

    public List<Libro> buscarLibroTitulo(String buscarLibro){
        if(buscarLibro==null || buscarLibro.isEmpty()){
            return libroRepositorio.findAll();
        }else{
            return libroRepositorio.findByTituloContainingIgnoreCase(buscarLibro);
        }
    }

    public Optional<Libro> buscarLibro(Long id){

        return libroRepositorio.findById(id);
    }

    public void guardarLibro(Libro libro ){

        libroRepositorio.save(libro);
    }

    public void eliminarLibro(Long id){
        libroRepositorio.deleteById(id);
    }


    public String generarPdf() throws DocumentException, IOException {
        List<Libro> libros = libroRepositorio.findAll();
        Document document = new Document();
        String rutaPdf = Paths.get("libros.pdf").toAbsolutePath().toString();
        try (FileOutputStream fos = new FileOutputStream(rutaPdf)) {
            PdfWriter.getInstance(document, fos);
            document.open();
            document.add(new Paragraph("Lista de Libros", FontFactory.getFont("Arial", 14, Font.BOLD)));
            PdfPTable tabla = new PdfPTable(4); // Número de columnas ajustado a 4
            tabla.setWidthPercentage(100);
            tabla.addCell(new PdfPCell(new Phrase("Codigo", FontFactory.getFont("Arial", 12))));
            tabla.addCell(new PdfPCell(new Phrase("Titulo", FontFactory.getFont("Arial", 12))));
            tabla.addCell(new PdfPCell(new Phrase("Genero", FontFactory.getFont("Arial", 12))));
            tabla.addCell(new PdfPCell(new Phrase("Fecha Publicacion", FontFactory.getFont("Arial", 12))));

            for (Libro libro : libros) {
                tabla.addCell(new PdfPCell(new Phrase(String.valueOf(libro.getId()), FontFactory.getFont("Arial", 11))));
                tabla.addCell(new PdfPCell(new Phrase(libro.getTitulo(), FontFactory.getFont("Arial", 11))));
                tabla.addCell(new PdfPCell(new Phrase(libro.getGenero(), FontFactory.getFont("Arial", 11))));
                tabla.addCell(new PdfPCell(new Phrase(String.valueOf(libro.getFechapublicacion()), FontFactory.getFont("Arial", 11))));
            }
            document.add(tabla);
            document.close();
        } catch (IOException | DocumentException e) {
            throw new IOException("No se puede generar el pdf", e);
        }
        return rutaPdf;
    }


    public List<Libro> listarLibrosPorIds(List<Long> ids) {
        return libroRepositorio.findAllById(ids);
    }
    //METODO PARA OBTENER PROVEEDOR CON PRODUCTOS
    @Transactional
    public Libro obtenerLibroConPrestamo(Long id) {
        Libro libro = libroRepositorio.findById(id).orElseThrow();
        return libro;
    }

}
