package com.item_productos_ubicacion.item_productos_ubicacion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

public class ItemPedidoValidaciones {
    @Autowired
    private WebClient.Builder webClientBuilder;

    public Integer obtenerPedidoIdExterno(Integer idItem){
        if(idItem == null || idItem < 0){
            return null;
        }
        try {
            return webClientBuilder.build()
                    .get()
                    .uri("lb://cliente-service/api/v1/pedidos" + idItem)
                    .retrieve()
                    .bodyToMono(Integer.class)
                    .block();

        } catch (Exception e) {
            return null;

        }
    }
    

}
