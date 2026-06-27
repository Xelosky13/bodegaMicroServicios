package com.proyecto.evaluacion3.DTO;

import lombok.Data;

@Data
public class PickingDTO {
    private Integer id;
    private String estado;
    private PedidoDTO pedido;
    private OperarioDTO operario;
}
