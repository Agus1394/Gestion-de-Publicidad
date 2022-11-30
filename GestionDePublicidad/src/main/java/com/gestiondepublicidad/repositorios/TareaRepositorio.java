package com.gestiondepublicidad.repositorios;

import com.gestiondepublicidad.entidades.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TareaRepositorio extends JpaRepository<Tarea, String> {
}
