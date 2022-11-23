package com.gestiondepublicidad.repositorios;

import com.gestiondepublicidad.entidades.Calendario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarioRepositorio extends JpaRepository <Calendario, String>{

    //quiero que busque el evento
    @Query("SELECT c FROM Calendario c WHERE c.evento = :evento")
    public List <Calendario> buscarEvento(@Param("evento") String evento);
    
    //(cambio) Buscar por palabras que contenga la descripción del evento
    @Query("SELECT c FROM Calendario c WHERE c.descripcion LIKE %:descripcion%")
    public List<Calendario> buscarDescripcion(@Param("descripcion") String descripcion);
    
        
}
