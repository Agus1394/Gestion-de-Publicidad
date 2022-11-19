package com.gestiondepublicidad.servicios;

import com.gestiondepublicidad.repositorios.ListaDeTareaRepositorio;
import com.gestiondepublicidad.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListaDeTareaServicio {
    
    @Autowired
    private ListaDeTareaRepositorio listaDeTareaRepositorio;
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    
}
