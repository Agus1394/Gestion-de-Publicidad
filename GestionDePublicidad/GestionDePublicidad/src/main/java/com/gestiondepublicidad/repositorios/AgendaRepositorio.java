package com.gestiondepublicidad.repositorios;

import com.gestiondepublicidad.entidades.Agenda;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendaRepositorio extends JpaRepository<Agenda, String> {

    //busqueda por nombre del trabajador(user)
    @Query("SELECT a FROM Agenda a WHERE a.nombre LIKE %:nombre%")
    public List<Agenda> buscarPorNombre(@Param("nombre") String nombre);

    //búsqueda por email
    @Query("SELECT a FROM Agenda a WHERE a.email LIKE %:email%")
    public Agenda buscarPorEmail(@Param("email") String email);
    
    //búsqueda de user por puesto en la empresa
    @Query("SELECT a FROM Agenda a WHERE a.puesto_en_la_empresa WHERE a.puesto_en_la_empresa "
            + "LIKE %:puesto_en_la_empresa%")
    public Agenda buscarUserPorPuesto(@Param("puesto_en_la_empresa") String puesto_en_la_empresa);
    
}
