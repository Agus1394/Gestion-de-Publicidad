package com.gestiondepublicidad.servicios;

import com.gestiondepublicidad.entidades.Nota;
import com.gestiondepublicidad.entidades.Usuario;
import com.gestiondepublicidad.excepciones.MiException;
import com.gestiondepublicidad.repositorios.NotaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NotaServicio {

    @Autowired
    NotaRepositorio notaRepositorio;

    @Autowired
    UsuarioServicio usuarioServicio;

    @Transactional
    public void crearNota(Nota nota){
        /*Nota nota = new Nota();

        nota.setDescripcion(descripcion);
        nota.setTitulo(titulo);
        nota.setFechaCreacion(new Date());*/

        notaRepositorio.save(nota);
    }


    @Transactional
    public void actualizar(String id, String descripcion, String titulo, Usuario usuario) throws MiException {

        Optional<Nota> respuesta = notaRepositorio.findById(id);
        if (respuesta.isPresent()){
            Nota nota = respuesta.get();
            nota.setDescripcion(descripcion);
            nota.setTitulo(titulo);

            notaRepositorio.save(nota);
        }else{
            throw new MiException("No se pudo actualizar la nota");
        }
    }

    @Transactional
    public void eliminar(String id) throws MiException {
        Nota nota = notaRepositorio.getReferenceById(id);
        notaRepositorio.delete(nota);
    }

    public List<Nota> listarNotas(String id_usuario){
        return notaRepositorio.notasUsuario(id_usuario);
    }
}