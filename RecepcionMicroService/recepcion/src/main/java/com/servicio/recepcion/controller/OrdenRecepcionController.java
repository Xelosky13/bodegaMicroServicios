package com.servicio.recepcion.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.servicio.recepcion.DTO.OrdenRecepcionDTO;
import com.servicio.recepcion.service.OrdenRecepcionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Orden Recepción", description = "Operaciones relacionadas con los ordenes de recepción")
@RequestMapping("/api/v1/ordenrecepcion")
public class OrdenRecepcionController {
    @Autowired
    private OrdenRecepcionService service;

    @Operation(summary = "Registrar un orden de recepción", description = "Permite crear un nuevo orden de recepción")
    @PostMapping
    public ResponseEntity<OrdenRecepcionDTO> guardar(@RequestBody OrdenRecepcionDTO dto) {
        OrdenRecepcionDTO response = service.guardarOrden(dto);
        if (response == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener un orden de recepción", description = "Permite obtener un nuevo orden de recepción")
    @GetMapping("/{id}")
    public ResponseEntity<OrdenRecepcionDTO> buscarPorId(@PathVariable Integer id) {
        OrdenRecepcionDTO dto = service.buscarOrdenPorId(id);
        if (dto == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Obtener Orden de recepción", description = "Permite obtener un orden de recepción por el ID de un proveedor")
    @GetMapping("/proveedor/{id}")
    public ResponseEntity<OrdenRecepcionDTO> buscarPorproveedor(@PathVariable Integer id) {
        OrdenRecepcionDTO dto = service.buscarPorProveedor(id);
        if (dto == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(dto);

    }

    @Operation(summary = "Eliminar orden de recepción", description = "Permite eliminar un orden por su ID")
    @DeleteMapping
    public void eliminarOrden(@PathVariable Integer id) {
        service.EliminarOrden(id);
    }

    @Operation(summary = "Actualizar orden de recepción", description = "Permite actualizar orden de recepción")
    @PutMapping("/{id}")
    public void actualizarOrden(@PathVariable Integer id, @RequestBody OrdenRecepcionDTO dto) {
        service.actualizarOrden(id, dto);
    }

    @Operation(summary = "Obtener lista de orden de recepción", description = "Permite obtener una lista de orden de recepción")
    @GetMapping
    public List<OrdenRecepcionDTO> listarOrdenes() {
        return service.listarOrdenes();
    }

}
