package com.gestiondepublicidad.controladores;

import com.gestiondepublicidad.entidades.Tarea;
import com.gestiondepublicidad.entidades.Usuario;
import com.gestiondepublicidad.enumeraciones.Estado;
import com.gestiondepublicidad.excepciones.MiException;
import com.gestiondepublicidad.servicios.TareaServicio;
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
@RequestMapping("/tarea")
public class TareaControlador {

    @Autowired
    UsuarioServicio usuarioServicio;

    @Autowired
    TareaServicio tareaServicio;

    //Crear
    @GetMapping("/registro")
    public String registrar(ModelMap modelo, HttpSession session) {

        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        Usuario usuario = usuarioServicio.getOne(logueado.getId_usuario());
        modelo.put("usuario", usuario);

        return "registrar_tarea.html";
    }

    @PostMapping("/cargar")
    public String registrarTarea(@RequestParam String id,
            @RequestParam String nombreTarea, @RequestParam String descripcion,
            @RequestParam Date fechaCreacion, @RequestParam Date fechaAsignacion,
            @RequestParam String estadoTarea, @RequestParam String id_usuario,
            ModelMap modelo) {
        try {
            tareaServicio.registrar(id, nombreTarea, descripcion, fechaCreacion,
                    fechaAsignacion, estadoTarea, id_usuario);

            modelo.put("Tarea publicada", "!!");
        } catch (MiException e) {

            modelo.put("error al subir tarea", e.getMessage());
            java.util.logging.Logger.getLogger(TareaControlador.class.getName())
                    .log(Level.SEVERE, null, e);

            return "registrar_tarea.html";
        }
        return "redirect:/tarea";
    }

    //MODIFICAR
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {
        modelo.put("tarea", tareaServicio.getOne(id));
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);

        return "tarea_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@RequestParam String id,
            @RequestParam String nombre,
            @RequestParam String descripcion,
            @RequestParam String tipo,
            @RequestParam(required = false) Date fecha,
            ModelMap modelo) {
        try {
            tareaServicio.actualizar(id, nombre, descripcion, fecha, fecha, nombre, id);

            return "redirect:../listar";
        } catch (MiException ex) {
            modelo.put("Error", ex.getMessage());
            return "tarea_modificar.html";
        }

    }

    //LISTAR
    @GetMapping("/listadoTareas")
    public String listar(ModelMap modelo) {
        List<Tarea> tareas = tareaServicio.listarTodos();
        modelo.addAttribute("tareas", tareas);
        return "listadoTareas.html";
    }

    //filtrar por nombre
    @GetMapping("/listadoTareas/nombre")
    public String filtrarPorNombre(@RequestParam String nombre,
            @RequestParam Estado estadoTarea, ModelMap modelo) {
        List<Tarea> tareas = new ArrayList<Tarea>();
        if (nombre.isEmpty() || nombre == null) {
            tareas = tareaServicio.listarTodos();
        } else {
            tareas = tareaServicio.buscarPorNombre(nombre);
        }
        modelo.addAttribute("tareas", tareas);
        return "listadoTareas.html";
    }

    //filtrar por descripcion
    @GetMapping("/listadoTareas/descripcion")
    public String filtrarPorDescripcion(@RequestParam String descripcion,
            @RequestParam Estado estadoTarea,
            ModelMap modelo) throws MiException {

        List<Tarea> tareas = new ArrayList<Tarea>();
        if (descripcion.isEmpty() || descripcion == null) {
            tareas = tareaServicio.listarTodos();
        } else {
            tareas = tareaServicio.buscarPorDescripcion(descripcion);
        }
        modelo.addAttribute("tareas", tareas);
        return "listadoTareas.html";
    }

    //filtrar por fecha de cracion
    @GetMapping("/listadoTareas/fechaCreacion")
    public String ordenarFechaCreacion(@RequestParam String fechaCreacion,
            ModelMap modelo) throws MiException {

        List<Tarea> tareas = new ArrayList<Tarea>();
        if (fechaCreacion.isEmpty() || fechaCreacion == null) {
            tareas = tareaServicio.listarTodos();
        } else {
            tareas = tareaServicio.buscarPorFechaCracion(fechaCreacion);
        }
        modelo.addAttribute("tareas", tareas);
        return "listadoTarea.html";
    }

    //filtrar por fecha de cracion
    @GetMapping("/listadoTareas/fechaAsignacion")
    public String ordenarFechaAsignacion(@RequestParam String fechaAsignacion,
            ModelMap modelo) throws MiException {

        List<Tarea> tareas = new ArrayList<Tarea>();
        if (fechaAsignacion.isEmpty() || fechaAsignacion == null) {
            tareas = tareaServicio.listarTodos();
        } else {
            tareas = tareaServicio.buscarPorFechaCracion(fechaAsignacion);
        }
        modelo.addAttribute("tareas", tareas);
        return "listadoTarea.html";
    }

    @GetMapping("/listadoTareas/estado")
    public String ordenarPorTipo(@RequestParam String estadoTarea,
            ModelMap modelo) throws MiException {

        List<Tarea> tareas = new ArrayList<Tarea>();
        if (estadoTarea.isEmpty() || estadoTarea == null) {
            tareas = tareaServicio.listarTodos();
        } else {
            tareas = tareaServicio.buscarPorEstado(Estado.valueOf(estadoTarea.toUpperCase()));
        }
        modelo.addAttribute("tareas", tareas);
        return "listadoTareas.html";
    }

    //ELIMINAR
    @PreAuthorize("hasAnyRole('ROLE_TRABAJADOR')")
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo) {
        List<Tarea> tareas = tareaServicio.listarTodos();
        modelo.addAttribute("tareas", tareas);
        tareaServicio.eliminarTarea(id);
        return "listadoTareas.html";
    }

}
