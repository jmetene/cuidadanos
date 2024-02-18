package com.metene.cuidadano.service.dto;

import com.metene.cuidadano.dominio.Cuidadano;

public interface CuidadanoMapper {

    /**
     * Convierte un objeto CuidadanoDto en un objeto Cuidadano.
     *
     * @param dto el objeto CuidadanoDto a convertir
     * @return el objeto Cuidadano convertido
     */
    Cuidadano toCuidadano(CuidadanoDto dto);
    /**
     * Convierte un objeto Cuidadano en un objeto CuidadanoDto.
     *
     * @param cuidadano el objeto CuidadanoDto a convertir
     * @return el objeto CuidadanoDto convertido
     */
    CuidadanoDto toCuidadanoDto(Cuidadano cuidadano);
}
