package com.gestiondepublicidad.controladores;

import com.gestiondepublicidad.entidades.Proyecto;
import com.gestiondepublicidad.entidades.Usuario;
import com.gestiondepublicidad.excepciones.MiException;
import com.gestiondepublicidad.servicios.ProyectoServicio;
import com.gestiondepublicidad.servicios.UsuarioServicio;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/proyecto")
public class ProyectoControlador {

    @Autowired
    private ProyectoServicio proyectoServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/registroproyecto")
    public String registrar(ModelMap modelo) {
        List<Proyecto> proyecto = proyectoServicio.listarUsuarios();
        List<Usuario> usuario = usuarioServicio.listarUsuarios();

        modelo.addAttribute("Proyectos", proyecto);
        modelo.addAttribute("Usuarios", usuario);
        return "registrar_proyecto.html";

    }

    @PostMapping("/cargarproyecto/nuevo")
    public String registrarProyecto(@RequestParam String idProyecto,
            @RequestParam String nombreProyecto,
            @RequestParam String descripcion,
            @RequestParam String idUsuario,
            @RequestParam(required = false) Date fechaInicio,
            @RequestParam(required = false) Date fechaFin,
            ModelMap modelo) {
        try {
            proyectoServicio.registrar(idProyecto, idUsuario, descripcion, fechaInicio, fechaFin, idUsuario);
            modelo.put("Proyecto publicado", "!!");
        } catch (MiException e) {
            modelo.put("error al subir el proyecto", e.getMessage());
            java.util.logging.Logger.getLogger(ProyectoControlador.class.getName()).log(Level.SEVERE, null, e);
        }
        return "registrar_proyecto.html";
    }

    @GetMapping("/buscar_por_nombre_p")
    public String buscarProyecto(ModelMap modelo, String nombre) {
        List<Proyecto> proyecto = (List<Proyecto>) proyectoServicio.buscarPorNombre(nombre);
        modelo.addAttribute("proyecto", proyecto);
        return "buscar_por_nombre_proyecto.html";
    }

    @GetMapping("/lista_usuarios_proyectos")
    public String listarUsuarios(ModelMap modelo) {
        List<Proyecto> proyecto = proyectoServicio.listarUsuarios();
        modelo.addAttribute("proyecto", proyecto);
        return "lista_usuarios.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {
        modelo.put("Proyecto", proyectoServicio.getOne(id));
        return "proyecto_modificado.html";

    }

    @PostMapping(value = "/eliminar_proyecto/{id}")
    public String eliminarProyecto(@PathVariable String id, ModelMap modelo) {
        modelo.remove(id);
        return "proyectoeliminado.html";
    }

}
