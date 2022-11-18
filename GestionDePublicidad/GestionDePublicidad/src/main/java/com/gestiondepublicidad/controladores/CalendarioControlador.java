package com.gestiondepublicidad.controladores;

import com.gestiondepublicidad.entidades.Calendario;
import com.gestiondepublicidad.excepciones.MiException;
import com.gestiondepublicidad.servicios.CalendarioServicio;
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
@RequestMapping("/calendario")
public class CalendarioControlador {

    @Autowired
    private CalendarioServicio calendarioServicio;

    @GetMapping("/registro_calendario")
    public String registrar() {
        return "registrar_calendario.html";
    }

    @PostMapping("/registro")
    public String registrarCalendario(@RequestParam String id,
            @RequestParam String descripcion,
            @RequestParam String evento,
            ModelMap modelo) {
        try {
            calendarioServicio.registrar(id, descripcion, evento);
            modelo.put("Calendario registrado","!");
        } catch (MiException e) {
            modelo.put("error al subir el calendario", e.getMessage());
            java.util.logging.Logger.getLogger(CalendarioControlador.class.getName()).log(Level.SEVERE, null, e);
        }
        return "registrar_calendario.html";
    }

    @GetMapping("/modificar_calendario/{id}")
    public String modificarCalendario(@PathVariable String id,
            ModelMap modelo) {
        modelo.put("Calendario", calendarioServicio.getOne(id));
        return "calendario_modif.html";
    }

    @PostMapping(value = "/eliminar_calendario{id}")
    public String eliminarProyecto(@PathVariable String id,
            ModelMap modelo) {
        modelo.remove(id);
        return "calendario_eliminado.html";
    }

    @GetMapping("/listar_calendarios")
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
