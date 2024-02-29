package com.metene.expediente.service.webclient;

import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

/**
 * Interfaz para conectar el microservicio de cuidadano
 */
public interface WebClientService {
    /**
     * Consulta el microservicio ciudadano para recuperar la información del cuidadano
     * @param basePath Endpoint de consulta
     * @param pathSegments segmentos del path
     * @param responseType Objeto de tipo ciudadano
     * @return  Entidad ciudadano
     * @param <T> Tipo genérico
     */
    <T> Mono<T> get(String basePath, Class<T> responseType, String...pathSegments);
    /**
     * Método para guardar un cliente desde el microservicio expediente
     * @param basePath Endpoint de consulta
     * @param queryParams parámetros de consulta
     * @param responseType Objeto de tipo ciudadano con los datos de éste.
     * @param paramsBean Objeto con los datos del cliente
     * @return Cuidadano
     * @param <T> Tipo genérico
     */
    <T> Mono<T> post(String basePath, MultiValueMap<String, String> queryParams, Class<T> responseType, Object paramsBean);
}
