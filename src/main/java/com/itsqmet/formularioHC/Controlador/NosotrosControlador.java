package com.itsqmet.formularioHC.Controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NosotrosControlador {

    @GetMapping("/nosotros")
    public String home(){
        return "pages/Nosotros";
    }
}
