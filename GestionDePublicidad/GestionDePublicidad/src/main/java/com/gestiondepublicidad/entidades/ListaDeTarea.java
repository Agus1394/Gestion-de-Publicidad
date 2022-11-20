package com.gestiondepublicidad.entidades;

import com.gestiondepublicidad.enumeraciones.EstadoProyecto;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
public class ListaDeTarea {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id_tareas;
    
    private String nombre;
    
    private String descripcion;
    
    @ManyToOne
    private Proyecto proyecto;
    
    @Enumerated(EnumType.STRING)
    private EstadoProyecto estadoTareas;
}
