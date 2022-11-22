package com.gestiondepublicidad.repositorios;

import com.gestiondepublicidad.entidades.ListaDeTarea;
<<<<<<< HEAD
import com.gestiondepublicidad.entidades.Proyecto;
=======
>>>>>>> 77a026393b302b52e29671e406979bf8d2d434b9
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
<<<<<<< HEAD
import org.springframework.stereotype.Repository;

@Repository
public interface ListaDeTareaRepositorio extends JpaRepository<ListaDeTarea,String> {
    
    //DEVUELVE LA LISTA DE TAREAS ASGINADA A UN PROYECTO
    @Query("SELECT l FROM ListaDeTarea l WHERE l.proyecto = :proyecto")
    List<ListaDeTarea> buscarPorProyecto(@Param("proyecto") Proyecto proyecto);
    
=======

public interface ListaDeTareaRepositorio extends JpaRepository<ListaDeTarea, String> {

    //quiero que busque x titulo
    @Query("SELECT l FROM ListaDeTarea l WHERE l.titulo = :titulo")
    public ListaDeTarea buscarPorTitulo(@Param("titulo") String titulo);

    //quiero que busque x palabra del contenido 
    @Query("SELECT l FROM ListaDeTarea l WHERE l.notas LIKE %:notas%")
    public List<ListaDeTarea> buscarPorNotas(@Param("notas") String notas);

>>>>>>> 77a026393b302b52e29671e406979bf8d2d434b9
}
