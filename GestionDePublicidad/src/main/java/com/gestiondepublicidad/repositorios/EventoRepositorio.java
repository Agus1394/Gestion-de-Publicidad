package com.gestiondepublicidad.repositorios;

import com.gestiondepublicidad.entidades.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepositorio extends JpaRepository<Evento, String> {
}
