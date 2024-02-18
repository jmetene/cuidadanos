package com.metene.cuidadano.service.repository;

import com.metene.cuidadano.dominio.Cuidadano;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuidadanoRespository extends JpaRepository<Cuidadano, Long> {
}
