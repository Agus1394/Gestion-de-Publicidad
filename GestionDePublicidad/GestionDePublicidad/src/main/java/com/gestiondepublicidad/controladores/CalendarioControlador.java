package com.gestiondepublicidad.controladores;

import com.gestiondepublicidad.entidades.Calendario;
import com.gestiondepublicidad.entidades.Usuario;
import com.gestiondepublicidad.excepciones.MiException;
import com.gestiondepublicidad.servicios.CalendarioServicio;
import com.gestiondepublicidad.servicios.UsuarioServicio;
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
@RequestMapping("/calendario")
public class CalendarioControlador {

    @Autowired
    private CalendarioServicio calendarioServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    //CONTROLAR USUARIO SESSION TESTEAR YA CON FRONT VINCULADO
    //REGISTRO
    @GetMapping("/registrar")
    public String registrar(ModelMap modelo, HttpSession session) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        Usuario usuario = usuarioServicio.getOne(logueado.getId_usuario());
        modelo.put("usuario", usuario);
        return "registrar_calendario.html";
    }

    @PostMapping("/registro")
    public String registrarCalendario(@RequestParam String id,
            @RequestParam String descripcion,
            @RequestParam String evento, ModelMap modelo) {
        try {
            calendarioServicio.registrar(id, descripcion, evento);
            modelo.put("Calendario registrado", "!");
        } catch (MiException e) {
            modelo.put("error al subir el calendario", e.getMessage());
            java.util.logging.Logger.getLogger(CalendarioControlador.class.getName()).log(Level.SEVERE, null, e);
        }
        return "registrar_calendario.html";
    }

    //MODIFICAR
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {
        modelo.put("calendario", calendarioServicio.getOne(id));
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);
        return "calendario_modif.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String descripcion,
            String evento, ModelMap modelo) {
        try {
            calendarioServicio.modificarCalendario(id, descripcion, evento);
            return "redirect:../lista";
        } catch (MiException e) {

            modelo.put("Error", e.getMessage());
            return "calendario_modif.html";
        }
    }

    //ELIMINAR
    //CONTROLAR SI SE NECESITA ELIMINACION DE ADMIN U OTRO USER CON FRONT HECHO
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/eliminar{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo) {

        List<Calendario> calendarios = calendarioServicio.listarTodos();
        modelo.addAttribute("calendarios", calendarios);

        calendarioServicio.eliminarCalendario(id);
        return "redirect:/calendario/lista";
    }

    //LISTAR
    @GetMapping("/listar")
    public String listarCalendarios(ModelMap modelo) {
        List<Calendario> calendario = calendarioServicio.listarTodos();
        modelo.put("Calendario", calendario);
        return "lista_calendarios.html";
    }

    @GetMapping("/buscar_eventos")
    public String buscarEventos(ModelMap modelo,
            String evento) {
        List<Calendario> calendario = calendarioServicio.BuscarEvento(evento);
        modelo.addAttribute("calendario", calendario);
        return "buscar_eventos.html";
    }

    @GetMapping("/buscar_descripcion")
    public String buscarDescripcion(ModelMap modelo,
            String descripcion) {
        List<Calendario> calendario = calendarioServicio.BuscarDescripcion(descripcion);
        modelo.addAttribute("calendario", calendario);
        return "buscar_descripcion.html";
    }

}
