package com.gestiondepublicidad.entidades;

import com.gestiondepublicidad.enumeraciones.EstadoProyecto;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "PROYECTOS")
public class Proyecto {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id_proyecto;
    
    private String nombre;
    
    private String descripcion;

    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    
    //Revisar relacion
    @ManyToOne
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    private EstadoProyecto estadoProyecto;
}
