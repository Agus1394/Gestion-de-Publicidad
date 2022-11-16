package com.gestiondepublicidad.controladores;

import com.gestiondepublicidad.servicios.ProyectoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("proyecto")
public class ProyectoControlador {
    
    @Autowired
    private ProyectoServicio proyectoServicio;
    
    
}
