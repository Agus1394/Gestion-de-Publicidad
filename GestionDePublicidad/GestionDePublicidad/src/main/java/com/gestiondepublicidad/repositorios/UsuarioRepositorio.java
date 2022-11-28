package com.gestiondepublicidad.repositorios;

import com.gestiondepublicidad.entidades.Proyecto;
import java.util.List;

import com.gestiondepublicidad.enumeraciones.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.gestiondepublicidad.entidades.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

    // BUSQUEDA DE USUARIO POR SU NOMBRE
    @Query("SELECT u FROM Usuario u WHERE u.nombre LIKE %:nombre%")
    List<Usuario> buscarPorNombre(@Param("nombre") String nombre);

    // BUSQUEDA DE USUARIO POR SU NOMBRE y SU ROL
    @Query(value = "SELECT * FROM Usuario WHERE nombre LIKE %:nombre% AND rol = :rol",
    nativeQuery = true)
    List<Usuario> buscarPorNombreYRol(@Param("nombre") String nombre, @Param("rol") String rol);


    //BUSQUEDA DE USUARIO POR EMAIL
    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    Usuario buscarPorEmail(@Param("email") String email);

    // BUSQUEDA DE USUARIO POR PROYECTO, NOS DEVUELVE LA LISTA DE USUARIOS
    //CONECTADOS AL PROYECTO
    @Query("SELECT u FROM Usuario u WHERE u.proyecto = :proyecto")
    List<Usuario> buscarPorProyecto(@Param("proyecto") Proyecto proyecto);

    //Buscar usuario por ROL
    @Query(value = "SELECT * FROM Usuario WHERE rol = :rol",
    nativeQuery = true)
    List<Usuario> buscarPorRol(@Param("rol") String rol);
}
