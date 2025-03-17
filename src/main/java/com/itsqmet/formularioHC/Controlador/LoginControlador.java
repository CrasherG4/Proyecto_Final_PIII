package com.itsqmet.formularioHC.Controlador;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginControlador {

    @GetMapping("/login")
    public String login() {
        return "/Bibliotecario/login";
    }

    @GetMapping("/postLogin")
    public String dirigirPorRol(Authentication authentication){
        User bibliotecario= (User) authentication.getPrincipal();
        String role = bibliotecario.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .findFirst()
                .orElse("");
        if (role.equals("ROLE_ADMIN")){
            return  "Usuarios/usuarioAdmin";
        }else if (role.equals("ROLE_CLIENTE")){
            return "Usuarios/usuarioNormal";
        }else if (role.equals("ROLE_EMPLEADO")){
            return "redirect:/";
        }
        return "redirect:/login?error";
    }

    /*
    @PostMapping("/postLogin")
    @ResponseBody
    public Map<String, String> dirigirPorRol(Authentication authentication) {
        User bibliotecario = (User) authentication.getPrincipal();
        String role = bibliotecario.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .findFirst()
                .orElse("");
        Map<String, String> response = new HashMap<>();
        if (role.equals("ROLE_ADMIN")) {
            response.put("redirectTo", "/admin");
        } else if (role.equals("ROLE_CLIENTE") || role.equals("ROLE_EMPLEADO")) {
            response.put("redirectTo", "/");
        } else {
            response.put("redirectTo", "/login?error");
        }
        return response;
    }

     */


    /*


     */
}
