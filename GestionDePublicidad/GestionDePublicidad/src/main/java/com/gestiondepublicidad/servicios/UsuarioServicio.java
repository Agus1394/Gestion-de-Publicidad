package com.gestiondepublicidad.servicios;

import com.gestiondepublicidad.entidades.Foto;
import com.gestiondepublicidad.entidades.Proyecto;
import com.gestiondepublicidad.entidades.Usuario;
import com.gestiondepublicidad.enumeraciones.Rol;
import com.gestiondepublicidad.excepciones.MiException;
import com.gestiondepublicidad.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private FotoServicio fotoServicio;

    //CREA/REGISTRA UN NUEVO USUARIO
    @Transactional
    public Usuario registrar(MultipartFile archivo, String nombre, String email, String contrasenia,
            String contrasenia2) throws MiException {

        validar(nombre, email, contrasenia, contrasenia2);

        Usuario usuario = new Usuario();

        usuario.setNombre(nombre);
        usuario.setEmail(email);

        usuario.setContrasenia(new BCryptPasswordEncoder().
                encode(contrasenia));

        usuario.setRol(Rol.USER);

        Foto foto = fotoServicio.guardar(archivo);
        usuario.setFoto(foto);
        return usuarioRepositorio.save(usuario);
    }

    //MODIFICA LOS DATOS EXISTENTES DE UN USUARIO
    @Transactional
    public void actualizar(MultipartFile archivo, String id_usuario, String nombre, String email,
            String password, String password2) throws MiException {

        validar(nombre, email, password, password2);

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id_usuario);

        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();
            usuario.setNombre(nombre);
            usuario.setEmail(email);

            usuario.setContrasenia(new BCryptPasswordEncoder().encode(password));

            usuario.setRol(Rol.USER);

            String idFoto = null;

            if (usuario.getFoto() != null) {
                idFoto = usuario.getFoto().getId_foto();
            }
            Foto foto = fotoServicio.actualizar(archivo, idFoto);
            usuario.setFoto(foto);
            usuarioRepositorio.save(usuario);
        }

    }

    //DEVUELVE UN USUARIO POR SU ID
    public Usuario getOne(String id_usuario) {
        return usuarioRepositorio.getOne(id_usuario);
    }

    //DEVUELVE LOS USUARIO CON EL MISMO NOMBRE
    public List<Usuario> usuariosPorNombre(String nombre) {

        List<Usuario> usuarios = new ArrayList();

        return usuarios = usuarioRepositorio.buscarPorNombre(nombre);
    }

    //DEVUELVE UN USUARIO BUSCADO POR SU EMAIL
    public Usuario usuariosPorEmail(String email) {
        return usuarioRepositorio.buscarPorEmail(email);
    }

    //DEVUELVE LOS USUARIOS CONECTADOS AL MISMO PROYECTO
    public List<Usuario> usuariosPorProyecto(Proyecto proyecto) {
        List<Usuario> usuarios = new ArrayList();

        return usuarios = usuarioRepositorio.buscarPorProyecto(proyecto);
    }

    //DEVUELVE UNA LISTA DE TODOS LOS USUARIOS
    public List<Usuario> listarUsuarios() {

        List<Usuario> usuarios = new ArrayList();

        return usuarios = usuarioRepositorio.findAll();
    }

    //CAMBIA ROLES ENTRE USER, CLIENTE Y ADMIN
    @Transactional
    public void cambiarRol(String id_usuario) {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id_usuario);

        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();

            if (usuario.getRol().equals(Rol.CLIENTE)) {

                usuario.setRol(Rol.USER);

            } else if (usuario.getRol().equals(Rol.USER)) {
                usuario.setRol(Rol.ADMIN);

            } else if (usuario.getRol().equals(Rol.ADMIN)) {
                usuario.setRol(Rol.CLIENTE);
            }
        }
    }

    //EDICION DE ADMIN Y VER SI DE CLIENTE
    public void adminEditar(MultipartFile archivo, String idUsuario, String nombre,
            String email) throws MiException {

        Optional<Usuario> respuesta = usuarioRepositorio.findById(idUsuario);

        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();

            usuario.setNombre(nombre);
            usuario.setEmail(email);
            String idImagen = null;
            if (usuario.getFoto()!= null) {

                idImagen = usuario.getFoto().getId_foto();
            }
            if (null != archivo && !archivo.isEmpty()) {
                Foto foto = fotoServicio.actualizar(archivo, idImagen);
                usuario.setFoto(foto);
            }

            usuarioRepositorio.save(usuario);
        }

    }

    //ELIMINA UN USUARIO SEGÚN SU ID
    @Transactional
    public void eliminarUsuario(String id_usuario) {
        usuarioRepositorio.deleteById(id_usuario);
    }

    //IMPIDE QUE SE INGRESEN DATOS NULOS
    private void validar(String nombre, String email, String contrasenia, String contrasenia2)
            throws MiException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("Debe ingresar un nombre");
        }
        if (email.isEmpty() || email == null) {
            throw new MiException("Debe ingresar un email válido");
        }
        if (contrasenia.isEmpty() || contrasenia == null || contrasenia.length() <= 5) {
            throw new MiException("La contraseña no puede estar vacía, y debe tener más de 5 dígitos");
        }

        if (!contrasenia.equals(contrasenia2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
        }

    }
}
