package com.gestiondepublicidad.entidades;

import com.gestiondepublicidad.enumeraciones.Estado;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
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
@Table(name = "Proyecto")
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
    
    @OneToMany
    private List<Tarea> Tarea;

//   COMO SON MUCHOS A MUCHOS; TAMBIÉN USUARIO SE ANOTA COMO LIST
    @ManyToMany
    private List<Usuario> usuario;

    @Enumerated(EnumType.STRING)
    private Estado estadoProyecto;
}
