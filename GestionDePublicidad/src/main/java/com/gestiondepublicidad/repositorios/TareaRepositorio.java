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
    @Query("SELECT t FROM Tarea t  WHERE t.nombre_tarea LIKE %:nombre%")
    List<Tarea> buscarPorNombre(@Param("nombre") String nombre);

    // BUSQUEDA DE TAREA POR SU DESCRIPCION
    @Query("SELECT t FROM Tarea t  WHERE t.descripcion LIKE %:descripcion%")
    List<Tarea> buscarPorDescripcion(@Param("descripcion") String descripcion);

    //BUSQUEDA POR FECHA DE CRACION
    @Query("SELECT t FROM Tarea t WHERE t.fecha_creacion = :fechaCreacion")
    public List<Tarea> buscarFechaCreacion(@Param("fechaCreacion") String fechaCreacion);

    //BUSQUEDA POR FECHA DE ASIGNACION
    @Query("SELECT t FROM Tarea t WHERE t.fecha_asignacion = :fechaAsignacion")
    public List<Tarea> buscarFechaAsignacion(@Param("fechaAsignacion") String fechaAsignacion);
    
    //BUSQUEDA POR ESTADO
    @Query("SELECT t FROM Tarea t WHERE t.estado_tarea = :estado")
    public List<Tarea> buscarPorEstado(@Param("estado") Estado estado);

}
