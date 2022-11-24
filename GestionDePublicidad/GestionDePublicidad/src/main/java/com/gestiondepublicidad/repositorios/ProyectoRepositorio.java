package com.gestiondepublicidad.repositorios;

import com.gestiondepublicidad.entidades.Proyecto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProyectoRepositorio extends JpaRepository<Proyecto, String> {

    @Query("SELECT p FROM Proyecto p WHERE p.nombre = :nombre")
    public Proyecto buscarPorNombreProy(@Param("nombre") String nombre);
    


//    @Query("SELECT p.id_proyecto, p.descripcion, p.estado_proyecto, p.fecha_fin, p.fecha_inicio,"
//            + " p.nombre, p.usuario_id_usuario FROM Proyecto p LEFT OUTER JOIN usuario u ON "
//            + "p.usuario_id_usuario = u.id_usuario WHERE u.nombre = :nombre")
//    public List<Proyecto> buscarPorUsuario(@Param("nombre") String nombre);


}
