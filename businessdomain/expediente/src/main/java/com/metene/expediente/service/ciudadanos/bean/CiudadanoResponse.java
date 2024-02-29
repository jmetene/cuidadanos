package com.metene.expediente.service.ciudadanos.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CiudadanoResponse {
    private long ciudadanoId;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String documento;
    private String email;
}
