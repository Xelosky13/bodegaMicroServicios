package com.item_productos_ubicacion.item_productos_ubicacion.DTO;

import java.util.List;

import lombok.Data;

@Data
public class UbicacionDTO {
    private Integer id;
    private Integer pasillo;
    private Integer estante;
    private String descripcion;
    private List<ProductoDTO> productos;
}
