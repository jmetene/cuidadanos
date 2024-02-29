package com.metene.expediente.dto.mapper;

import com.metene.expediente.dominio.Expediente;
import com.metene.expediente.dto.CiudadanoDto;
import com.metene.expediente.dto.ExpedienteRequestDto;
import com.metene.expediente.dto.ExpedienteResponseDto;

public interface ExpedienteMapper {
    /**
     * Convierte un ExpedienteRequestDto a objeto de tipo Expediente
     * @param dto Expediente Dto
     * @return Expediente
     */
    Expediente toExpediente(ExpedienteRequestDto dto);

    /**
     * Convierte un objeto de tipo Expediente a ExpedienteRequestDto
     * @param expediente Expediente
     * @return ExpedienteRequestDto
     */
    ExpedienteResponseDto toExpedienteResponse(Expediente expediente);

    /**
     * Extraer los campos pertenecientes a un Cuidadano del Expediente
     * @param dto Expediente con los datos del cuidadano
     * @return CuidadanoDto
     */
    CiudadanoDto toCuidadanoDto(ExpedienteRequestDto dto);
}
