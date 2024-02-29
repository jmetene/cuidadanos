package com.metene.cuidadano.dto;

import com.metene.cuidadano.dominio.Ciudadano;

public interface CiudadanoMapper {

    /**
     * Convierte un objeto CiudadanoDto en un objeto Cuidadano.
     *
     * @param dto el objeto CiudadanoDto a convertir
     * @return el objeto Cuidadano convertido
     */
    Ciudadano toCuidadano(CiudadanoDto dto);
    /**
     * Convierte un objeto Cuidadano en un objeto CiudadanoDto.
     *
     * @param cuidadano el objeto CiudadanoDto a convertir
     * @return el objeto CiudadanoDto convertido
     */
    CiudadanoDto toCuidadanoDto(Ciudadano cuidadano);
}
