package com.gestiondepublicidad.entidades;

import com.gestiondepublicidad.enumeraciones.PuestoEmpresa;
import com.gestiondepublicidad.enumeraciones.Rol;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "USUARIOS")
public class Usuario {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "ID_USUARIO")
    private String id_usuario;

    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name= "CORREO")
    private String email;
    @Column(name = "CONTRASEÑA")
    private String contrasenia;

    @OneToOne
    private Foto foto;

    @OneToMany
    private List<Proyecto> proyecto;

    @OneToMany
    private List<Calendario> calendario;

    @Column(name = "ROL")
    @Enumerated(EnumType.STRING)
    private Rol rol;

    @Column(name = "PUESTO EN LA EMPRESA")
    @Enumerated(EnumType.STRING)
    private PuestoEmpresa puestoEmpresa;

}
