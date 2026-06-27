package com.proyecto.evaluacion3.DTO;

import lombok.Data;

@Data
public class DespachoDTO {
    private Integer id;
    private String direccion;
    private String estado;
    private PickingDTO picking;
}
