package com.gestiondepublicidad.entidades;

import java.util.Date;
import javax.persistence.Entity;
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
@Table(name = "ListaDeTarea")
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
    
    @OneToOne
    private Usuario usuario;
}
