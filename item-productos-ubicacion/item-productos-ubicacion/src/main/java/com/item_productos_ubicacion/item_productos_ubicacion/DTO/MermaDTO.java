package com.item_productos_ubicacion.item_productos_ubicacion.DTO;

import java.time.LocalDate;

import lombok.Data;

@Data
public class MermaDTO {
    private Integer id;
    private LocalDate fechaReporte;
    private Integer cantidad;
    private String motivo;
    private ProductoDTO producto;
}
