package com.gestiondepublicidad.repositorios;

import com.gestiondepublicidad.entidades.Proyecto;
import com.gestiondepublicidad.entidades.Usuario;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProyectoRepositorio extends JpaRepository<Proyecto, String> {

    @Query("SELECT p FROM Proyecto p WHERE UPPER(p.nombre) LIKE %:nombre%")
    public List<Proyecto> buscarPorNombreProy(@Param("nombre") String nombre);

    @Query(value = "SELECT * FROM Proyecto WHERE estado_Proyecto = :estadoProyecto",

    nativeQuery = true)
    public List<Proyecto> buscarPorEstado(@Param("estadoProyecto") String estadoProyecto);

    @Query(value = "SELECT * FROM Proyecto WHERE fecha_Inicio = :fechaInicio",
    nativeQuery = true)
    public List<Proyecto> proyectosOrdenadosPorFechaInicio(String fechaInicio);

    @Query(value = "SELECT * FROM Proyecto WHERE fecha_Fin = :fechaFin",
            nativeQuery = true)
    public List<Proyecto> proyectosOrdenadosPorFechaFin(String fechaFin);
}
