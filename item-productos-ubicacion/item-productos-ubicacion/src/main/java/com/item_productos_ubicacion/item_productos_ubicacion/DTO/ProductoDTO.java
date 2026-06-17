package com.item_productos_ubicacion.item_productos_ubicacion.DTO;

import lombok.Data;

@Data
public class ProductoDTO {
    private Integer id;
    private String nombre;
    private String sku;
    private Integer stockActual;
    private UbicacionDTO ubicacion;
}
