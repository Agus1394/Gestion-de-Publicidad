package com.gestiondepublicidad.repositorios;

import com.gestiondepublicidad.entidades.Agenda;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendaRepositorio extends JpaRepository <Agenda, String>{
    
    @Query("SELECT a FROM Agenda a WHERE a.numero_cliente = :numero_cliente")
    public List <Agenda> buscarNumeroCliente(@Param ("numero_cliente") Long numero_cliente);
    
    @Query("SELECT a FROM Agenda a WHERE a.numero_interno = :numero_interno")
    public List<Agenda> buscarNumeroInterno (@Param ("numero_interno") Long numero_interno);
}
