package com.metene.expediente.dto.mapper.impl;

import com.metene.expediente.dominio.Expediente;
import com.metene.expediente.dto.CiudadanoDto;
import com.metene.expediente.dto.ExpedienteRequestDto;
import com.metene.expediente.dto.ExpedienteResponseDto;
import com.metene.expediente.dto.mapper.ExpedienteMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class ExpedienteMapperImpl implements ExpedienteMapper {
    @Override
    public Expediente toExpediente(ExpedienteRequestDto dto) {
        if (Objects.isNull(dto)) return null;

        Expediente expediente = new Expediente();
        expediente.setTipoPrestacion(dto.getTipoPrestacion());
        expediente.setNotas(dto.getNotas());
        expediente.setCreateAt(LocalDateTime.now());
        expediente.setDniNie(dto.getDniNie());
        return expediente;
    }

    @Override
    public ExpedienteResponseDto toExpedienteResponse(Expediente expediente) {
        if (Objects.isNull(expediente)) return null;

        ExpedienteResponseDto dto = new ExpedienteResponseDto();
        dto.setTipo(expediente.getTipoPrestacion());
        dto.setNotas(expediente.getNotas());
        dto.setDniNie(expediente.getDniNie());

        return dto;
    }

    @Override
    public CiudadanoDto toCuidadanoDto(ExpedienteRequestDto dto) {
        if (Objects.isNull(dto)) return null;

        CiudadanoDto cuidadanoDto = new CiudadanoDto();

        cuidadanoDto.setNombre(dto.getNombre());
        cuidadanoDto.setApellido1(dto.getApellido1());
        cuidadanoDto.setApellido2(dto.getApellido2());
        cuidadanoDto.setDocumento(dto.getDniNie());
        cuidadanoDto.setEmail(dto.getEmail());

        return cuidadanoDto;
    }
}
