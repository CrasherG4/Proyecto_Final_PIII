package com.itsqmet.formularioHC.Controlador;

import com.itsqmet.formularioHC.Entidad.Autor;
import com.itsqmet.formularioHC.Entidad.Libro;
import com.itsqmet.formularioHC.Servicio.AutorServicio;
import com.itsqmet.formularioHC.Servicio.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AutorControlador {

    @Autowired
    AutorServicio autorServicio;

    @Autowired
    LibroServicio libroServicio;

    //LEER
    @GetMapping("autores")
    public List<Autor> autores(){
        List<Autor> autores = autorServicio.listarAutores();
        return autores;
    }

    //GUARDAR
    @PostMapping("guardarAutor")
    public Autor guardar(@RequestBody Autor autor){
        return autorServicio.guardarAutor(autor);
    }

    //ELIMINAR
    @DeleteMapping("/eliminarAutor/{id}")
    public ResponseEntity<Boolean> eliminar(@PathVariable Long id){
        autorServicio.eliminarAutor(id);
        return ResponseEntity.ok(true);
    }

    //ACTUALIZAR
    @PutMapping("/actualizarAutor/{id}")
    public ResponseEntity<Autor> actualizar(@PathVariable Long id, @RequestBody Autor autorData){
        Optional<Autor> autorOpcional = autorServicio.buscarAutor(id);
        Autor autor=autorOpcional.get();
        autor.setNombre(autorData.getNombre());
        autor.setApellido(autorData.getApellido());
        autor.setCi(autorData.getCi());

        Autor autorRegistrado = autorServicio.guardarAutor(autor);
        return ResponseEntity.ok(autorRegistrado);
    }
    
    /*
    //LEER
    @GetMapping("/autores")
    public String mostrarAutores(@RequestParam(name = "buscarAutores", required = false, defaultValue = "") String buscarAutores, Model model){
        List<Autor> autores = autorServicio.buscarAutorNombre(buscarAutores);
        model.addAttribute("buscarAutor", buscarAutores);
        model.addAttribute("autores", autores);
        return "/Autor/listaAutores";
    }

    //CREAR
    @GetMapping("/formularioAutor")
    public String formularioAutor(Model model){
        model.addAttribute("autor", new Autor());
        return "/Autor/formularioAutor";
    }

    @PostMapping("/guardarAutor")
    public String crearAutor(Autor autor){
        autorServicio.guardarAutor(autor);
        return "redirect:/autores";
    }

    //ACTUALIZAR
    @GetMapping("/editarAutor/{id}")
    public String editarAutor(@PathVariable Long id, Model model){
        Optional<Autor> autor = autorServicio.buscarAutor(id);
        model.addAttribute("autor", autor);
        return "/Autor/formularioAutor";
    }

    //ELIMINAR
    @GetMapping("/eliminarAutor/{id}")
    public String eliminarAutor(@PathVariable Long id){
        autorServicio.eliminarAutor(id);
        return "redirect:/autores";
    }

    //METODO PARA BUSCAR LOS PRODUCTOS DE UN PROVEEDOR
    @GetMapping("/autor/{id}")
    public String obtenerAutoresPorLibro(@PathVariable Long id, Model model) {
        Optional<Libro> libros = libroServicio.buscarLibro(id);
        Autor autor = autorServicio.buscarAutor(id)
                .orElseThrow(() -> new RuntimeException("Autor no encontrado"));
        model.addAttribute("autor", autor);

        model.addAttribute("libros", libros);

        return "Libro/listaAutorLibros";
    }
    
     */

}