package com.gestiondepublicidad.repositorios;

import com.gestiondepublicidad.entidades.Nota;
import com.gestiondepublicidad.entidades.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotaRepositorio extends JpaRepository<Nota, String> {

    @Query(value = "SELECT * FROM Nota WHERE id_usuario = :id_usuario",
    nativeQuery = true)
    public List<Nota> notasUsuario(@Param("id_usuario") String id_usuario);
}
