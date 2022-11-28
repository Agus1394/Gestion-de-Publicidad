package com.gestiondepublicidad.servicios;

import com.gestiondepublicidad.entidades.Agenda;
import com.gestiondepublicidad.entidades.Usuario;
import com.gestiondepublicidad.excepciones.MiException;
import com.gestiondepublicidad.repositorios.AgendaRepositorio;
import com.gestiondepublicidad.repositorios.UsuarioRepositorio;
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
            Long numeroCliente,
            Long numeroInterno,
            String id_usuario) throws MiException {

        validarCambios(id_agenda, numeroCliente, numeroInterno);

        Usuario usuario = usuarioRepositorio.findById(id_usuario).get();
        Agenda registrar = new Agenda();

        registrar.setId_agenda(id_agenda);
        registrar.setNumeroCliente(numeroCliente);
        registrar.setNumeroInterno(numeroInterno);

        agendaRepositorio.save(registrar);
    }

    @Transactional
    public void actualizarAgenda(String id_agenda,
            Long numeroCliente,
            Long numeroInterno,
            String id_usuario,
            Agenda agenda) throws MiException {

        Optional<Agenda> actualizar = agendaRepositorio.findById(id_agenda);
        Optional<Usuario> actualizarAgendaUsuario = usuarioRepositorio.findById(id_usuario);
        Usuario usuario = new Usuario();

        validarCambios(id_agenda, numeroCliente, numeroInterno);

        if (actualizarAgendaUsuario.isPresent()) {
            usuario = actualizarAgendaUsuario.get();
        } else if (actualizar.isPresent()) {
            Agenda actualizarAgenda = actualizar.get();

            actualizarAgenda.setId_agenda(id_agenda);
            actualizarAgenda.setNumeroCliente(numeroCliente);
            actualizarAgenda.setNumeroInterno(numeroInterno);

            agendaRepositorio.save(actualizarAgenda);
        }

    }

    public void eliminarAgenda(String id_agenda) {
        agendaRepositorio.deleteById(id_agenda);
    }

    private void validarCambios(String id_agenda,
            Long numeroCliente,
            Long numeroInterno) throws MiException {

        if (id_agenda.isEmpty() || id_agenda == null) {
            throw new MiException("Campo obliagatorio!");
        }
        if (numeroCliente == null) {
            throw new MiException("Campo obliagatorio!");
        }
        if (numeroInterno == null) {
            throw new MiException("Campo obliagatorio!");
        }
    }

//    public List<Agenda> traerNumeroCliente(Long numero_ciente) {
//        List <Agenda> agenda = agendaRepositorio.buscarNumeroCliente(numero_ciente);
//        return agenda;
//    }
//    
//    public List<Agenda> traerNumeroInterno(Long numero_interno){
//        List <Agenda> agenda = agendaRepositorio.buscarNumeroInterno(numero_interno);       
//        return agenda;
//    }

}
