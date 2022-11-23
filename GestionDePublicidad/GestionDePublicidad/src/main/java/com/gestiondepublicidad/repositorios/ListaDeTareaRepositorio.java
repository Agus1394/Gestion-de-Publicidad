package com.gestiondepublicidad.repositorios;

import com.gestiondepublicidad.entidades.ListaDeTarea;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ListaDeTareaRepositorio extends JpaRepository<ListaDeTarea, String> {

    //quiero que busque x titulo
    @Query("SELECT l FROM ListaDeTarea l WHERE l.titulo = :titulo")
    public ListaDeTarea buscarPorTitulo(@Param("titulo") String titulo);

    //quiero que busque x palabra del contenido 
    @Query("SELECT l FROM ListaDeTarea l WHERE l.notas LIKE %:notas%")
    public List<ListaDeTarea> buscarPorNotas(@Param("notas") String notas);

}
