package com.servicio.recepcion.DTO;

import java.util.List;

import lombok.Data;

@Data
public class UbicacionDTO {
    private Integer id;
    private Integer pasillo;
    private Integer estante;
    private String descripcion;
    private List<ProductoExternoDTO> productos;
}
