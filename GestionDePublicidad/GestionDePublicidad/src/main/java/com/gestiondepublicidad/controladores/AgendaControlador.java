package com.gestiondepublicidad.controladores;

import com.gestiondepublicidad.entidades.Agenda;
import com.gestiondepublicidad.excepciones.MiException;
import com.gestiondepublicidad.servicios.AgendaServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/agenda")
public class AgendaControlador {

    @Autowired
    private AgendaServicio agendaServicio;

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping("/filtro/agenda/contactosInternos")
    public String traerContactosInternos(@PathVariable Long contactoInterno,
            ModelMap modelo) {
        List<Agenda> contactosInternos = agendaServicio.traerNumeroInterno(contactoInterno);
        modelo.addAttribute("contactosInternos", contactosInternos);
        return null;
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping("/filtro/agenda/numeroCliente")
    public String traerContactosDeClientes(@PathVariable Long contactoCliente,
            ModelMap modelo) {
        List<Agenda> contactosClientes = agendaServicio.traerNumeroCliente(contactoCliente);
        modelo.addAttribute("contactosClientes", contactosClientes);
        return null;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')") //cambiar 
    @PostMapping("/filtro/agenda/eliminarContactos/{id}")
    public String eliminarContactos(@PathVariable String idAgenda,
            ModelMap modelo) {
        agendaServicio.eliminarContacto(idAgenda);
        return null;
    }

//    @PreAuthorize("hasAnyRole('ROLE_USER')")
//    @PostMapping("/filtro/agenda/actuall")
//    public String actualizarAgenda(@PathVariable String id,
//            ModelMap modelo) throws MiException{
//
//        
//    }

}
