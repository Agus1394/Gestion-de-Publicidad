package com.gestiondepublicidad.servicios;

import com.gestiondepublicidad.entidades.ListaDeTarea;
import com.gestiondepublicidad.entidades.Proyecto;
import com.gestiondepublicidad.excepciones.MiException;
import com.gestiondepublicidad.repositorios.ListaDeTareaRepositorio;
import com.gestiondepublicidad.repositorios.ProyectoRepositorio;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ListaDeTareaServicio {
    
    @Autowired
    private ListaDeTareaRepositorio listaDeTareaRepositorio;

    @Autowired
    private ProyectoRepositorio proyectoRepositorio;

    //CREAR
    @Transactional
    public void registrar(String id_tareas, String nombre, String descripcion,
            String id_proyecto) throws MiException {

        validar(id_tareas, nombre, descripcion,id_proyecto);

        Proyecto proyecto = proyectoRepositorio.findById(id_proyecto).get();
        ListaDeTarea listaDeTarea = new ListaDeTarea();

        listaDeTarea.setId_tareas(id_tareas);
        listaDeTarea.setNombre(nombre);
        listaDeTarea.setDescripcion(descripcion);
        listaDeTarea.setProyecto(proyecto);

        listaDeTareaRepositorio.save(listaDeTarea);
    }
    
    //ELIMINAR 
    public void eliminar(String id) {
        listaDeTareaRepositorio.deleteById(id);     
    }
    
    //VALIDACION
    private void validar(String id_tareas, String nombre, String descripcion,
            String id_proyecto) throws MiException {

        if (id_tareas.isEmpty() || id_tareas == null) {
            throw new MiException("El id de la tarea no puede estar vacío");
        }

        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre de la tarea no puede estar vacío");
        }

        if (descripcion.isEmpty() || descripcion == null) {
            throw new MiException("La descripcion de la tarea no puede estar vacía");
        }

        if (id_proyecto.isEmpty() || id_proyecto == null) {
            throw new MiException("El id del proyecto no puede estar vacío");
        }
    }
    
    
    
}
