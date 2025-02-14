package com.itsqmet.formularioHC.Controlador;

import com.itsqmet.formularioHC.Entidad.Libro;
import com.itsqmet.formularioHC.Entidad.Prestamo;
import com.itsqmet.formularioHC.Servicio.AutorServicio;
import com.itsqmet.formularioHC.Servicio.LibroServicio;
import com.itsqmet.formularioHC.Servicio.PrestamoServicio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller

public class LibroControlador {

    @Autowired
    LibroServicio libroServicio;

    @Autowired
    AutorServicio autorServicio;

    @Autowired
    PrestamoServicio prestamoServicio;

    //LEER
    @GetMapping("/libros")
    public String mostrarLibros(@RequestParam(name = "buscarLibro", required = false, defaultValue = "") String buscarLibro, Model model){
        List<Libro> libros = libroServicio.buscarLibroTitulo(buscarLibro);
        model.addAttribute("buscarLibro", buscarLibro);
        model.addAttribute("libros", libros);
        return "/Libro/listaLibros";
    }

    //CREAR
    @GetMapping("/formularioLibro")
    public String formularioLibro(Model model){
        model.addAttribute("libro", new Libro());
        model.addAttribute("autores", autorServicio.listarAutores());
        return "/Libro/formularioLibros";
    }

    @PostMapping("/guardarLibro")
    public String crearLibro(Libro libro){
        libroServicio.guardarLibro(libro);
        return "redirect:/libros";
    }

    //ACTUALIZAR
    @GetMapping("/editarLibro/{id}")
    public String editarLibro(@PathVariable Long id, Model model){
        Optional<Libro> libro = libroServicio.buscarLibro(id);
        model.addAttribute("libro", libro);
        model.addAttribute("autores", autorServicio.listarAutores());
        return "/Libro/formularioLibros";
    }

    //ELIMINAR
    @GetMapping("/eliminarLibro/{id}")
    public String eliminarLibro(@PathVariable Long id){
        libroServicio.eliminarLibro(id);
        return "redirect:/libros";
    }


    @GetMapping("/libros/pdf")
    public ResponseEntity<byte[]> descargarPdf() throws Exception{
        String rutaPdf = libroServicio.generarPdf();
        File pdfFile = new File(rutaPdf);
        if(!pdfFile.exists()){
            throw new FileNotFoundException("El archivo pdf no existe");
        }
        byte[] contenido = Files.readAllBytes(pdfFile.toPath());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "libros.pdf");
        return new ResponseEntity<>(contenido,headers, HttpStatus.OK);
    }

    //METODO PARA BUSCAR LOS LIBROS DE UN AUTOR
    @GetMapping("/libro/{id}")
    public String obtenerLibrosPorPrestamo(@PathVariable Long id, Model model) {
        Optional<Prestamo> prestamos = prestamoServicio.buscarPrestamo(id);
        Libro libro = libroServicio.buscarLibro(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        model.addAttribute("libro", libro);

        model.addAttribute("prestamos", prestamos);

        return "Libro/listaLibroPrestamo";
    }

}
