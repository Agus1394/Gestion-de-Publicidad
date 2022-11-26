package com.gestiondepublicidad.servicios;

import com.gestiondepublicidad.entidades.ListaDeTarea;
import com.gestiondepublicidad.entidades.Proyecto;
import com.gestiondepublicidad.excepciones.MiException;
import com.gestiondepublicidad.repositorios.ListaDeTareaRepositorio;
import com.gestiondepublicidad.repositorios.ProyectoRepositorio;
import java.util.Date;
import com.gestiondepublicidad.excepciones.MiException;
import com.gestiondepublicidad.repositorios.ListaDeTareaRepositorio;
import com.gestiondepublicidad.entidades.ListaDeTarea;
import com.gestiondepublicidad.repositorios.UsuarioRepositorio;
import java.util.Date;
import com.gestiondepublicidad.entidades.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



    
@Service
public class ListaDeTareaServicio {

    @Autowired
    ListaDeTareaRepositorio listaDeTareaRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public void registrar(String id, String titulo, String notas,
            Date eventoComun, Date eventoPrivado, String id_usuario) throws MiException {

        validar(id, titulo, notas, eventoComun, eventoPrivado, id_usuario);

        Usuario usuario = usuarioRepositorio.findById(id_usuario).get();
        ListaDeTarea lista = new ListaDeTarea();

        lista.setId(id);
        lista.setTitulo(titulo);
        lista.setNotas(notas);
        lista.setEventoComun(eventoComun);
        lista.setEventoPrivado(eventoPrivado);
        lista.setUsuario(usuario);

        listaDeTareaRepositorio.save(lista);
    }

    //ACTUALIZAR
    @Transactional
    public void actualizar(String id, String titulo, String notas,
            Date eventoComun, Date eventoPrivado, String id_usuario) throws MiException {

        validar(id, titulo, notas, eventoComun, eventoPrivado, id_usuario);

        Optional<ListaDeTarea> respuesta = listaDeTareaRepositorio.findById(id);
        Optional<Usuario> respuestaUsuario = usuarioRepositorio.findById(id_usuario);
        Usuario usuario = new Usuario();

        if (respuestaUsuario.isPresent()) {
            usuario = respuestaUsuario.get();
        }
        if (respuesta.isPresent()) {
            ListaDeTarea lista = respuesta.get();

            lista.setTitulo(titulo);
            lista.setNotas(notas);
            lista.setEventoComun(eventoComun);
            lista.setEventoPrivado(eventoPrivado);
            lista.setUsuario(usuario);
            listaDeTareaRepositorio.save(lista);
        }
    }

    //LISTAR-QUERYS
    public ListaDeTarea getOne(String id) {
        return listaDeTareaRepositorio.getOne(id);
    }

    public List<ListaDeTarea> listarTodos() {

        List<ListaDeTarea> lista = new ArrayList<>();

        return lista = listaDeTareaRepositorio.findAll();
    }

    public ListaDeTarea buscarPorTitulo(String titulo) {
        return listaDeTareaRepositorio.buscarPorTitulo(titulo);
    }

    public List<ListaDeTarea> buscarPorNotas(String notas) {

        List<ListaDeTarea> lista = new ArrayList<>();

        return lista = listaDeTareaRepositorio.buscarPorNotas(notas);
    }

    //ELIMINAR 
    public void eliminar(String id) {
        listaDeTareaRepositorio.deleteById(id);
    }

    //VALIDACION
    private void validar(String id, String titulo, String notas,
            Date eventoComun, Date eventoPrivado, String id_usuario) throws MiException {

        if (id.isEmpty() || id == null) {
            throw new MiException("El id no puede estar vacío");
        }

        if (titulo.isEmpty() || titulo == null) {
            throw new MiException("El titulo no puede estar vacío");
        }

        if (notas.isEmpty() || notas == null) {
            throw new MiException("Las notas no pueden estar vacías");
        }

        if (eventoComun.toString().isEmpty() || eventoComun == null) {
            throw new MiException("La fecha no puede estar vacía");
        }

        if (eventoPrivado.toString().isEmpty() || eventoPrivado == null) {
            throw new MiException("La fecha no puede estar vacía");
        }
        
        if (id_usuario.isEmpty() || id_usuario == null) {
            throw new MiException("El id no puede estar vacío");
        }
    }

}
