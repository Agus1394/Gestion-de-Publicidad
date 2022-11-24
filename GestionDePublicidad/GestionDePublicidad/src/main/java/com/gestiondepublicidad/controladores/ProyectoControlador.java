package com.gestiondepublicidad.controladores;

import com.gestiondepublicidad.entidades.Proyecto;
import com.gestiondepublicidad.entidades.Usuario;
import com.gestiondepublicidad.excepciones.MiException;
import com.gestiondepublicidad.servicios.ProyectoServicio;
import com.gestiondepublicidad.servicios.UsuarioServicio;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    //Crear
    @GetMapping("/registro")
    public String registrar(ModelMap modelo, HttpSession session) {

        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        Usuario usuario = usuarioServicio.getOne(logueado.getId_usuario());
        modelo.put("usuario", usuario);

        return "registrar_proyecto.html";
    }

    @PostMapping("/cargar")
    public String registrarProyecto(@RequestParam String idProyecto,
            @RequestParam String nombreProyecto,
            @RequestParam String descripcion,
            @RequestParam String idUsuario,
            @RequestParam(required = false) Date fechaInicio,
            @RequestParam(required = false) Date fechaFin,
            ModelMap modelo) {
        try {
            proyectoServicio.registrar(idProyecto, idUsuario,
                    descripcion, fechaInicio, fechaFin, idUsuario);

            modelo.put("Proyecto publicado", "!!");
        } catch (MiException e) {

            modelo.put("error al subir el proyecto", e.getMessage());
            java.util.logging.Logger.getLogger(ProyectoControlador.class.getName()).log(Level.SEVERE, null, e);

            return "registrar_proyecto.html";
        }
        return "proyecto_cargado.html";
    }

    //LISTAR
    @GetMapping("/listar")
    public String listarUsuarios(ModelMap modelo) {
        List<Proyecto> proyectos = proyectoServicio.listarTodos();
        modelo.addAttribute("proyecto", proyectos);
        return "listar.html";
    }

    @GetMapping("/buscar_por_nombre_p")
    public String buscarPorNombre(ModelMap modelo, String nombre) {
        Proyecto proyectos = proyectoServicio.buscarPorNombre(nombre);
        modelo.addAttribute("proyecto", proyectos);
        return "buscar_por_nombre_proyecto.html";
    }

//    @GetMapping("/lista_usuarios_p")
//    public String listarUsuarios(ModelMap modelo, String nombre) {
//        List<Proyecto> proyectos = proyectoServicio.buscarPorUsuario(nombre);
//        modelo.addAttribute("proyecto", proyectos);
//        return "lista_usuarios.html";
//    }
   
    
    //MODIFICAR
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {
        modelo.put("proyecto", proyectoServicio.getOne(id));
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);

        return "proyecto_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@RequestParam String idProyecto,
            @RequestParam String nombreProyecto,
            @RequestParam String descripcion,
            @RequestParam String idUsuario,
            @RequestParam(required = false) Date fechaInicio,
            @RequestParam(required = false) Date fechaFin,
            ModelMap modelo) {
        try {
            proyectoServicio.actualizar(idProyecto, idUsuario,
                    descripcion, fechaInicio, fechaFin, idUsuario);

            return "redirect:../listar";
        } catch (MiException ex) {
            modelo.put("Error", ex.getMessage());
            return "proyecto_modificar.html";
        }

    }

    //ELIMINAR
    // VER LOS ROLES REQUERIDOS PARA ELIMINAR
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo) {
        List<Proyecto> proyectos = proyectoServicio.listarTodos();
        modelo.addAttribute("proyectos", proyectos);
        proyectoServicio.eliminar(id);
        return "tablaProyecto.html  ";
    }

    //filtrar por nombre
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/filtro/nombre")
    public String filtrarPorNombre(@PathVariable String nombre, ModelMap modelo) {
        List<Proyecto> proyectos = proyectoServicio.listarTodos();
        modelo.addAttribute("proyectos", proyectos);
        return "tablaProyecto.html ";
    }

    //filtrar por estado del proyecto
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/filtro/estado")
    public String filtrarPorEstadoProyecto(@PathVariable String estado, ModelMap modelo) {
        List<Proyecto> proyectos = proyectoServicio.listarTodos();
        modelo.addAttribute("proyectos", proyectos);
        return "tablaProyecto.html";
    }
    
    
    @GetMapping("/lista_proyectos")
    public String listarProyectos(ModelMap modelo) {
        List<Proyecto> proyectos = proyectoServicio.listarTodos();
        modelo.addAttribute("proyectos", proyectos);
        return "tablaProyecto.html";
    }
}
