package com.gestiondepublicidad.repositorios;

import com.gestiondepublicidad.entidades.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TareaRepositorio extends JpaRepository<Tarea, String> {
}
