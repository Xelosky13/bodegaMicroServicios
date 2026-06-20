package com.servicio.recepcion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.servicio.recepcion.DTO.ProductoExternoDTO;

import reactor.core.publisher.Mono;

@Service
public class ProductoService {

    private final WebClient webClient;

    public ProductoService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:8082").build();
    }

    public ProductoExternoDTO obtenerProducto(Integer id) {

        // Validación local
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID del producto debe ser mayor a 0");
        }

        return webClient.get()
                .uri("/api/productos/{id}", id)
                .retrieve()

                // Error 404
                .onStatus(
                        status -> status.value() == 404,
                        response -> Mono.error(
                                new RuntimeException("Producto no encontrado")))

                // Error 500 u otros errores del servidor remoto
                .onStatus(
                        status -> status.is5xxServerError(),
                        response -> Mono.error(
                                new RuntimeException("Error en el microservicio de productos")))

                .bodyToMono(ProductoExternoDTO.class)
                .block();
    }
}

/*
 * private RestTemplate restTemplate;
 * 
 * public ProductoExternoDTO obtenerProducto(Integer id) {
 * try {
 * return restTemplate.getForObject(
 * "http://localhost:8082/api/v1/productos/" + id,
 * ProductoExternoDTO.class);
 * } catch (RestClientException e) {
 * throw new RuntimeException(
 * "No fue posible obtener el producto " + id, e);
 * }
 * }
 */