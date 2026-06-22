package com.servicio.recepcion.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.servicio.recepcion.DTO.DetalleRecepcionDTO;
import com.servicio.recepcion.service.DetalleRecepcionService;
import com.servicio.recepcion.service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Detalle Recepción", description = "Operaciones relacionadas con los detalles de recepción")
@RequestMapping("/api/v1/detallerecepcion")
public class DetalleRecepcionController {

    private DetalleRecepcionService service;

    public DetalleRecepcionController(DetalleRecepcionService service) {
        this.service = service;
    }

    @Operation(summary = "Registrar detalle de recepción", description = "Permite crear un nuevo detalle de recepción")
    @PostMapping
    public ResponseEntity<DetalleRecepcionDTO> guardar(@RequestBody DetalleRecepcionDTO dto) {
        DetalleRecepcionDTO response = service.guardarDetalleRecepcion(dto);

        if (response == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Buscar detalle por ID", description = "Obtiene un detalle de recepción según su identificador")
    @GetMapping("/{id}")
    public ResponseEntity<DetalleRecepcionDTO> buscarPorId(@PathVariable Integer id) {
        DetalleRecepcionDTO dto = service.buscarPorId(id);
        if (dto == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(dto);

    }

    @Operation(summary = "Buscar detalle por un estado", description = "Obtiene un detalle de recepción según estado")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<DetalleRecepcionDTO>> buscarPorestado(@PathVariable String estado) {
        List<DetalleRecepcionDTO> dto = service.buscarPorEstado(estado);
        if (dto == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(dto);

    }

    @Operation(summary = "Eliminar detalle por ID", description = "Borrar un detalle de recepción según su identificador")
    @DeleteMapping("/{id}")
    public void eliminarDetalle(@PathVariable Integer id) {
        service.deleteById(id);
    }

    @Operation(summary = "Actualizar detalle por ID", description = "Se actualiza un detalle de recepción según su identificador")
    @PutMapping("/{id}")
    public void actualizarDetalle(@PathVariable Integer id, @RequestBody DetalleRecepcionDTO detalle) {
        service.actualizarDetalleRecepcion(id, detalle);
    }

    @Operation(summary = "Buscar detalle por un Producto_id", description = "Obtiene un detalle de recepción según el ID de un producto")
    @GetMapping("/producto/{id}")
    public DetalleRecepcionDTO buscarPorIdProductoExternoDTO(@PathVariable Integer id) {
        return service.buscarPorProductoId(id);
    }

}
