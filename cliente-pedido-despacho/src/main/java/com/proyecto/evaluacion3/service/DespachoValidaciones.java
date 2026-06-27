package com.proyecto.evaluacion3.service;

import com.proyecto.evaluacion3.DTO.DespachoDTO;
import com.proyecto.evaluacion3.DTO.PickingDTO;
import com.proyecto.evaluacion3.model.Despacho;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class DespachoValidaciones {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public Boolean validarNullVacio(Despacho despacho) {
        if (despacho.getDireccion() == null || despacho.getDireccion().trim().length() == 0) {
            return false;
        }
        if (despacho.getEstado() == null || despacho.getEstado().trim().length() == 0) {
            return false;
        }
        return true;
    }

    public PickingDTO obtenerPicking(Integer id) {
        PickingDTO pickingRecuperado = new PickingDTO();
        try {
            PickingDTO resultado = webClientBuilder.build()
                    .get()
                    .uri("http://evaluacion3/api/v1/pickings/buscar/" + id)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.empty())
                    .bodyToMono(PickingDTO.class)
                    .block();
            if (resultado != null) {
                return resultado;
            }
            return pickingRecuperado;
        } catch (Exception e) {
            return pickingRecuperado;
        }
    }

    public DespachoDTO convertirADTO(Despacho despacho) {
        DespachoDTO dto = new DespachoDTO();
        dto.setId(despacho.getId());
        dto.setDireccion(despacho.getDireccion());
        dto.setEstado(despacho.getEstado());
        dto.setPicking(obtenerPicking(despacho.getIdPicking()));
        return dto;
    }
}
