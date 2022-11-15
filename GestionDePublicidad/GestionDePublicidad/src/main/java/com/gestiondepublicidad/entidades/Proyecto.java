package com.gestiondepublicidad.entidades;

import com.gestiondepublicidad.enumeraciones.EstadoProyecto;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
    @Column(name= "ID_PROYECTO")
    private String id_proyecto;
    
    @Column(name= "NOMBRE PROYECTO")
    private String nombre;
    @Column(name = "DESCRIPCIÓN")
    private String descripcion;

    @Column(name ="FECHA DE INICIO")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    
    @Column(name ="FECHA FINAL")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    
    @Column(name ="ESTADO")
    @Enumerated(EnumType.STRING)
    private EstadoProyecto estadoProyecto;
}
