package com.itsqmet.formularioHC.Controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AngularControlador {
    @GetMapping("/bienvenidaUsuario")
    public String loginUser() {
        return "/Usuarios/usuarioNormal";
    }
    @GetMapping("/bienvenidaAdmin")
    public String loginAdmin() {
        return "/Usuarios/usuarioNormal";
    }
}
