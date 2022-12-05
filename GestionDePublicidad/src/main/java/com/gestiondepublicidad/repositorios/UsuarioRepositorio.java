package com.gestiondepublicidad.repositorios;

import com.gestiondepublicidad.entidades.Proyecto;
import java.util.List;

import com.gestiondepublicidad.enumeraciones.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.gestiondepublicidad.entidades.Usuario;
import com.gestiondepublicidad.enumeraciones.PuestoEmpresa;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

    // BUSQUEDA DE USUARIO POR SU NOMBRE
    @Query(value = "SELECT u.* FROM Usuario u WHERE UPPER(u.nombre) LIKE %:nombre%",
            nativeQuery = true)
    List<Usuario> buscarPorNombre(@Param("nombre") String nombre);

    // BUSQUEDA DE USUARIO POR SU NOMBRE y SU ROL
    @Query(value = "SELECT u.* FROM Usuario u WHERE UPPER(u.nombre) LIKE %:nombre% AND u.rol = :rol",
            nativeQuery = true)
    List<Usuario> buscarPorNombreYRol(@Param("nombre") String nombre, @Param("rol") String rol);

    //BUSQUEDA DE USUARIO POR EMAIL ///VALIDACIONES\\\
    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    Usuario buscarPorEmail(@Param("email") String email);
    
    //BUSQUEDA DE USUARIO POR EMAIL ///NO BORRAR\\\
    @Query("SELECT u FROM Usuario u WHERE u.email = :email AND u.rol = :rol")
    Usuario buscarUsuarioPorEmail(@Param("email") String email, @Param("rol") Rol rol);
    
    @Query("SELECT u FROM Usuario u WHERE u.puestoEmpresa LIKE %:puesto_empresa% AND u.rol = :rol")
    List<Usuario> buscarUsuarioPorPuesto(@Param("puesto_empresa") PuestoEmpresa puesto_empresa,
       @Param("rol") Rol rol);


    // BUSQUEDA DE USUARIO POR PROYECTO, NOS DEVUELVE LA LISTA DE USUARIOS
    //CONECTADOS AL PROYECTO
    @Query("SELECT u FROM Usuario u WHERE UPPER(u.proyecto) = :proyecto AND u.rol = :rol")
    List<Usuario> buscarPorProyecto(@Param("proyecto") String proyecto, @Param("rol") Rol rol);

    //Buscar usuario por ROL
    @Query(value = "SELECT u FROM Usuario u WHERE u.rol = :rol",
            nativeQuery = true)
    List<Usuario> buscarPorRol(@Param("rol") String rol);

    //AGENDA
    @Query(value = "SELECT u.* FROM Usuario u LEFT JOIN usuario_proyecto up ON u.id_usuario = up.usuario_id_usuario WHERE up.proyecto_id_proyecto = :id_proyecto",
            nativeQuery = true)
    public List<Usuario> agendaProyecto(@Param("id_proyecto") String id_proyecto);

//    @Query(value = "SELECT u.* FROM Usuario u LEFT JOIN usuario_proyecto up ON u.id_usuario = up.usuario_id_usuario WHERE %:nombre% LIKE up.nombre",
//            nativeQuery = true)
//    public List<Usuario> nombreProyectoUsuario(@Param("id_proyecto") String id_proyecto);

    @Query("SELECT u FROM Usuario u WHERE UPPER(u.rol) = :rol")
    List<Usuario> buscarPorRol(@Param("rol") Rol rol);

}
