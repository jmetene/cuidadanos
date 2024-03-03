package com.metene.expediente.service.ciudadanos;

import com.metene.expediente.dto.CiudadanoDto;

public interface CiudadanoService {
    /**
     * Método para dar de alta a un cuidadano a partir de un expediente
     *
     * @param bean Objeto con la información del ciudadano que se va a dar de alta
     * @return Long el id del usuario que se ha dado de alta
     */
    Long altaCiudadanoByExpediente(CiudadanoDto bean);

    /**
     * Comprueba si ya existe un ciudadano en el sistema con este DNI
     * @param dniNie DNI o NIE del ciudadano a consultar
     * @return true si existe o false si no está en el sistema.
     */
    Boolean checkIfCiudadanoExists(String dniNie);
}
