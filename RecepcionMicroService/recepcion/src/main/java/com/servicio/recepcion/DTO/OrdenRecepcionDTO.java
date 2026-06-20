package com.servicio.recepcion.DTO;

import java.time.LocalDate;
import java.util.List;
import com.servicio.recepcion.model.DetalleRecepcion;
import com.servicio.recepcion.model.Proveedor;
import lombok.Data;

@Data
public class OrdenRecepcionDTO {
    private LocalDate fechaRecepcion;
    private Proveedor provedor;
    private List<DetalleRecepcion> detalles;

}