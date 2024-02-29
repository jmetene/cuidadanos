package com.metene.expediente.dto;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class ExpedienteRequestDto {
    @NotBlank(message = "Este campo es obligatorio")
    @Size(min = 3, max = 20)
    private String nombre;
    @NotBlank(message = "Este campo es obligatorio")
    @Size(min = 3, max = 30)
    private String apellido1;
    @NotBlank(message = "Este campo es obligatorio")
    @Size(min = 3, max = 30)
    private String apellido2;
    @Pattern(regexp = "^([XYZ])?[0-9]{7,8}[A-Z]$")
    private String dniNie;
    @NotBlank(message = "Este campo es obligatorio")
    @Size(max = 40)
    @Email
    private String email;
    @NotNull(message = "Este campo es obligatorio")
    @Min(1)
    @Max(3)
    private int tipoPrestacion;
    @NotBlank(message = "Este campo es obligatorio")
    @Size(max = 80)
    private String notas;
}
