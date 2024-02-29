package com.metene.cuidadano.repository;

import com.metene.cuidadano.dominio.Ciudadano;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CiudadanoRespository extends JpaRepository<Ciudadano, Long> {
   Optional<Ciudadano> findByDocumentoIgnoreCase(String documento);
}
