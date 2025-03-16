package com.itsqmet.formularioHC.Controlador;


import com.itsqmet.formularioHC.Entidad.Bibliotecario;
import com.itsqmet.formularioHC.Roles.Rol;
import com.itsqmet.formularioHC.Servicio.BibliotecarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BibliotecarioControlador {

    @Autowired
    private BibliotecarioServicio bibliotecarioServicio;

    //LEER
    @GetMapping("bibliotecarios")
    public List<Bibliotecario> bibliotecarios(){
        List<Bibliotecario> bibliotecarios = bibliotecarioServicio.listarUsuarios();
        return bibliotecarios;
    }

    //GUARDAR
    @PostMapping("guardarBibliotecario")
    public Bibliotecario guardar(@RequestBody Bibliotecario bibliotecarioDTO) {
        return bibliotecarioServicio.registrar(
                bibliotecarioDTO.getId(),
                bibliotecarioDTO.getNombre(),
                bibliotecarioDTO.getUsername(),
                bibliotecarioDTO.getPassword(),
                bibliotecarioDTO.getRol()
        );
    }

    //ELIMINAR
    @DeleteMapping("/eliminarBibliotecario/{id}")
    public ResponseEntity<Boolean> eliminar(@PathVariable Long id){
        bibliotecarioServicio.eliminarBbibliotecario(id);
        return ResponseEntity.ok(true);
    }


    /*

    //ACTUALIZAR
    @PutMapping("/actualizarBibliotecario/{id}")
    public ResponseEntity<Bibliotecario> actualizar(@PathVariable Long id, @RequestBody Bibliotecario bibliotecarioData){
        Optional<Bibliotecario> bibliotecarioOpcional = bibliotecarioServicio.buscarBibliotecarioId(id);
        Bibliotecario bibliotecario=bibliotecarioOpcional.get();
        bibliotecario.setNombre(bibliotecarioData.getNombre());
        bibliotecario.setDescripcion(bibliotecarioData.getDescripcion());
        bibliotecario.setPrecio(bibliotecarioData.getPrecio());
        bibliotecario.setStock(bibliotecarioData.getStock());
        bibliotecario.setProveedor(bibliotecarioData.getProveedor());

        Bibliotecario bibliotecarioRegistrado = bibliotecarioServicio.guardarBibliotecario(bibliotecario);
        return ResponseEntity.ok(bibliotecarioRegistrado);
    }
     */




    /*
    //LEER
    @GetMapping("/bibliotecarios")
    public String mostrarBibliotecarios(@RequestParam(name = "buscarBibliotecario", required = false, defaultValue = "") String buscarBibliotecario, Model model){
        List<Bibliotecario> bibliotecarios = bibliotecarioServicio.buscarBibliotecarioNombre(buscarBibliotecario);
        model.addAttribute("buscarBibliotecario", buscarBibliotecario);
        model.addAttribute("bibliotecarios", bibliotecarios);
        return "/Bibliotecario/listaBibliotecarios";
    }

    //CREAR
    @GetMapping("/bibliotecario")
    public String formularioBibiotecario(Model model){
        model.addAttribute("bibliotecario", new Bibliotecario());
        return "/Bibliotecario/bibliotecario";
    }

    @PostMapping("/guardarBibliotecario")
    public String crearBibliotecario(@RequestParam(required = false) Long id,
                               @RequestParam String nombre,
                               @RequestParam String username,
                               @RequestParam String password,
                               @RequestParam Rol rol) throws Exception{
        bibliotecarioServicio.registrar(id,nombre,username,password,rol);
        return "redirect:/bibliotecarios";
    }

    //ACTUALIZAR
    @GetMapping("/editarBibliotecario/{id}")
    public String editarBibliotecario(@PathVariable Long id, Model model){
        Optional<Bibliotecario> bibliotecario = bibliotecarioServicio.buscarBibliotecario(id);
        model.addAttribute("bibliotecario", bibliotecario);
        return "/Bibliotecario/bibliotecario";
    }

    //ELIMINAR
    @GetMapping("/eliminarBibliotecario/{id}")
    public String eliminarBibliotecario(@PathVariable Long id){
        bibliotecarioServicio.eliminarBbibliotecario(id);
        return "redirect:/bibliotecarios";
    }

    //Guardar usuarios
    //CREAR
    @GetMapping("/nuevoUsuario")
    public String formularioUsuario(Model model){
        model.addAttribute("bibliotecario", new Bibliotecario());
        return "/Usuario/formularioUser";
    }

    @PostMapping("/guardarUser")
    public String crearUsuario(@RequestParam(required = false) Long id,
                                     @RequestParam String nombre,
                                     @RequestParam String username,
                                     @RequestParam String password) throws Exception{
        Rol rol = Rol.CLIENTE;
        bibliotecarioServicio.registrar(id,nombre,username,password,rol);
        return "redirect:/bibliotecarios";
    }
     */
}
