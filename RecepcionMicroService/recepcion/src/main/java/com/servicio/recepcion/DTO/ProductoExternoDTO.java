package com.servicio.recepcion.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoExternoDTO {
    private Integer id;
    private String nombre;
    private String sku;
    private Integer stockActual;
    private UbicacionDTO ubicacion;
}
