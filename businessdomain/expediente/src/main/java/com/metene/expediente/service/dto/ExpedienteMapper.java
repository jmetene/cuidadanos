package com.metene.expediente.service.dto;

import com.metene.expediente.dominio.Expediente;

public interface ExpedienteMapper {
    Expediente toExpediente(ExpedienteDto dto);
    ExpedienteDto toDto(Expediente expediente);
}
