package com.gestiondepublicidad.repositorios;

import com.gestiondepublicidad.entidades.Proyecto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.gestiondepublicidad.entidades.Usuario;


@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {
    
    // BUSQUEDA DE USUARIO POR SU NOMBRE, SI ESTA REPETIDO MUESTRA LISTA
    @Query("SELECT u FROM Usuario u WHERE u.nombre = :nombre")
    List<Usuario> buscarPorNombre(@Param("nombre") String nombre);
    
    //BUSQUEDA DE USUARIO POR EMAIL
    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    Usuario buscarPorEmail(@Param("email") String email);
    
    // BUSQUEDA DE USUARIO POR PROYECTO, NOS DEVUELVE LA LISTA DE USUARIOS
    //CONECTADOS AL PROYECTO
    @Query("SELECT u FROM Usuario u WHERE u.proyecto = :proyecto")
    List<Usuario> buscarPorProyecto(@Param("proyecto") Proyecto proyecto);
    
}
