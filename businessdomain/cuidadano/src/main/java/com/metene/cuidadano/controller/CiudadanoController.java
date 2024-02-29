package com.metene.cuidadano.controller;

import com.metene.cuidadano.dominio.Ciudadano;
import com.metene.cuidadano.dto.CiudadanoDto;
import com.metene.cuidadano.dto.CiudadanoMapper;
import com.metene.cuidadano.repository.CiudadanoRespository;
import org.hibernate.exception.DataException;
import org.hibernate.exception.JDBCConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/ciudadanos")
public class CiudadanoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CiudadanoController.class);

    private final CiudadanoRespository repository;
    private final CiudadanoMapper mapper;

    public CiudadanoController(CiudadanoRespository repository, CiudadanoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<Ciudadano>> getAll() {
        LOGGER.debug("CiudadanoController::findAll: Inicio");
        List<Ciudadano> ciudadanos = new ArrayList<>();
        try {
            ciudadanos = repository.findAll();
        } catch (DataException e) {
            LOGGER.error("CiudadanoController::getAll: Error al recuperar los datos en la BD: {} ", e.getMessage());
        } catch (JDBCConnectionException e) {
            LOGGER.error("CiudadanoController::getAll: Error al conecetar con la BD: {} ", e.getMessage());
        }
        LOGGER.debug("CiudadanoController::findAll: Fin");
        return ciudadanos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(ciudadanos);
    }

    @GetMapping("/consultarCuidadano/{id}")
    public ResponseEntity<Ciudadano> consultarCuidadano(@PathVariable Long id) {
        return repository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/consultarCiudadanoPorDniNie/{dniNie}")
    public ResponseEntity<Ciudadano> consultarPorDniNie(@PathVariable String dniNie) {
        return repository
                .findByDocumentoIgnoreCase(dniNie)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/altaCiudadano")
    public ResponseEntity<Long> altaCuidadano(@RequestBody CiudadanoDto dto) {
        Ciudadano cuidadano = null;
        try {
            cuidadano = repository.save(mapper.toCuidadano(dto));
        } catch (Exception e) {
            LOGGER.error("CiudadanoController::altaCuidadano: Error al dar de alta al cuidadno: {} ", e.getMessage());
        }

        return new ResponseEntity<>(Objects.requireNonNull(cuidadano).getCiudadanoId(), HttpStatus.CREATED);
    }

    @PutMapping("/modificarCiudadano/{id}")
    public ResponseEntity<CiudadanoDto> updateCuidadano(@PathVariable Long id, @RequestBody CiudadanoDto dto) {
        CiudadanoDto myDto = null;
        try {
            Optional<Ciudadano> cuidadano = repository.findById(id);

            if (cuidadano.isPresent()) {
                // Actualizamos el cuidadano con los nuevos datos recibidos
                Ciudadano cuidadanoToSave = mapper.toCuidadano(dto);
                cuidadanoToSave.setCiudadanoId(id);
                myDto = mapper.toCuidadanoDto(repository.save(cuidadanoToSave));
            }
        } catch (Exception e) {
            LOGGER.error("CiudadanoController::updateCuidadano: Error al actualizar los datos del cuidadno: {} ", e.getMessage());
        }

        return ResponseEntity.ok(myDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CiudadanoDto> deleteCuidadano(@PathVariable Long id) {

        try {
            Optional<Ciudadano> customer = repository.findById(id);
            customer.ifPresent(repository::delete);
        } catch (Exception e) {
            LOGGER.error("CiudadanoController::deleteCuidadano: Error al eliminar los datos del cuidadno: {} ", e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

}
