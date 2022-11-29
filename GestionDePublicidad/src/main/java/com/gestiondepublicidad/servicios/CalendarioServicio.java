package com.gestiondepublicidad.servicios;

import com.gestiondepublicidad.entidades.Calendario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gestiondepublicidad.repositorios.CalendarioRepositorio;
import org.springframework.transaction.annotation.Transactional;
import com.gestiondepublicidad.excepciones.MiException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CalendarioServicio {

    @Autowired
    private CalendarioRepositorio calendarioRepositorio;

    //CREAR
    @Transactional
    public void registrar(String id, String descripcion,
            String evento) throws MiException {

        validar(id, descripcion, evento);

        Calendario calendario = new Calendario();

        calendario.setId_calendario(id);
        calendario.setDescripcion(descripcion);
        calendario.setEvento(evento);

        calendarioRepositorio.save(calendario);
    }

    //ACTUALIZAR
    @Transactional
    public void modificarCalendario(String id, String descripcion,
            String evento) throws MiException {

        validar(id, descripcion, evento);

        Optional<Calendario> respuesta = calendarioRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Calendario calendarioModif = new Calendario();
            calendarioModif.setId_calendario(id);
            calendarioModif.setDescripcion(descripcion);
            calendarioModif.setEvento(evento);

            calendarioRepositorio.save(calendarioModif);
        }
    }

    //LISTAR-QUERYS
    public Calendario getOne(String id) {
        return calendarioRepositorio.getOne(id);
    }

    public List<Calendario> listarTodos() {
        List<Calendario> calendarios = new ArrayList<>();
        return calendarios = calendarioRepositorio.findAll();
    }

    public List<Calendario> BuscarEvento(String evento) {
        List<Calendario> calendarios = new ArrayList<>();
        return calendarios = calendarioRepositorio.buscarEvento(evento);
    }

    public List<Calendario> BuscarDescripcion(String descripcion) {
        List<Calendario> calendarios = new ArrayList<>();
        return calendarios = calendarioRepositorio.buscarDescripcion(descripcion);
    }

    //ELIMINAR
    @Transactional
    public void eliminarCalendario(String idCalendario) {
        calendarioRepositorio.deleteById(idCalendario);
    }
    
    
    //VALIDAR
    private void validar(String id, String descripcion, String evento)
            throws MiException {

        if (id.isEmpty() || id == null) {
            throw new MiException("El id del Calendario no puede estar vacío");
        }

        if (descripcion.isEmpty() || descripcion == null) {
            throw new MiException("La descripcion del calendario no puede estar vacía");
        }

        if (evento.isEmpty() || evento == null) {
            throw new MiException("El evento del calendario no puede estar vacía");
        }

    }

}
