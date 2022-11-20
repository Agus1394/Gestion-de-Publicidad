package com.gestiondepublicidad.entidades;

import com.gestiondepublicidad.enumeraciones.EstadoProyecto;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
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
@Table(name = "TO DO LIST")
public class ListaDeTarea {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id_tarea;

    private Long contactoInterno;
    private Long contactoCliente;
    private String notas;
    
    @Temporal(TemporalType.DATE)
    private Calendar eventoComun;
    
    @Temporal(TemporalType.DATE)
    private Calendar eventoPrivado;
    
    @OneToOne
    private Usuario usuario;
    
    @Enumerated(EnumType.STRING)
    private EstadoProyecto estadoProyecto;
}
