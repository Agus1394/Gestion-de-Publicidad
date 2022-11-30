package com.gestiondepublicidad.repositorios;

import com.gestiondepublicidad.entidades.Nota;
import com.gestiondepublicidad.entidades.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotaRepositorio extends JpaRepository<Nota, String> {
}
