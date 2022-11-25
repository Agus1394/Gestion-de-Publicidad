package com.gestiondepublicidad.entidades;

<<<<<<< HEAD
import com.gestiondepublicidad.enumeraciones.EstadoProyecto;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
=======
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
>>>>>>> 77a026393b302b52e29671e406979bf8d2d434b9
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

<<<<<<< HEAD

=======
>>>>>>> 77a026393b302b52e29671e406979bf8d2d434b9
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
<<<<<<< HEAD
=======
@Table(name = "ListaDeTarea")
>>>>>>> 77a026393b302b52e29671e406979bf8d2d434b9
public class ListaDeTarea {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String titulo;
    private String notas;
    
 

    @Temporal(TemporalType.DATE)
    private Date eventoComun;

    @Temporal(TemporalType.DATE)
    private Date eventoPrivado;
    
<<<<<<< HEAD
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
=======
    @OneToOne
    private Usuario usuario;
<<<<<<< HEAD
>>>>>>> 77a026393b302b52e29671e406979bf8d2d434b9
=======
    
>>>>>>> d10657d19c2cc5a7eaa247f713fc24eb4d45ce3b
}
