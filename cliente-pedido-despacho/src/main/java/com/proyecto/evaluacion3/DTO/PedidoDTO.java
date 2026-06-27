package com.proyecto.evaluacion3.DTO;

import lombok.Data;

@Data
public class PedidoDTO {
    private Integer id;
    private String estado;
    private ClienteDTO cliente;
}
