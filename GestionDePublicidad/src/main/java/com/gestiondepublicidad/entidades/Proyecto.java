package com.gestiondepublicidad.entidades;

import com.gestiondepublicidad.enumeraciones.Estado;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

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

    private boolean proyectoActivo;
    
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tarea> Tarea;

    @ManyToMany
    private List<Usuario> usuario;

    @Enumerated(EnumType.STRING)
    private Estado estadoProyecto;
}
