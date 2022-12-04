package com.gestiondepublicidad.repositorios;

import com.gestiondepublicidad.entidades.Tarea;
import com.gestiondepublicidad.enumeraciones.Estado;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TareaRepositorio extends JpaRepository<Tarea, String> {

    // BUSQUEDA DE TAREA POR SU NOMBRE
    @Query(value = "SELECT * FROM Tarea  WHERE nombre_tarea LIKE %:nombre%",
            nativeQuery = true)
    List<Tarea> buscarPorNombre(@Param("nombre") String nombre);

    // BUSQUEDA DE TAREA POR SU DESCRIPCION
    @Query(value = "SELECT * FROM Tarea WHERE descripcion LIKE %:descripcion%",
            nativeQuery = true)
    List<Tarea> buscarPorDescripcion(@Param("descripcion") String descripcion);

    //BUSQUEDA POR FECHA DE CRACION
    @Query(value = "SELECT * FROM Tarea WHERE fecha_creacion = :fechaCreacion",
            nativeQuery = true)
    public List<Tarea> buscarFechaCreacion(@Param("fechaCreacion") String fechaCreacion);

    //BUSQUEDA POR FECHA DE ASIGNACION
    @Query(value = "SELECT * FROM Tarea WHERE fecha_asignacion = :fechaAsignacion",
            nativeQuery = true)
    public List<Tarea> buscarFechaAsignacion(@Param("fechaAsignacion") String fechaAsignacion);

    //BUSQUEDA POR ESTADO
    @Query(value = "SELECT * FROM Tarea WHERE estado_tarea = :estado",
            nativeQuery = true)
    public List<Tarea> buscarPorEstado(@Param("estado") Estado estado);

}
