/*

*/

package com.gestiondepublicidad.entidades;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
@Table(name = "CALENDARIO")
public class Calendario {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "ID_CALENDARIO")
    private String id_calendario;
    
    @Column(name = "DESCRIPCIÓN")
    private String descripcion;
    
    @Column(name = "EVENTO")
    private String evento;
    
}
