package com.item_productos_ubicacion.item_productos_ubicacion.DTO;

import lombok.Data;

@Data
public class ItemPedidoDTO {
    private Integer id;
    private Integer cantidad;
    private Integer pedido_id;
    private ProductoDTO producto;
}
