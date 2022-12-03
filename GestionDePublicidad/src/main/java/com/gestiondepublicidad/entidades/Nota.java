package com.gestiondepublicidad.entidades;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Nota {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id_nota;

    private String titulo;

    private String descripcion;

    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;

    @ManyToOne
    Usuario usuario;
}
