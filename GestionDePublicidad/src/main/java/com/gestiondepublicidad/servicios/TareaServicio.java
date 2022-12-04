package com.gestiondepublicidad.servicios;

import com.gestiondepublicidad.entidades.Tarea;
import com.gestiondepublicidad.entidades.Usuario;
import com.gestiondepublicidad.enumeraciones.Estado;
import com.gestiondepublicidad.excepciones.MiException;
import com.gestiondepublicidad.repositorios.TareaRepositorio;
import com.gestiondepublicidad.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TareaServicio {

    @Autowired
    TareaRepositorio tareaRepositorio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    //REGISTRA UN NUEVO EVENTO
    @Transactional
    public void registrar(String id, String nombreTarea, String descripcion,
            Date fechaCreacion, Date fechaAsignacion, String estadoTarea, String id_usuario)
            throws MiException {

        validar(id, nombreTarea, descripcion, fechaCreacion, fechaAsignacion, estadoTarea);
        Optional<Usuario> respuestausuario = usuarioRepositorio.findById(id_usuario);

        Tarea tarea = tareaRepositorio.findById(id).get();
        Usuario usuario = new Usuario();

        if (respuestausuario.isPresent()) {
            usuario = respuestausuario.get();
        }

        tarea.setId_tarea(id);
        tarea.setNombreTarea(nombreTarea);
        tarea.setDescripcion(descripcion);
        tarea.setFechaCreacion(fechaCreacion);
        tarea.setFechaAsignacion(fechaAsignacion);
        tarea.setEstadoTarea(Estado.valueOf(estadoTarea.toUpperCase()));
        tarea.setUsuario(usuario);

        tareaRepositorio.save(tarea);
    }

    //LISTAR TAREAS
    //LISTAR TODAS LAS TAREAS
    public List<Tarea> listarTodos() {

        List<Tarea> tareas = new ArrayList<>();

        return tareas = tareaRepositorio.findAll();
    }

    //LISTAR POR ID
    public Tarea getOne(String id) {
        return tareaRepositorio.getOne(id);
    }

    //LISTAR POR NOMBRE
    public List<Tarea> buscarPorNombre(String nombre) {
        return tareaRepositorio.buscarPorNombre(nombre);
    }

    //LISTAR POR DESCRIPCION
    public List<Tarea> buscarPorDescripcion(String descripcion) {
        return tareaRepositorio.buscarPorDescripcion(descripcion);
    }

    //LISTAR POR FECHA DE CREACION
    public List<Tarea> buscarPorFechaCracion(String fechaCreacion) {
        return tareaRepositorio.buscarFechaCreacion(fechaCreacion);
    }

    //LISTAR POR FECHA DE ASIGACION
    public List<Tarea> buscarPorFechaAsignacion(String fechaAsignacion) {
        return tareaRepositorio.buscarFechaAsignacion(fechaAsignacion);
    }

    //LISTAR POR TIPO DE EVENTO
    public List<Tarea> buscarPorEstado(Estado estado) {
        return tareaRepositorio.buscarPorEstado(estado);
    }

    //MODIFICAR
    @Transactional
    public void actualizar(String id, String nombreTarea, String descripcion,
            Date fechaCreacion, Date fechaAsignacion, String estadoTarea, String id_usuario)
            throws MiException {

        validar(id, nombreTarea, descripcion, fechaCreacion, fechaAsignacion, estadoTarea);
        Optional<Tarea> respuesta = tareaRepositorio.findById(id);
        Optional<Usuario> respuestaUsuario = usuarioRepositorio.findById(id_usuario);
        Usuario usuario = new Usuario();

        if (respuestaUsuario.isPresent()) {
            usuario = respuestaUsuario.get();
        }

        if (respuesta.isPresent()) {
            Tarea tarea = respuesta.get();

            tarea.setNombreTarea(nombreTarea);
            tarea.setDescripcion(descripcion);
            tarea.setFechaCreacion(fechaCreacion);
            tarea.setFechaAsignacion(fechaAsignacion);

            tarea.setEstadoTarea(Estado.valueOf(estadoTarea));

            tareaRepositorio.save(tarea);
        }
    }

    //ELIMINAR
    @Transactional
    public void eliminarTarea(String id) {
        tareaRepositorio.deleteById(id);
    }

    //VALIDAR
    private void validar(String id, String nombreTarea, String descripcion,
            Date fechaCreacion, Date fechaAsignacion, String estadoTarea) throws MiException {

        if (id.isEmpty() || id == null) {
            throw new MiException("El id de la tarea no puede estar vacío");
        }

        if (nombreTarea.isEmpty() || nombreTarea == null) {
            throw new MiException("El nombre de la tarea no puede estar vacío");
        }

        if (descripcion.isEmpty() || descripcion == null) {
            throw new MiException("La descripcion de la tarea no puede estar vacía");
        }

        if (fechaCreacion.toString().isEmpty() || fechaCreacion == null) {
            throw new MiException("La fecha de creacion no puede estar vacía");
        }

        if (fechaAsignacion.toString().isEmpty() || fechaAsignacion == null) {
            throw new MiException("La fecha de asigacion no puede estar vacía");
        }

        if (estadoTarea.isEmpty() || estadoTarea == null) {
            throw new MiException("El estado de la tarea no puede estar vacío");
        }
    }

}
