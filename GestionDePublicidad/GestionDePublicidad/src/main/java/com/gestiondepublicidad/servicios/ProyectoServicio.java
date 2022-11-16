package com.gestiondepublicidad.servicios;

import com.gestiondepublicidad.repositorios.ProyectoRepositorio;
import com.gestiondepublicidad.repositorios.UsuarioRepositorio;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ProyectoServicio {
    
    @Autowired
    private ProyectoRepositorio proyectoRepositorio;
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    //quiero subir un proyecto
    @Transactional
    public void subirProyecto(String id_proyecto, String nombre, String descripcion,
    Date fechaInicio, Date fechaFin, ){
        
    }
}
