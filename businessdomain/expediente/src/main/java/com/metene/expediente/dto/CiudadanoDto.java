package com.metene.expediente.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CiudadanoDto {
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String documento;
    private String email;
}
