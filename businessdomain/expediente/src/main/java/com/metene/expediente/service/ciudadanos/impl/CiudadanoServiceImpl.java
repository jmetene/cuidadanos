package com.metene.expediente.service.ciudadanos.impl;

import com.metene.expediente.service.ciudadanos.bean.CiudadanoResponse;
import com.metene.expediente.service.ciudadanos.CiudadanoService;
import com.metene.expediente.dto.CiudadanoDto;
import com.metene.expediente.service.webclient.WebClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public class CiudadanoServiceImpl implements CiudadanoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CiudadanoServiceImpl.class);
    private final WebClientService clientService;
    private static final String ALTAPATH = "altaCiudadano";
    private static final String CONSULTAPATH = "consultarCiudadanoPorDniNie";

    @Autowired
    public CiudadanoServiceImpl(WebClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public Long altaCiudadanoByExpediente(CiudadanoDto dto) {
        Long response = null;
        try {
            Mono<Long> ciudadanoMono = clientService.post(ALTAPATH, null, Long.class, dto);
            response = ciudadanoMono.block();
        } catch (Exception e) {
            LOGGER.error("CiudadanoServiceImpl::altaCuidadanoByExpediente: error: {}", e.getMessage());
        }

        return Objects.requireNonNull(response);
    }

    @Override
    public Boolean checkIfCiudadanoExists(String dniNie) {
        CiudadanoResponse response = null;

        try {
            String [] pathSegments = {dniNie};
            Mono<CiudadanoResponse> ciudadanoMono = clientService.get(CONSULTAPATH, CiudadanoResponse.class, pathSegments);
            response = ciudadanoMono.block();
        } catch (Exception e) {
            LOGGER.error("CiudadanoServiceImpl::altaCuidadanoByExpediente: error: {}", e.getMessage());
        }

        return Objects.nonNull(response);
    }
}
