package com.metene.expediente.dominio;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class Expediente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int tipoPrestacion;
    private String notas;
    private LocalDateTime createAt;
    /**
     * Representa el DNI o NIE del ciudadano
     */
    private String dniNie;
}
