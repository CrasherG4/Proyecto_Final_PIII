package com.itsqmet.formularioHC.Controlador;

import com.itsqmet.formularioHC.Entidad.Bibliotecario;
import com.itsqmet.formularioHC.Entidad.Prestamo;
import com.itsqmet.formularioHC.Entidad.Usuario;
import com.itsqmet.formularioHC.Servicio.PrestamoServicio;
import com.itsqmet.formularioHC.Servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UsuarioControlador {
    @Autowired
    UsuarioServicio usuarioServicio;
    
    @Autowired
    PrestamoServicio prestamoServicio;



    /*
    //LEER
    @GetMapping("/usuarios")
    public String mostrarUsuarios(@RequestParam(name = "buscarUsuario", required = false, defaultValue = "") String buscarUsuario, Model model){
        List<Usuario> usuarios = usuarioServicio.buscarUsuarioNombre(buscarUsuario);
        model.addAttribute("buscarUsuario", buscarUsuario);
        model.addAttribute("usuarios", usuarios);
        return "/Usuario/listaUsuarios";
    }

    //CREAR
    @GetMapping("/formularioUsuario")
    public String formularioUsuario(Model model){
        model.addAttribute("usuario", new Usuario());
        return "/Usuario/formularioUsuario";
    }

    @PostMapping("/guardarUsuario")
    public String crearUsuario(Usuario usuario){
        usuarioServicio.guardarUsuario(usuario);
        return "redirect:/usuarios";
    }

    //ACTUALIZAR
    @GetMapping("/editarUsuario/{id}")
    public String editarUsuario(@PathVariable Long id, Model model){
        Optional<Usuario> usuario = usuarioServicio.buscarUsuario(id);
        model.addAttribute("usuario", usuario);
        return "/Usuario/formularioUsuario";
    }

    //ELIMINAR
    @GetMapping("/eliminarUsuario/{id}")
    public String eliminarUsuario(@PathVariable Long id){
        usuarioServicio.eliminarUsuario(id);
        return "redirect:/usuarios";
    }

    //Metodo para buscar los prestamos de un usuario
    @GetMapping("/usuario/{id}")
    public String obtenerUsuariosPorPrestamo(@PathVariable Long id, Model model) {
        Optional<Prestamo> prestamos = prestamoServicio.buscarPrestamo(id);
        Usuario usuario = usuarioServicio.buscarUsuario(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        model.addAttribute("usuario", usuario);

        model.addAttribute("prestamos", prestamos);

        return "Usuario/listaUsuarioPrestamo";
    }
     */
}
