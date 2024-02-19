package com.metene.expediente.controller;

import com.metene.expediente.dominio.Expediente;
import com.metene.expediente.service.dto.ExpedienteDto;
import com.metene.expediente.service.dto.ExpedienteMapper;
import com.metene.expediente.service.repository.ExpedienteRepository;
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
@RequestMapping("/expediente")
public class ExpedienteController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExpedienteController.class);
    private final ExpedienteRepository repository;
    private final ExpedienteMapper mapper;

    public ExpedienteController(ExpedienteRepository respository, ExpedienteMapper mapper) {
        this.repository = respository;
        this.mapper = mapper;
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Expediente>> getAll() {
        List<Expediente> expedientes = new ArrayList<>();
        try {
            expedientes = repository.findAll();
        } catch (DataException e) {
            LOGGER.error("ExpedienteController::getAll: Error al recuperar los datos en la BD: {} ", e.getMessage());
        } catch (JDBCConnectionException e) {
            LOGGER.error("ExpedienteController::getAll: Error al conecetar con la BD: {} ", e.getMessage());
        }

        return expedientes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(expedientes);
    }

    @PostMapping("/nuevo")
    public ResponseEntity<ExpedienteDto> crearExpediente(@RequestBody ExpedienteDto dto) {
        ExpedienteDto dtoToReturn = null;
        try {
            Expediente expediente = repository.save(mapper.toExpediente(dto));
            dtoToReturn = mapper.toDto(expediente);
        } catch (Exception e) {
            LOGGER.error("ExpedienteController::crearExpediente: Error al crear el expediente: {} ", e.getMessage());
        }

        return new ResponseEntity<>(dtoToReturn, HttpStatus.CREATED);
    }

    @GetMapping("/consultar/{id}")
    public ResponseEntity<Expediente> consultarExpediente(@PathVariable long id) {
        return repository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ExpedienteDto> actualizarExpediente(@PathVariable long id, @RequestBody ExpedienteDto dto) {
        ExpedienteDto myDto = null;
        try {
            Optional<Expediente> expediente = repository.findById(id);

            if (expediente.isPresent()) {
                // Actualizamos el expediente con los nuevos datos recibidos
                Expediente expedienteToSave = mapper.toExpediente(dto);
                expedienteToSave.setId(id);
                myDto = mapper.toDto(repository.save(expedienteToSave));
            }
        } catch (Exception e) {
            LOGGER.error("ExpedienteController::actualizarExpediente: Error al actualizar el expediente: {} ", e.getMessage());
        }

        return ResponseEntity.ok(myDto);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<ExpedienteDto> eliminarExpediente(@PathVariable long id) {

        try {
            Optional<Expediente> expediente = repository.findById(id);
            expediente.ifPresent(repository::delete);
        } catch (Exception e) {
            LOGGER.error("ExpedienteController::eliminarExpediente: Error al eliminar el expediente: {} ", e.getMessage());
        }

        return ResponseEntity.ok().build();
    }
}
