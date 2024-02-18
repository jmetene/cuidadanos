package com.metene.cuidadano.controller;

import com.metene.cuidadano.dominio.Cuidadano;
import com.metene.cuidadano.service.dto.CuidadanoDto;
import com.metene.cuidadano.service.dto.CuidadanoMapper;
import com.metene.cuidadano.service.repository.CuidadanoRespository;
import org.hibernate.exception.DataException;
import org.hibernate.exception.JDBCConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cuidadano")
public class CuidadanoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CuidadanoController.class);

    private final CuidadanoRespository repository;
    private final CuidadanoMapper mapper;

    public CuidadanoController(CuidadanoRespository repository, CuidadanoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Cuidadano>> getAll() {
        LOGGER.debug("CuidadanoController::findAll: Inicio");
        List<Cuidadano> cuidadanos = new ArrayList<>();
        try {
            cuidadanos = repository.findAll();
        } catch (DataException e) {
            LOGGER.error("CuidadanoController::getAll: Error al recuperar los datos en la BD: {} ", e.getMessage());
        } catch (JDBCConnectionException e) {
            LOGGER.error("CuidadanoController::getAll: Error al conecetar con la BD: {} ", e.getMessage());
        }
        LOGGER.debug("CuidadanoController::findAll: Fin");
        return cuidadanos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(cuidadanos);
    }

    @GetMapping("/consultarCuidadano/{id}")
    public ResponseEntity<Cuidadano> consultarCuidadano(@PathVariable long id) {
        return repository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/altaCuidadano")
    public ResponseEntity<CuidadanoDto> altaCuidadano(@RequestBody CuidadanoDto dto) {
        CuidadanoDto dtoToReturn = null;
        try {
            Cuidadano cuidadano = repository.save(mapper.toCuidadano(dto));
            dtoToReturn = mapper.toCuidadanoDto(cuidadano);
        } catch (Exception e) {
            LOGGER.error("CuidadanoController::altaCuidadano: Error al dar de alta al cuidadno: {} ", e.getMessage());
        }

        return new ResponseEntity<>(dtoToReturn, HttpStatus.CREATED);
    }

    @PutMapping("/modificarCuidadano/{id}")
    public ResponseEntity<CuidadanoDto> updateCuidadano(@PathVariable long id, @RequestBody CuidadanoDto dto) {
        CuidadanoDto myDto = null;
        try {
            Optional<Cuidadano> cuidadano = repository.findById(id);

            if (cuidadano.isPresent()) {
                // Actualizamos el cuidadano con los nuevos datos recibidos
                Cuidadano cuidadanoToSave = mapper.toCuidadano(dto);
                cuidadanoToSave.setCuidadanoId(id);
                myDto = mapper.toCuidadanoDto(repository.save(cuidadanoToSave));
            }
        } catch (Exception e) {
            LOGGER.error("CuidadanoController::updateCuidadano: Error al actualizar los datos del cuidadno: {} ", e.getMessage());
        }

        return ResponseEntity.ok(myDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CuidadanoDto> deleteCuidadano(@PathVariable long id) {

        try {
            Optional<Cuidadano> customer = repository.findById(id);
            customer.ifPresent(repository::delete);
        } catch (Exception e) {
            LOGGER.error("CuidadanoController::deleteCuidadano: Error al eliminar los datos del cuidadno: {} ", e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

}
