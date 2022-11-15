package com.gestiondepublicidad.repositorios;

import com.gestiondepublicidad.entidades.Proyecto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProyectoRepositorio extends JpaRepository<Proyecto, String> {

    @Query("SELECT p FROM Proyecto p WHERE p.nombre= :nombre")
    public Proyecto buscarPorNombreProy(@Param("nombre") String nombre);

    @Query("SELECT p FROM Proyecto p WHERE p.usuario.nombre= :nombre")
    public List <Proyecto> buscarPorUsuario(@Param("nombre") String nombre);
    
}


