package com.gestiondepublicidad.controladores;

import com.gestiondepublicidad.entidades.Proyecto;
import com.gestiondepublicidad.entidades.Usuario;
import com.gestiondepublicidad.excepciones.MiException;
import com.gestiondepublicidad.servicios.ProyectoServicio;
import com.gestiondepublicidad.servicios.UsuarioServicio;

import java.util.ArrayList;
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
            proyectoServicio.actualizar(idProyecto, nombreProyecto,descripcion, fechaFin,
                    fechaInicio, idUsuario);

            return "redirect:../listar";
        } catch (MiException ex) {
            modelo.put("Error", ex.getMessage());
            return "proyecto_modificar.html";
        }

    }
    //agregar usuario al proyecto
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/modificar/{id_proyecto}/agregarEliminarUsuario")
    public String agregarEliminarUsuarioProyecto(@PathVariable("id_proyecto") String id_proyecto, ModelMap modelo){
        modelo.addAttribute("usuarios", usuarioServicio.listarUsuarios());
        modelo.put("proyecto", proyectoServicio.getOne(id_proyecto));
        return "agregarEliminarUsuariosProyecto.html";//agregar botones de agregar y eliminar usuario a la vista particular del proyecto
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("{id_proyecto}/agregarUsuario/{id_usuario}")
    public String agregarUsuarioProyecto(@PathVariable("id_proyecto") String id_proyecto, @PathVariable("id_usuario") String id_usuario, ModelMap modelo){
        proyectoServicio.agregarUsuarioProyecto(id_proyecto, id_usuario);

        modelo.addAttribute("proyectos", proyectoServicio.listarTodos());
        return "tablaProyectos.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("{id_proyecto}/eliminarUsuario/{id_usuario}")
    public String eliminarUsuarioProyecto(@PathVariable("id_proyecto") String id_proyecto, @PathVariable("id_usuario") String id_usuario, ModelMap modelo){
        proyectoServicio.eliminarUsuarioProyecto(id_proyecto, id_usuario);

        modelo.addAttribute("proyectos", proyectoServicio.listarTodos());
        return "tablaProyectos.html";
    }

    //ELIMINAR
    // VER LOS ROLES REQUERIDOS PARA ELIMINAR
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo) {
        List<Proyecto> proyectos = proyectoServicio.listarTodos();
        modelo.addAttribute("proyectos", proyectos);
        proyectoServicio.eliminar(id);
        return "tablaProyectos.html";
    }

    //filtrar por nombre
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/tablaProyectos/nombre")
    public String filtrarPorNombre(@RequestParam String nombre, ModelMap modelo) {
        List<Proyecto> proyectos = new ArrayList<Proyecto>();
        System.out.println(nombre);
        if (nombre.isEmpty() || nombre == null){
            proyectos = proyectoServicio.listarTodos();
        }else{
            proyectos = proyectoServicio.buscarPorNombre(nombre.toUpperCase());
        }
        modelo.addAttribute("proyectos", proyectos);
        return "tablaProyectos.html ";
    }

    //filtrar por estado del proyecto
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/tablaProyectos/estado")
    public String filtrarPorEstadoProyecto(@RequestParam String estado, ModelMap modelo) throws MiException {

        List<Proyecto> proyectos = new ArrayList<Proyecto>();

        if (estado.isEmpty() || estado == null){
            proyectos = proyectoServicio.listarTodos();
        }else{
            proyectos = proyectoServicio.filtrarProyectoPorEstado(estado);
        }
        modelo.addAttribute("proyectos", proyectos);
        return "tablaProyectos.html";
    }
    
    //Listar TODOS
    @GetMapping("/tablaProyectos")
    public String listarProyectos(ModelMap modelo) {
        List<Proyecto> proyectos = proyectoServicio.listarTodos();
        modelo.addAttribute("proyectos", proyectos);
        return "tablaProyectos.html";
    }

    //Filtrar Por Usuario
    @PreAuthorize("hasAnyRole('ROLE_USER, ROLE_CLIENTE')")
    @GetMapping("/usuario")
    public String listarProyectosPorUsuario(ModelMap modelo, HttpSession httpSession){
        Usuario logueado = (Usuario) httpSession.getAttribute("usuariosession");
        Usuario usuario = usuarioServicio.getOne(logueado.getId_usuario());

        proyectoServicio.buscarPorUsuario(usuario);
        modelo.addAttribute("proyectos", proyectoServicio.buscarPorUsuario(usuario));

        return "tablaProyectos.html";
    }

    @GetMapping("/tablaProyectos/fechaInicio")
    public String ordenarProyectosPorFechaInicio(@RequestParam String fechaInicio, ModelMap modelo){
        List<Proyecto> proyectos = new ArrayList<Proyecto>();

        if (fechaInicio.isEmpty() || fechaInicio == null){
            proyectos = proyectoServicio.listarTodos();
        }else{
            proyectos = proyectoServicio.ordenarProyectosPorFechaInicio(fechaInicio);
        }

        modelo.addAttribute("proyectos", proyectos);
        return "tablaProyectos.html";
    }
    @GetMapping("/tablaProyectos/fechaFin")
    public String ordenarProyectosPorFechaFin(@RequestParam String fechaFin, ModelMap modelo){
        List<Proyecto> proyectos = new ArrayList<Proyecto>();

        if (fechaFin.isEmpty() || fechaFin == null){
            proyectos = proyectoServicio.listarTodos();
        }else{
            proyectos = proyectoServicio.ordenarProyectosPorFechaFin(fechaFin);
        }

        modelo.addAttribute("proyectos", proyectos);
        return "tablaProyectos.html";
    }
}
