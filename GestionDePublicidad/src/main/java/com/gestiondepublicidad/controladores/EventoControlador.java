package com.gestiondepublicidad.controladores;

import com.gestiondepublicidad.entidades.Evento;
import com.gestiondepublicidad.entidades.Usuario;
import com.gestiondepublicidad.excepciones.MiException;
import com.gestiondepublicidad.servicios.EventoServicio;
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
@RequestMapping("/evento")

public class EventoControlador {

    @Autowired
    UsuarioServicio usuarioServicio;

    @Autowired
    EventoServicio eventoServicio;

    //Crear
    @GetMapping("/registro")
    public String registrar(ModelMap modelo, HttpSession session) {

        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        Usuario usuario = usuarioServicio.getOne(logueado.getId_usuario());
        modelo.put("usuario", usuario);

        return "registrar_evento.html";
    }

    @PostMapping("/cargar")
    public String registrarEvento(@RequestParam String id,
            @RequestParam String nombre,
            @RequestParam String descripcion,
            @RequestParam String tipo,
            @RequestParam(required = false) Date fecha,
            ModelMap modelo) {
        try {
            eventoServicio.registrar(id, nombre, descripcion, fecha, tipo);

            modelo.put("Evento publicado", "!!");
        } catch (MiException e) {

            modelo.put("error al subir el evento", e.getMessage());
            java.util.logging.Logger.getLogger(EventoControlador.class.getName())
                    .log(Level.SEVERE, null, e);

            return "registrar_evento.html";
        }
        return "redirect:/trabajador/dashboard";
    }

    //MODIFICAR
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {
        modelo.put("evento", eventoServicio.getOne(id));
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);

        return "evento_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@RequestParam String id,
            @RequestParam String nombre,
            @RequestParam String descripcion,
            @RequestParam String tipo,
            @RequestParam(required = false) Date fecha,
            ModelMap modelo) {
        try {
            eventoServicio.actualizar(id, nombre, descripcion, fecha, tipo);

            return "redirect:../listar";
        } catch (MiException ex) {
            modelo.put("Error", ex.getMessage());
            return "evento_modificar.html";
        }

    }

    //LISTAR
    @PreAuthorize("hasAnyRole('ROLE_TRABAJADOR')")
    @GetMapping("/tablaEventos")
    public String listar(ModelMap modelo) {
        List<Evento> eventos = eventoServicio.listarTodos();
        modelo.addAttribute("eventos", eventos);
        return "tablaEventos.html";
    }

    //filtrar por nombre
    @PreAuthorize("hasAnyRole('ROLE_TRABAJADOR')")
    @GetMapping("/tablaEventos/nombre")
    public String filtrarPorNombre(@RequestParam String nombre,
            @RequestParam String tipoEvento, ModelMap modelo) {
        List<Evento> eventos = new ArrayList<Evento>();
        if (nombre.isEmpty() || nombre == null || tipoEvento.isEmpty() || tipoEvento == null) {
            eventos = eventoServicio.listarTodos();
        } else {
            eventos = eventoServicio.buscarPorNombre(nombre, tipoEvento);
        }
        modelo.addAttribute("eventos", eventos);
        return "tablaEventos.html";
    }

    //filtrar por estado del proyecto
    @PreAuthorize("hasAnyRole('ROLE_TRABAJADOR')")
    @GetMapping("/tablaEventos/descripcion")
    public String filtrarPorDescripcion(@RequestParam String descripcion,
            @RequestParam String tipoEvento,
            ModelMap modelo) throws MiException {

        List<Evento> eventos = new ArrayList<Evento>();
        if (descripcion.isEmpty() || descripcion == null) {
            eventos = eventoServicio.listarTodos();
        } else {
            eventos = eventoServicio.buscarPorDescripcion(descripcion, tipoEvento);
        }
        modelo.addAttribute("eventos", eventos);
        return "tablaEventos.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_TRABAJADOR')")
    @GetMapping("/tablaEventos/fecha")
    public String ordenarEventosPorFecha(@RequestParam String tipoEvento,
            @RequestParam String fecha,
            ModelMap modelo) throws MiException {

        List<Evento> eventos = new ArrayList<Evento>();
        if (fecha.toString().isEmpty() || fecha == null) {
            eventos = eventoServicio.listarTodos();
        } else {
            eventos = eventoServicio.buscarPorFecha(fecha, tipoEvento);
        }
        modelo.addAttribute("eventos", eventos);
        return "tablaEventos.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_TRABAJADOR')")
    @GetMapping("/tablaEventos/tipoEvento")
    public String ordenarPorTipo(@RequestParam String tipoEvento,
            ModelMap modelo) throws MiException {

        List<Evento> eventos = new ArrayList<Evento>();
        if (tipoEvento.toString().isEmpty() || tipoEvento == null) {
            eventos = eventoServicio.listarTodos();
        } else {
            eventos = eventoServicio.buscarPorTipoEvento(tipoEvento);
        }
        modelo.addAttribute("eventos", eventos);
        return "tablaEventos.html";
    }

    //ELIMINAR
    @PreAuthorize("hasAnyRole('ROLE_TRABAJADOR')")
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo) {
        List<Evento> eventos = eventoServicio.listarTodos();
        modelo.addAttribute("eventos", eventos);
        eventoServicio.eliminarEvento(id);
        return "tablaEventos.html";
    }
}
