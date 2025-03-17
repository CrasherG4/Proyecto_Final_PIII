package com.itsqmet.formularioHC.Controlador;

import com.itsqmet.formularioHC.Entidad.Autor;
import com.itsqmet.formularioHC.Entidad.Bibliotecario;
import com.itsqmet.formularioHC.Entidad.Libro;
import com.itsqmet.formularioHC.Entidad.Prestamo;
import com.itsqmet.formularioHC.Servicio.BibliotecarioServicio;
import com.itsqmet.formularioHC.Servicio.LibroServicio;
import com.itsqmet.formularioHC.Servicio.PrestamoServicio;
import com.itsqmet.formularioHC.Servicio.BibliotecarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class PrestamoControlador {

    @Autowired
    PrestamoServicio prestamoServicio;

    @Autowired
    BibliotecarioServicio BibliotecarioServicio;

    @Autowired
    LibroServicio libroServicio;

    @Autowired
    BibliotecarioServicio bibliotecarioServicio;

    //LEER
    @GetMapping("prestamos")
    public List<Prestamo> prestamos(){
        List<Prestamo> prestamos = prestamoServicio.listarPrestamos();
        return prestamos;
    }

    //GUARDAR
    @PostMapping("guardarPrestamo")
    public Prestamo guardar(@RequestBody Prestamo prestamo){
        //CAMBIO
        Long libroId = prestamo.getLibro().getId();
        Libro libro = libroServicio.buscarLibro(libroId)
                .orElseThrow(()-> new RuntimeException("Libro no encontrado"));
        prestamo.setLibro(libro);
        Long bibliotecarioId = prestamo.getBibliotecario().getId();
        Bibliotecario bibliotecario = bibliotecarioServicio.buscarBibliotecario(bibliotecarioId)
                .orElseThrow(()-> new RuntimeException("Bibliotecario no encontrado"));
        prestamo.setBibliotecario(bibliotecario);
        return prestamoServicio.guardarPrestamo(prestamo);
    }

    //ELIMINAR
    @DeleteMapping("/eliminarPrestamo/{id}")
    public ResponseEntity<Boolean> eliminar(@PathVariable Long id){
        prestamoServicio.eliminarPrestamo(id);
        return ResponseEntity.ok(true);
    }

    //ACTUALIZAR
    @PutMapping("/actualizarPrestamo/{id}")
    public ResponseEntity<Prestamo> actualizar(@PathVariable Long id, @RequestBody Prestamo prestamoData){
        Optional<Prestamo> prestamoOpcional = prestamoServicio.buscarPrestamo(id);
        Prestamo prestamo=prestamoOpcional.get();
        prestamo.setDescripcion(prestamoData.getDescripcion());
        prestamo.setFechaPrestamo(prestamoData.getFechaPrestamo());
        prestamo.setFechaDevolucion(prestamoData.getFechaDevolucion());
        prestamo.setBibliotecario(prestamoData.getBibliotecario());
        prestamo.setLibro(prestamoData.getLibro());

        Prestamo prestamoRegistrado = prestamoServicio.guardarPrestamo(prestamo);
        return ResponseEntity.ok(prestamoRegistrado);
    }

    @GetMapping("/prestamos/pdf")
    public ResponseEntity<byte[]> descargarPdf() throws Exception{
        String rutaPdf = prestamoServicio.generarPPdf();
        File pdfFile = new File(rutaPdf);
        if(!pdfFile.exists()){
            throw new FileNotFoundException("El archivo pdf no existe");
        }
        byte[] contenido = Files.readAllBytes(pdfFile.toPath());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "prestamos.pdf");
        return new ResponseEntity<>(contenido,headers, HttpStatus.OK);
    }
    
    /*
    //Leer
    @GetMapping("/prestamo")
    public String mostrarPrestamos(@RequestParam(name = "buscarPrestamo", required = false, defaultValue = "") String buscarPrestamo, Model model) {
        List<Prestamo> prestamos = prestamoServicio.listarPrestamos();
        model.addAttribute("prestamos", prestamos);
        model.addAttribute("buscarPrestamo", buscarPrestamo);
        return "/Prestamo/listaPrestamo";
    }

    //CREAR
    @GetMapping("/formularioPrestamo")
    public String formularioPrestamo(Model model){
        model.addAttribute("prestamo", new Prestamo());
        //model.addAttribute("Bibliotecarios", BibliotecarioServicio.listarBibliotecarios());
        model.addAttribute("bibliotecarios", bibliotecarioServicio.listarBibliotecarios());
        model.addAttribute("libros", libroServicio.listarLibros());
        return "/Prestamo/formularioPrestamo";
    }

    @PostMapping("/guardarPrestamo")
    public String crearPrestamo(Prestamo prestamo){
        prestamoServicio.guardarPrestamo(prestamo);
        return "redirect:/prestamo";
    }

    //ACTUALIZAR
    @GetMapping("/editarPrestamo/{id}")
    public String editarPrestamo(@PathVariable Long id, Model model){
        Optional<Prestamo> prestamo = prestamoServicio.buscarPrestamo(id);
        model.addAttribute("prestamo", prestamo);
        model.addAttribute("bibliotecarios", bibliotecarioServicio.listarBibliotecarios());
        model.addAttribute("libros", libroServicio.listarLibros());
        return "/Prestamo/formularioPrestamo";
    }

    //ELIMINAR
    @GetMapping("/eliminarPrestamo/{id}")
    public String eliminarPrestamo(@PathVariable Long id){
        prestamoServicio.eliminarPrestamo(id);
        return "redirect:/prestamo";
    }
    
     */
}
