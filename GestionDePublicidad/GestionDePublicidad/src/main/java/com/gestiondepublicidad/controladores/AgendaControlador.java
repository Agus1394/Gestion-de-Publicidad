package com.gestiondepublicidad.controladores;

import com.gestiondepublicidad.entidades.Agenda;
import com.gestiondepublicidad.enumeraciones.PuestoEmpresa;
import com.gestiondepublicidad.servicios.AgendaServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/agenda")
public class AgendaControlador {

    @Autowired
    private AgendaServicio agendaServicio;

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/filtro/agenda/nombreTrabajador")
    public String traerNombreUser(@PathVariable String nombre, ModelMap modelo) {
        List<Agenda> nombresUser = agendaServicio.traerNombreUser(nombre);
        modelo.addAttribute("nombresUser", nombresUser);
        return null;
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/filtro/agenda/emailTrabajador")
    public String traerUserPorEmail(@PathVariable String email,
            ModelMap modelo) {
        Agenda buscarEmails = agendaServicio.traerUserPorEmail(email);
        modelo.addAttribute("buscarEmails", buscarEmails);
        return null;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/filtro/agenda/eliminar/{id}")
    public String eliminarContactos(@PathVariable String idAgenda,
            ModelMap modelo) {
        agendaServicio.eliminar(idAgenda);
        modelo.put("Contacto eliminado", "!");
        return null;
    }

//    @PreAuthorize("hasAnyRole('ROLE_USER')")
//    @GetMapping("/filtro/agenda/puestoEmpresa")
//    public String buscarPorPuestoEnEmpresa(@PathVariable String puestoEmpresa,
//            ModelMap modelo){
//        Agenda buscarPorPuestos = agendaServicio.traerUserPorPuestoEmp();
        
        
        
    }

