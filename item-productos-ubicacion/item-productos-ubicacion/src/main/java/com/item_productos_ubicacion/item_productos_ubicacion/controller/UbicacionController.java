package com.item_productos_ubicacion.item_productos_ubicacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.item_productos_ubicacion.item_productos_ubicacion.DTO.UbicacionDTO;
import com.item_productos_ubicacion.item_productos_ubicacion.service.UbicacionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/ubicaciones")
public class UbicacionController {
    @Autowired
    private UbicacionService ubicacionService;

    @PostMapping
    @Operation(
        summary = "Crear una nueva ubicación", 
        description = "Registra una nueva ubicación física o lógica en el sistema para el almacenamiento de productos."
    )
    @ApiResponse(responseCode = "201", description = "Ubicación creada exitosamente")
    @ApiResponse(responseCode = "400", description = "Error en los datos enviados o solicitud inválida")
    public ResponseEntity<UbicacionDTO> guardar(@Valid @RequestBody UbicacionDTO dto) {
        UbicacionDTO response = ubicacionService.guardarUbicacion(dto);
        if (response == null) {
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar ubicación por ID", 
        description = "Obtiene los detalles de una ubicación específica y la lista de productos almacenados en ella utilizando su identificador único."
    )
    @ApiResponse(responseCode = "200", description = "Ubicación encontrada exitosamente")
    @ApiResponse(responseCode = "404", description = "Ubicación no encontrada en el sistema")
    public ResponseEntity<UbicacionDTO> buscarPorId(@PathVariable Integer id) {
        UbicacionDTO dto = ubicacionService.obtenerPorId(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    @Operation(
        summary = "Listar todas las ubicaciones", 
        description = "Retorna una lista completa de todas las ubicaciones registradas en el sistema junto con los productos que contienen."
    )
    @ApiResponse(responseCode = "200", description = "Lista de ubicaciones recuperada con éxito")
    @ApiResponse(responseCode = "204", description = "No hay ubicaciones registradas en el sistema")
    public ResponseEntity<List<UbicacionDTO>> obtenerTodas() {
        List<UbicacionDTO> lista = ubicacionService.obtenerTodas();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar una ubicación por ID", 
        description = "Remueve permanentemente una ubicación del sistema utilizando su identificador único."
    )
    @ApiResponse(responseCode = "200", description = "Ubicación eliminada correctamente")
    @ApiResponse(responseCode = "404", description = "Ubicación no encontrada para eliminar")
    public ResponseEntity<String> eliminarPorId(@PathVariable Integer id) {
        try {
            ubicacionService.eliminar(id);
            return ResponseEntity.ok("Ubicación eliminada correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar una ubicación existente", 
        description = "Modifica los datos de una ubicación (pasillo, estante, descripción) basándose en su ID de ruta y los nuevos datos provistos."
    )
    @ApiResponse(responseCode = "200", description = "Ubicación actualizada con éxito")
    @ApiResponse(responseCode = "400", description = "Error en los datos de entrada")
    @ApiResponse(responseCode = "404", description = "Ubicación no encontrada")
    public ResponseEntity<?> actualizarUbicacion(@PathVariable Integer id, @Valid @RequestBody UbicacionDTO dto) {
        try {
            UbicacionDTO actualizado = ubicacionService.actualizarUbicacion(id, dto);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
