package com.metene.expediente.service.dto;

import com.metene.expediente.dominio.Expediente;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class ExpedienteMapperImpl implements ExpedienteMapper {
    @Override
    public Expediente toExpediente(ExpedienteDto dto) {
        if (Objects.isNull(dto)) return null;

        Expediente expediente = new Expediente();
        expediente.setTipoPrestacion(dto.getTipoPrestacion());
        expediente.setNotas(dto.getNotas());
        expediente.setCreateAt(LocalDateTime.now());
        return expediente;
    }

    @Override
    public ExpedienteDto toDto(Expediente expediente) {
        if (Objects.isNull(expediente)) return null;

        ExpedienteDto dto = new ExpedienteDto();
        dto.setTipoPrestacion(expediente.getTipoPrestacion());
        dto.setNotas(expediente.getNotas());

        return dto;
    }
}
