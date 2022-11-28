package com.gestiondepublicidad.servicios;

import com.gestiondepublicidad.entidades.Agenda;
import com.gestiondepublicidad.entidades.Usuario;
import com.gestiondepublicidad.excepciones.MiException;
import com.gestiondepublicidad.repositorios.AgendaRepositorio;
import com.gestiondepublicidad.repositorios.UsuarioRepositorio;
import com.gestiondepublicidad.enumeraciones.PuestoEmpresa;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AgendaServicio {

    @Autowired
    private AgendaRepositorio agendaRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public Agenda getOne(String id) {
        return agendaRepositorio.getOne(id);
    }

    @Transactional
    public void registrarAgenda(Agenda agenda,
            String id_agenda,
            String nombre,
            String email,
            String puestoEnLaEmpresa) throws MiException {
        
        Agenda registrar = new Agenda();

        registrar.setId_agenda(id_agenda);
        registrar.setNombre(nombre);
        registrar.setEmail(email);
        registrar.setPuestoEnLaEmpresa(puestoEnLaEmpresa);
               
        agendaRepositorio.save(registrar);
    }

    @Transactional
    public void actualizarAgenda(String id_agenda,
            String nombre,
            String email,
            String puestoEnLaEmpresa,
            String id_usuario,
            Agenda agenda) throws MiException {

        Optional<Agenda> actualizar = agendaRepositorio.findById(id_agenda);
        Optional<Usuario> actualizarAgendaUsuario = usuarioRepositorio.findById(id_usuario);
        Usuario usuario = new Usuario();

        if (actualizarAgendaUsuario.isPresent()) {
            usuario = actualizarAgendaUsuario.get();
        } else if (actualizar.isPresent()) {
            Agenda actualizarAgenda = actualizar.get();

            actualizarAgenda.setId_agenda(id_agenda);
            actualizarAgenda.setNombre(nombre);
            actualizarAgenda.setEmail(email);
            actualizarAgenda.setPuestoEnLaEmpresa(puestoEnLaEmpresa);

            agendaRepositorio.save(actualizarAgenda);
        }
    }

    @Transactional
    public void eliminar(String id_agenda) {
        agendaRepositorio.deleteById(id_agenda);
    }

    private void validar(String id_agenda,
            String nombre,
            String email) throws MiException {

        if (id_agenda.isEmpty() || id_agenda == null) {
            throw new MiException("Campo a rellenar!");
        } 
        if (nombre.isEmpty() || nombre == null) {
             throw new MiException("Campo a rellenar!");
        }
        if (email.isEmpty() || email == null) {
             throw new MiException("Campo a rellenar!");
        }
        if (email.contains("@") || email.contains(".com")) {
             throw new MiException("El email ingresado es inválido");
        }
    }

    public List<Agenda> traerNombreUser(String nombre) {
        return agendaRepositorio.buscarPorNombre(nombre);  
    }

    public Agenda traerUserPorEmail(String email){
        return agendaRepositorio.buscarPorEmail(email);
    }
//    
//    public Agenda traerUserPorPuestoEmp(String puestoEmpresa, PuestoEmpresa puestoEmp){
//        return agendaRepositorio.buscarUserPorPuesto(puestoEmpresa);      
//    }
    
    public List<Agenda> listarContactosTrabajadores(){
        List<Agenda> agenda = new ArrayList();
        return agenda = agendaRepositorio.findAll();
    }
}
