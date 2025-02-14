package com.itsqmet.formularioHC.Controlador;

import com.itsqmet.formularioHC.Entidad.Prestamo;
import com.itsqmet.formularioHC.Servicio.BibliotecarioServicio;
import com.itsqmet.formularioHC.Servicio.LibroServicio;
import com.itsqmet.formularioHC.Servicio.PrestamoServicio;
import com.itsqmet.formularioHC.Servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class PrestamoControlador {

    @Autowired
    PrestamoServicio prestamoServicio;

    @Autowired
    UsuarioServicio usuarioServicio;

    @Autowired
    LibroServicio libroServicio;

    @Autowired
    BibliotecarioServicio bibliotecarioServicio;

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
        //model.addAttribute("usuarios", usuarioServicio.listarUsuarios());
        model.addAttribute("bibliotecarios", bibliotecarioServicio.listarUsuarios());
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
        model.addAttribute("bibliotecarios", bibliotecarioServicio.listarUsuarios());
        model.addAttribute("libros", libroServicio.listarLibros());
        return "/Prestamo/formularioPrestamo";
    }

    //ELIMINAR
    @GetMapping("/eliminarPrestamo/{id}")
    public String eliminarPrestamo(@PathVariable Long id){
        prestamoServicio.eliminarPrestamo(id);
        return "redirect:/prestamo";
    }
}
