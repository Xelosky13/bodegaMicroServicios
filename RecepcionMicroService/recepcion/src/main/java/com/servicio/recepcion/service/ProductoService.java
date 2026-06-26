package com.servicio.recepcion.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.servicio.recepcion.DTO.ProductoExternoDTO;

import reactor.core.publisher.Mono;

@Service
public class ProductoService {

    private final WebClient webClient;

    public ProductoService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:8081").build();
    }

    public ProductoExternoDTO obtenerProducto(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID del producto debe ser mayor a 0");
        }

        return webClient.get()
                .uri("/api/v1/productos/{id}", id)
                .retrieve()
                .onStatus(
                        status -> status.value() == 404,
                        response -> Mono.error(
                                new RuntimeException("Producto no encontrado")))
                .onStatus(
                        status -> status.is5xxServerError(),
                        response -> Mono.error(
                                new RuntimeException("Error en el microservicio de productos")))

                .bodyToMono(ProductoExternoDTO.class)
                .block();
    }
}