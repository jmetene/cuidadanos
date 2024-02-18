package com.metene.cuidadano.dominio;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Cuidadano {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long cuidadanoId;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String documento;
    private String email;
}
