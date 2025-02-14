package com.itsqmet.formularioHC.Controlador;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginControlador {

    @GetMapping("/login")
    public String login(){
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
            return  "redirect:/admin";
        }else if (role.equals("ROLE_CLIENTE")){
            return "redirect:/";
        }else if (role.equals("ROLE_EMPLEADO")){
            return "redirect:/";
        }
        return "redirect:/login?error";
    }
}
