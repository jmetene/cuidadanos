package com.metene.expediente.service.repository;

import com.metene.expediente.dominio.Expediente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpedienteRespository extends JpaRepository<Expediente, Long> {
}
