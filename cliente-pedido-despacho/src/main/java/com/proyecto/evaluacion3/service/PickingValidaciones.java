package com.proyecto.evaluacion3.service;

import com.proyecto.evaluacion3.DTO.OperarioDTO;
import com.proyecto.evaluacion3.DTO.PedidoDTO;
import com.proyecto.evaluacion3.DTO.PickingDTO;
import com.proyecto.evaluacion3.model.Picking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PickingValidaciones {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public Boolean validarNullVacio(Picking picking) {
        if (picking.getEstado() == null || picking.getEstado().trim().length() == 0) {
            return false;
        }
        return true;
    }

    public PedidoDTO obtenerPedido(Integer id) {
        PedidoDTO pedidoRecuperado = new PedidoDTO();
        try {
            PedidoDTO resultado = webClientBuilder.build()
                    .get()
                    .uri("http://evaluacion3/api/v1/pedidos/buscar/" + id)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.empty())
                    .bodyToMono(PedidoDTO.class)
                    .block();
            if (resultado != null) {
                return resultado;
            }
            return pedidoRecuperado;
        } catch (Exception e) {
            return pedidoRecuperado;
        }
    }

    public OperarioDTO obtenerOperario(Integer id) {
        OperarioDTO operarioRecuperado = new OperarioDTO();
        try {
            OperarioDTO resultado = webClientBuilder.build()
                    .get()
                    .uri("http://evaluacion3/api/v1/operarios/buscar/" + id)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.empty())
                    .bodyToMono(OperarioDTO.class)
                    .block();
            if (resultado != null) {
                return resultado;
            }
            return operarioRecuperado;
        } catch (Exception e) {
            return operarioRecuperado;
        }
    }

    public PickingDTO convertirADTO(Picking picking) {
        PickingDTO dto = new PickingDTO();
        dto.setId(picking.getId());
        dto.setEstado(picking.getEstado());
        dto.setPedido(obtenerPedido(picking.getIdPedido()));
        dto.setOperario(obtenerOperario(picking.getIdOperario()));
        return dto;
    }
}
