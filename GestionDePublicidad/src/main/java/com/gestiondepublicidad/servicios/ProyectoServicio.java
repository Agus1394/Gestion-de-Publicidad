package com.gestiondepublicidad.servicios;

import com.gestiondepublicidad.entidades.Proyecto;
import com.gestiondepublicidad.entidades.Usuario;
import com.gestiondepublicidad.excepciones.MiException;
import com.gestiondepublicidad.repositorios.ProyectoRepositorio;
import com.gestiondepublicidad.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProyectoServicio {

    @Autowired
    private ProyectoRepositorio proyectoRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    //----------------------------------CUD, read esta mas abajo-----------------------------------------
    //CREAR
    @Transactional
    public void registrar(String id_proyecto, String nombre, String descripcion,
                          Date fechaInicio, Date fechaFin, String id_usuario) throws MiException {

        validar(id_proyecto, nombre, descripcion, fechaInicio, fechaFin);

        Proyecto proyecto = new Proyecto();

        Usuario usuario = usuarioRepositorio.getOne(id_usuario);

        List<Usuario> nuevoUsuario = usuarioRepositorio.agendaProyecto(id_proyecto);
        nuevoUsuario.add(usuario);


        proyecto.setId_proyecto(id_proyecto);
        proyecto.setNombre(nombre);
        proyecto.setDescripcion(descripcion);
        proyecto.setFechaInicio(fechaInicio);
        proyecto.setFechaFin(fechaFin);
        proyecto.setUsuario(nuevoUsuario);

        proyectoRepositorio.save(proyecto);
    }

    @Transactional
    public void actualizar(String id_proyecto, String nombre, String descripcion,
                           Date fechaInicio, Date fechaFin, String id_usuario) throws MiException {

        validar(id_proyecto, nombre, descripcion, fechaInicio, fechaFin);

        Optional<Proyecto> respuesta = proyectoRepositorio.findById(id_proyecto);
        Optional<Usuario> respuestaUsuario = usuarioRepositorio.findById(id_usuario);
        Usuario usuario = new Usuario();

        List<Proyecto> proyectoActualizado = new ArrayList();

        if (respuestaUsuario.isPresent()) {
            usuario = respuestaUsuario.get();
        }

        if (respuesta.isPresent()) {
            Proyecto proyecto = respuesta.get();

            proyecto.setNombre(nombre);
            proyecto.setDescripcion(descripcion);
            proyecto.setFechaInicio(fechaInicio);
            proyecto.setFechaFin(fechaFin);

            proyectoActualizado.add(proyecto);
            usuario.setProyecto(proyectoActualizado);

            proyectoRepositorio.save(proyecto);
        }
    }

    public void eliminar(String id) {
        proyectoRepositorio.deleteById(id);
    }

    //Buscar uno por id
    public Proyecto getOne(String id) throws MiException {
        return proyectoRepositorio.getOne(id);
    }

        //--------------------------------------SUS USUARIOS-------------------------------------
        @Transactional
        public void agregarUsuarioProyecto (String id_proyecto, String id_usuario){
            Proyecto proyecto = proyectoRepositorio.getOne(id_proyecto);
            Usuario usuario = usuarioRepositorio.getOne(id_usuario);

            List<Usuario> usuarios = proyecto.getUsuario();
            List<Proyecto> proyectos = usuario.getProyecto();

            usuarios.add(usuario);
            proyecto.setUsuario(usuarios);

            proyectos.add(proyecto);
            usuario.setProyecto(proyectos);

            usuarioRepositorio.save(usuario);
            proyectoRepositorio.save(proyecto);
        }

        @Transactional
        public void eliminarUsuarioProyecto (String id_proyecto, String id_usuario){
            Proyecto proyecto = proyectoRepositorio.getOne(id_proyecto);
            Usuario usuario = usuarioRepositorio.getOne(id_usuario);

            List<Usuario> usuarios = proyecto.getUsuario();
            List<Proyecto> proyectos = usuario.getProyecto();

            usuarios.remove(usuario);
            proyecto.setUsuario(usuarios);

            proyectos.remove(proyecto);
            usuario.setProyecto(proyectos);

            usuarioRepositorio.save(usuario);
            proyectoRepositorio.save(proyecto);
        }


//ADMIN
        //-----------------------------------FILTROS---------------------------------------------
        //buscar Proyectos por Usuario
        public List<Proyecto> buscarPorUsuario (String id_usuario){
            List<Proyecto> proyectos = proyectoRepositorio.proyectosDelUsuario(id_usuario);
            return proyectos;
        }

        //metodo que filtra al proyecto por estado
        public List<Proyecto> filtrarProyectoPorEstado (String estadoProyecto) throws MiException {
            return proyectoRepositorio.buscarPorEstado(estadoProyecto);
        }

        public List<Proyecto> listarTodos () {
            List<Proyecto> proyectos = new ArrayList<>();
            return proyectoRepositorio.findAll();

        }

        public List<Proyecto> buscarPorNombre (String nombre){
            return proyectoRepositorio.buscarPorNombreProy(nombre);
        }

        public List<Proyecto> ordenarProyectosPorFechaInicio (String fechaInicio){
            return proyectoRepositorio.proyectosOrdenadosPorFechaInicio(fechaInicio);
        }

        public List<Proyecto> ordenarProyectosPorFechaFin (String fechaFin){
            return proyectoRepositorio.proyectosOrdenadosPorFechaFin(fechaFin);
        }


        //TRABAJADOR Y CLIENTE
//---------------------------------FILTROS POR ROL Y...-------------------------------------------------------

        public List<Proyecto> proyectosPorIdYNombre (String nombre, String id){
            return proyectoRepositorio.listarIDyNombre(id, nombre);

        }
        public List<Proyecto> proyectosPorIdYEstado (String estado, String id){
            return proyectoRepositorio.listarIDyEstado(id, estado);

        }
        public List<Proyecto> proyectosPorIdYFechaFin (String fechaFin, String id){
            return proyectoRepositorio.listarIDyFechaFin(id, fechaFin);
        }

        public List<Proyecto> proyectosPorIdYFechaInicio (String fechaInicio, String id){
            return proyectoRepositorio.listarIDyFechaInicio(id, fechaInicio);
        }

        //------------------------------------------------------------------------------------------
        //VALIDACION
        private void validar(String id_proyecto, String nombre, String descripcion, Date fechaInicio, Date fechaFin) throws MiException{

            if (id_proyecto.isEmpty() || id_proyecto == null) {
                throw new MiException("El id del proyecto no puede estar vacío");
            }

            if (nombre.isEmpty() || nombre == null) {
                throw new MiException("El nombre del proyecto no puede estar vacío");
            }

            if (descripcion.isEmpty() || descripcion == null) {
                throw new MiException("La descripcion del proyecto no puede estar vacía");
            }

            if (fechaInicio.toString().isEmpty() || fechaInicio == null) {
                throw new MiException("La fecha del proyecto no puede estar vacía");
            }

            if (fechaFin.toString().isEmpty() || fechaFin == null) {
                throw new MiException("La fecha del proyecto no puede estar vacía");
            }

        }

}

