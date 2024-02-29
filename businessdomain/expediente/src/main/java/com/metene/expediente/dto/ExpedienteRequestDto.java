package com.metene.expediente.dto;

import lombok.Data;

@Data
public class ExpedienteRequestDto {
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String dniNie;
    private String email;
    private int tipoPrestacion;
    private String notas;
}
