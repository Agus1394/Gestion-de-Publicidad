package com.gestiondepublicidad.repositorios;

import com.gestiondepublicidad.entidades.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoRepositorio extends JpaRepository<Foto, String>{

    @Query("SELECT nombre FROM Foto f WHERE f.nombre = :nombre")
    public Foto buscarPorNombre(@Param("nombre")String nombre);
    
}
