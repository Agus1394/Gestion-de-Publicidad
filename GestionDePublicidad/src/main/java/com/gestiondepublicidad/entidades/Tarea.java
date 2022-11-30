package com.gestiondepublicidad.entidades;

import com.gestiondepublicidad.enumeraciones.Estado;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Tarea {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id_tarea;

    private String nombreTarea;

    private String descripcion;

    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;

    @Temporal(TemporalType.DATE)
    private Date fechaAsignacion;

    @Enumerated(EnumType.STRING)
    private Estado estadoTarea;

    @ManyToOne
    private Usuario usuario;
}
