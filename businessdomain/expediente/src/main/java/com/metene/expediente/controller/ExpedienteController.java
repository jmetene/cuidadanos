package com.metene.expediente.controller;

import com.metene.expediente.dominio.Expediente;
import com.metene.expediente.dto.ExpedienteRequestDto;
import com.metene.expediente.dto.ExpedienteResponseDto;
import com.metene.expediente.dto.mapper.ExpedienteMapper;
import com.metene.expediente.repository.ExpedienteRepository;
import com.metene.expediente.service.ciudadanos.CiudadanoService;
import org.hibernate.exception.DataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/expedientes")
public class ExpedienteController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExpedienteController.class);
    private static final String EXPEDIENTE_EXISTENTE = "Ya existe un expediente con estos datos";
    private static final String MENSAJE_ERROR_CREAR = "ExpedienteController::crearExpediente: Error: {} ";
    private final ExpedienteRepository repository;
    private final CiudadanoService ciudadanoService;
    private final ExpedienteMapper mapper;

    public ExpedienteController(ExpedienteRepository respository,
                                ExpedienteMapper mapper,
                                CiudadanoService ciudadanoService) {
        this.repository = respository;
        this.mapper = mapper;
        this.ciudadanoService = ciudadanoService;
    }

    /**
     * Endpoint de consulta de todos los expedientes
     * @return Listado de expedientes
     */
    @GetMapping
    public ResponseEntity<List<ExpedienteResponseDto>> getAll() {
        List<Expediente> expedientes;
        List<ExpedienteResponseDto> expedienteResponseDtos;
        try {
            expedientes = repository.findAll();
            if (expedientes.isEmpty()) return ResponseEntity.noContent().build();
            expedienteResponseDtos = expedientes
                    .stream()
                    .map(mapper::toExpedienteResponse)
                    .collect(Collectors.toList());
        } catch (DataException e) {
            LOGGER.error("ExpedienteController::getAll: Error {} ", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(expedienteResponseDtos);
    }

    /**
     * Endpoint para dar de alta a un expediente y a un ciudadano
     * @param expedienteInfo Objeto con la información del expediente
     * @return ExpedienteResponse
     */
    @PostMapping("/alta")
    public ResponseEntity<?> altaExpediente(@Valid @RequestBody ExpedienteRequestDto expedienteInfo) {
        Expediente expediente;
        String dni = expedienteInfo.getDniNie();
        int tipoPrestacion = expedienteInfo.getTipoPrestacion();

        Optional<Expediente> expedienteEncontrado;
        try {
            expedienteEncontrado = repository
                    .findByDniNieEqualsIgnoreCaseAndTipoPrestacionEquals(dni, tipoPrestacion);
            // Comprobamos que no exista ya un expediente con estos datos.
            if (Objects.requireNonNull(expedienteEncontrado).isPresent())
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(EXPEDIENTE_EXISTENTE);
        } catch (Exception e) {
            LOGGER.error(MENSAJE_ERROR_CREAR, e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

        try {
            expediente = repository.save(mapper.toExpediente(expedienteInfo));
        } catch (Exception e) {
            LOGGER.error(MENSAJE_ERROR_CREAR, e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

        try {
            altaCiudadano(expedienteInfo);
        } catch (Exception e) {
            LOGGER.error(MENSAJE_ERROR_CREAR, e.getMessage());
        }

        return new ResponseEntity<>(expediente, HttpStatus.CREATED);
    }

    /**
     * Endpoint para consultar un expediente por su id
     * @param id identificador del expediente
     * @return ExpedienteResponseDto
     */
    @GetMapping("/consultar/{id}")
    public ResponseEntity<ExpedienteResponseDto> consultarExpediente(@PathVariable long id) {
        ExpedienteResponseDto responseDto;
        try {
            Optional<Expediente> expediente = repository.findById(id);
            if (expediente.isPresent()) {
                responseDto = mapper.toExpedienteResponse(expediente.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            LOGGER.error("ExpedienteController::actualizarExpediente: Error: {} ", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(responseDto);
    }

    /**
     * Endpint para actualizar un expediente mediante su id
     * @param id Identificador del expediente
     * @param dto información a actualizar
     * @return ExpedienteResponseDto
     */
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ExpedienteResponseDto> actualizarExpediente(@PathVariable long id, @RequestBody ExpedienteRequestDto dto) {
        ExpedienteResponseDto respose;
        try {
            Optional<Expediente> expediente = repository.findById(id);

            if (expediente.isPresent()) {
                // Actualizamos el expediente con los nuevos datos recibidos
                Expediente expedienteToSave = mapper.toExpediente(dto);
                expedienteToSave.setId(id);
                respose = mapper.toExpedienteResponse(repository.save(expedienteToSave));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            LOGGER.error("ExpedienteController::actualizarExpediente: Error: {} ", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(respose);
    }

    /**
     * Endpoint para eliminar un expediente por su id
     * @param id identificador del expediente
     * @return Expediente
     */
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<ExpedienteRequestDto> eliminarExpediente(@PathVariable long id) {

        try {
            Optional<Expediente> expedienteOptional = repository.findById(id);

            if (expedienteOptional.isPresent()) {
                Expediente expediente = expedienteOptional.get();
                repository.delete(expediente);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            LOGGER.error("ExpedienteController::eliminarExpediente: Error: {} ", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint para buscar los expedientes por el dni o nie del ciudadano
     * @param dniNie dni o nie del ciudadano
     * @return Lista de expedientes que cumplen con el criterio de búsqueda
     */
    @GetMapping("/buscarPorDniNie/{dniNie}")
    public ResponseEntity<List<ExpedienteResponseDto>> buscarPorDni(@PathVariable @Valid String dniNie/*, BindingResult result*/) {
        List<Expediente> expedientes;
        List<ExpedienteResponseDto> responseDtoList;
        try {
            expedientes = repository.findByDniNieEqualsIgnoreCase(dniNie);
            responseDtoList = expedientes
                    .stream()
                    .map(mapper::toExpedienteResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOGGER.error("ExpedienteController::buscarPorDni: Error: {} ", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

        return expedientes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(responseDtoList);
    }

    /**
     * Enpoint para buscar los expedientes por tipo de presatación
     * @param tipoPrestacion tipo de prestación
     * @return Listado de expedientes que cumplen con el criterio de búsqueda
     */
    @GetMapping("/buscarPorTipoPrestacion /{tipoPrestacion}")
    public ResponseEntity<List<Expediente>> buscarPorTipoPrestacion(@PathVariable int tipoPrestacion) {
        List<Expediente> expedientes = null;
        try {
            expedientes = repository.findByTipoPrestacionEquals(tipoPrestacion);
        } catch (Exception e) {
            LOGGER.error("ExpedienteController::buscarPorTipoPrestacion: Error: {} ", e.getMessage());
        }

        if (Objects.requireNonNull(expedientes).isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(expedientes);
    }

    /**
     * Método para dar de alta a un ciudanano mediante su expediente
     * @param expedienteInfo Objeto con los datos del ciudadano
     */
    private void altaCiudadano(ExpedienteRequestDto expedienteInfo) {
        Boolean ciudadanoExists = ciudadanoService.checkIfCiudadanoExists(expedienteInfo.getDniNie());
        // Creamos el ciudadano si no existe
        if (Boolean.FALSE.equals(ciudadanoExists))
            ciudadanoService.altaCiudadanoByExpediente(mapper.toCuidadanoDto(expedienteInfo));
    }
}