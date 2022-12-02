package com.gestiondepublicidad.servicios;

import com.gestiondepublicidad.entidades.Evento;
import com.gestiondepublicidad.enumeraciones.TipoEvento;
import com.gestiondepublicidad.excepciones.MiException;
import com.gestiondepublicidad.repositorios.EventoRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventoServicio {

    @Autowired
    EventoRepositorio eventoRepositorio;

    //REGISTRA UN NUEVO EVENTO
    @Transactional
    public void registrar(String id, String nombre, String descripcion,
            Date fecha, String tipo) throws MiException {

        validar(id, nombre, descripcion, fecha, tipo);
        Evento evento = eventoRepositorio.findById(id).get();

        evento.setId(id);
        evento.setNombre(nombre);
        evento.setDescripcion(descripcion);
        evento.setFecha(fecha);
        evento.setTipo(TipoEvento.valueOf(tipo.toUpperCase()));

        eventoRepositorio.save(evento);
    }

    //LISTAR EVENTOS
    //LISTAR TODOS LOS EVENTOS
    public List<Evento> listarTodos() {

        List<Evento> eventos = new ArrayList<>();

        return eventos = eventoRepositorio.findAll();
    }

    //LISTAR POR ID
    public Evento getOne(String id) {
        return eventoRepositorio.getOne(id);
    }

    //LISTAR POR NOMBRE
    public List<Evento> buscarPorNombre(String nombre, String tipo) {
        return eventoRepositorio.buscarPorNombre(nombre, tipo);
    }

    //LISTAR POR DESCRIPCION
    public List<Evento> buscarPorDescripcion(String descripcion, String tipo) {
        return eventoRepositorio.buscarPorDescripcion(descripcion, tipo);
    }

    //LISTAR POR FECHA
    public List<Evento> buscarPorFecha(String fecha, String tipo) {
        return eventoRepositorio.buscarPorFecha(fecha, tipo);
    }

    //LISTAR POR TIPO DE EVENTO
    public List<Evento> buscarPorTipoEvento(String tipo) {
        return eventoRepositorio.buscarPorTipoEvento(tipo);
    }

    //MODIFICAR
    @Transactional
    public void actualizar(String id, String nombre, String descripcion,
            Date fecha, String tipo) throws MiException {

        validar(id, nombre, descripcion, fecha, tipo);
        Optional<Evento> respuesta = eventoRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Evento evento = respuesta.get();

            evento.setNombre(nombre);
            evento.setDescripcion(descripcion);
            evento.setFecha(fecha);
            evento.setTipo(TipoEvento.valueOf(tipo));

            eventoRepositorio.save(evento);
        }
    }

    //ELIMINAR
    @Transactional
    public void eliminarEvento(String id) {
        eventoRepositorio.deleteById(id);
    }

    //VALIDAR
    private void validar(String id, String nombre, String descripcion,
            Date fecha, String tipo) throws MiException {

        if (id.isEmpty() || id == null) {
            throw new MiException("El id del evento no puede estar vacío");
        }

        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre del evento no puede estar vacío");
        }

        if (descripcion.isEmpty() || descripcion == null) {
            throw new MiException("La descripcion del evento no puede estar vacía");
        }

        if (fecha.toString().isEmpty() || fecha == null) {
            throw new MiException("La fecha del evento no puede estar vacía");
        }

        if (tipo.isEmpty() || tipo == null) {
            throw new MiException("El tipo de evento no puede estar vacío");
        }
    }

}
