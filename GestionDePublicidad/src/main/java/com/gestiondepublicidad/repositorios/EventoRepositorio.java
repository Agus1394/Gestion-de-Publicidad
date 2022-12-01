package com.gestiondepublicidad.repositorios;

import com.gestiondepublicidad.entidades.Evento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepositorio extends JpaRepository<Evento, String> {

    // BUSQUEDA DE EVENTO POR SU NOMBRE
    @Query("SELECT e FROM Evento e  WHERE e.nombre LIKE %:nombre% AND u.tipo LIKE %:tipo%")
    List<Evento> buscarPorNombre(@Param("nombre") String nombre, @Param("tipo") String tipo);

    // BUSQUEDA DE EVENTO POR SU DESCRIPCION
    @Query("SELECT e FROM Evento e  WHERE e.descripcion LIKE %:descripcion% AND u.tipo LIKE %:tipo%")
    List<Evento> buscarPorDescripcion(@Param("descripcion") String descripcion, @Param("tipo") String tipo);

    //FILTRAR EVENTO POR FECHA (DUDOSO)
    @Query("SELECT e FROM Evento e  WHERE e.fecha = :fecha AND u.tipo LIKE %:tipo%")
    List<Evento> buscarPorFecha(@Param("fecha") String fecha, @Param("tipo") String tipo);

    //FILTRAR EVENTO POR TIPO EVENTOD
    @Query("SELECT e FROM Evento e  WHERE u.tipo LIKE %:tipo%")
    List<Evento> buscarPorTipoEvento(@Param("tipo") String tipo);

}
