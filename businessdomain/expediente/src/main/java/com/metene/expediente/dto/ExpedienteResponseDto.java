package com.metene.expediente.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Expediente DTO de respuesta
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpedienteResponseDto {
    private Integer tipo;
    private String notas;
    private String dniNie;
}
