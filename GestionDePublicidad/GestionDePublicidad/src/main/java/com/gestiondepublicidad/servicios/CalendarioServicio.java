package com.gestiondepublicidad.servicios;

import com.gestiondepublicidad.entidades.Calendario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gestiondepublicidad.repositorios.CalendarioRepositorio;
import org.springframework.transaction.annotation.Transactional;
import com.gestiondepublicidad.excepciones.MiException;
import java.util.Optional;

@Service
public class CalendarioServicio {
    
    @Autowired
    private CalendarioRepositorio calendarioRepositorio;
    
    @Transactional
    public Calendario subirCalendario(String idCalendario, 
            String descripcionCalendario, String eventoCalendario) throws MiException {
        
        validarRegistro(idCalendario, descripcionCalendario, eventoCalendario);
        
        Calendario nuevoCalendario = new Calendario();
        
        nuevoCalendario.setId_calendario(idCalendario);
        nuevoCalendario.setDescripcion(descripcionCalendario);
        nuevoCalendario.setEvento(eventoCalendario);
        
        return calendarioRepositorio.save(nuevoCalendario);
    }
    
    @Transactional
    public void modificarCalendario(String idCalendario, String descripcionCalendario,
            String eventoCalendario) throws MiException {
        
        Optional<Calendario> respuesta = calendarioRepositorio.findById(idCalendario);
        
        validarRegistro(idCalendario, descripcionCalendario, eventoCalendario);
        
        if (respuesta.isPresent()) {
            Calendario calendarioModif = new Calendario();
            calendarioModif.setId_calendario(idCalendario);
            calendarioModif.setDescripcion(descripcionCalendario);
            calendarioModif.setEvento(eventoCalendario);
            
            calendarioRepositorio.save(calendarioModif);
        }
    }
    
    @Transactional
    public void eliminarCalendario (String idCalendario){
        calendarioRepositorio.deleteById(idCalendario);
    }
    
    //me faltan las queris y alguna validacion !!
    
    private void validarRegistro(String idCalendario, String descripcionCalendario,
            String eventoCalendario) throws MiException{
        
        if (idCalendario == null || idCalendario.isEmpty()) {
            throw new MiException("El campo ID no debe quedar vacío");
        }
        if (descripcionCalendario == null|| descripcionCalendario.isEmpty()) {
            throw new  MiException("La descripción del calendario no debe quedar vacía");
        }
        if (eventoCalendario == null || eventoCalendario.isEmpty()) {
            throw new  MiException("El campo evento no debe quedar vacío");
        }
    }
}
