package com.gestiondepublicidad.entidades;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
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
@Table(name = "Foto")
public class Foto {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id_foto;

    private String mime;
    private String nombre;

    @Lob
    @Basic(FetchType.LAZY)
    private byte[] contenido;

}
