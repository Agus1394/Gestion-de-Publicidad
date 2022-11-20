package com.gestiondepublicidad.entidades;

import com.gestiondepublicidad.enumeraciones.EstadoProyecto;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
    @Column(name = "ID PROYECTO")
    private String id_proyecto;
    
//    @Column(name = "NOMBRE")
    private String nombre;
    
//    @Column (name = "DESCRIPCION")
    private String descripcion;

//    @Column (name = "FECHA INICIO")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    
//    @Column(name = "FECHA FIN")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    
    @ManyToOne
    private Usuario usuario;
    
    @OneToMany
    private List<ListaDeTarea> listaDeTarea;


//    @Column (name = "ESTADO DEL PROYECTO")
    @Enumerated(EnumType.STRING)
    private EstadoProyecto estadoProyecto;
}
