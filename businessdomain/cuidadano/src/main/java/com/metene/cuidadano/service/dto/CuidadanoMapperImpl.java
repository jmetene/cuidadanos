package com.metene.cuidadano.service.dto;

import com.metene.cuidadano.dominio.Cuidadano;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CuidadanoMapperImpl implements CuidadanoMapper {
    @Override
    public Cuidadano toCuidadano(CuidadanoDto dto) {
        if (Objects.isNull(dto)) return null;

        Cuidadano cuidadano = new Cuidadano();

        cuidadano.setNombre(dto.getNombre());
        cuidadano.setApellido1(dto.getApellido1());
        cuidadano.setApellido2(dto.getApellido2());
        cuidadano.setEmail(dto.getEmail());
        cuidadano.setDocumento(dto.getDniNie());

        return cuidadano;
    }

    @Override
    public CuidadanoDto toCuidadanoDto(Cuidadano cuidadano) {
        if (Objects.isNull(cuidadano)) return null;

        CuidadanoDto dto = new CuidadanoDto();

        dto.setNombre(cuidadano.getNombre());
        dto.setApellido1(cuidadano.getApellido1());
        dto.setApellido2(cuidadano.getApellido2());
        dto.setEmail(cuidadano.getEmail());
        dto.setDniNie(cuidadano.getDocumento());

        return dto;
    }
}
