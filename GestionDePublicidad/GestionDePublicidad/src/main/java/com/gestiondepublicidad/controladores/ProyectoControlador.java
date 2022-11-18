package com.gestiondepublicidad.controladores;

import com.gestiondepublicidad.entidades.Proyecto;
import com.gestiondepublicidad.entidades.Usuario;
import com.gestiondepublicidad.servicios.ProyectoServicio;
import com.gestiondepublicidad.servicios.UsuarioServicio;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("proyecto")
public class ProyectoControlador {
    
    @Autowired
    private ProyectoServicio proyectoServicio;
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @GetMapping()
    public String registrar(ModelMap modelo){
        List <Proyecto> proyecto = proyectoServicio.listarUsuarios();
        List <Usuario> usuario = usuarioServicio.listarUsuarios();
        
        modelo.addAttribute("Proyectos", proyecto);
        modelo.addAttribute("Usuarios", usuario);
        return "registrar_proyecto.html";
        
    }
    
    
    @GetMapping("/cargarproyecto")
    public String registrarProyecto(@RequestParam String idProyecto,
            @RequestParam String nombreProyecto,
            @RequestParam String descripcion,
            @RequestParam String idUsuario,
            @RequestParam(required = false) Date fechaInicio,
            @RequestParam(required = false) Date fechaFin,
            ModelMap modelo){
        
        try {
            
        } catch (Exception e) {
        }
        return null;
        
    }
    
    
    
}
