package com.metene.cuidadano.dto;

import com.metene.cuidadano.dominio.Ciudadano;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CiudadanoMapperImpl implements CiudadanoMapper {
    @Override
    public Ciudadano toCuidadano(CiudadanoDto dto) {
        if (Objects.isNull(dto)) return null;

        Ciudadano ciudadano = new Ciudadano();

        ciudadano.setNombre(dto.getNombre());
        ciudadano.setApellido1(dto.getApellido1());
        ciudadano.setApellido2(dto.getApellido2());
        ciudadano.setEmail(dto.getEmail());
        ciudadano.setDocumento(dto.getDocumento());

        return ciudadano;
    }

    @Override
    public CiudadanoDto toCuidadanoDto(Ciudadano cuidadano) {
        if (Objects.isNull(cuidadano)) return null;

        CiudadanoDto dto = new CiudadanoDto();

        dto.setNombre(cuidadano.getNombre());
        dto.setApellido1(cuidadano.getApellido1());
        dto.setApellido2(cuidadano.getApellido2());
        dto.setEmail(cuidadano.getEmail());
        dto.setDocumento(cuidadano.getDocumento());

        return dto;
    }
}
