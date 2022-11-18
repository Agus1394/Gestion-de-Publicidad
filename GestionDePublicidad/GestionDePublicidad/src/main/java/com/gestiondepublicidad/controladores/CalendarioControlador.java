package com.gestiondepublicidad.controladores;

import com.gestiondepublicidad.servicios.CalendarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/calendario")
public class CalendarioControlador {
    
    @Autowired 
    private CalendarioServicio calendarioServicio;
    
    
    
}
