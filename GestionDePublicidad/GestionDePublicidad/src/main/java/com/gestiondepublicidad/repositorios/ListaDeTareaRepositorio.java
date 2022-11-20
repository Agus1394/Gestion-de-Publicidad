package com.gestiondepublicidad.repositorios;

import com.gestiondepublicidad.entidades.ListaDeTarea;
import com.gestiondepublicidad.entidades.Proyecto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ListaDeTareaRepositorio extends JpaRepository<ListaDeTarea,String> {
    
    //DEVUELVE LA LISTA DE TAREAS ASGINADA A UN PROYECTO
    @Query("SELECT l FROM ListaDeTarea l WHERE l.proyecto = :proyecto")
    List<ListaDeTarea> buscarPorProyecto(@Param("proyecto") Proyecto proyecto);
    
}
