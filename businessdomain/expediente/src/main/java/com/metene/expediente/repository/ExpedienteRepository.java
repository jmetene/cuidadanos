package com.metene.expediente.repository;

import com.metene.expediente.dominio.Expediente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExpedienteRepository extends JpaRepository<Expediente, Long> {

    /**
     * Devuelve un expediente por el tipo de prestación y por el dni del ciudadano
     * @param dniNie Documento del ciudadano
     * @param tipo tipo de prestación
     * @return Expediente
     */
    Optional<Expediente> findByDniNieEqualsIgnoreCaseAndTipoPrestacionEquals(String dniNie, int tipo);

    /**
     * Devuelve el listado de expedientes asociados a un ciudadano
     * @param dniNie Documento del ciudadano
     * @return Lista de expedientes
     */
    List<Expediente> findByDniNieEqualsIgnoreCase(String dniNie);

    /**
     * Filtra los expedientes por el tipo de prestación
     * @param tipo tipo de prestación
     * @return Listado de expedientes
     */
    List<Expediente> findByTipoPrestacionEquals(int tipo);
}
