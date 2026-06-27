package com.proyecto.evaluacion3.service;

import com.proyecto.evaluacion3.DTO.ClienteDTO;
import com.proyecto.evaluacion3.DTO.PedidoDTO;
import com.proyecto.evaluacion3.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PedidoValidaciones {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public Boolean validarNullVacio(Pedido pedido) {
        if (pedido.getEstado() == null || pedido.getEstado().trim().length() == 0) {
            return false;
        }
        return true;
    }

    public ClienteDTO obtenerCliente(Integer id) {
        ClienteDTO clienteRecuperado = new ClienteDTO();
        try {
            ClienteDTO resultado = webClientBuilder.build()
                    .get()
                    .uri("http://evaluacion3/api/v1/clientes/buscar/" + id)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.empty())
                    .bodyToMono(ClienteDTO.class)
                    .block();
            if (resultado != null) {
                return resultado;
            }
            return clienteRecuperado;
        } catch (Exception e) {
            return clienteRecuperado;
        }
    }

    public PedidoDTO convertirADTO(Pedido pedido) {
        PedidoDTO dto = new PedidoDTO();
        dto.setId(pedido.getId());
        dto.setEstado(pedido.getEstado());
        dto.setCliente(obtenerCliente(pedido.getIdCliente()));
        return dto;
    }
}
