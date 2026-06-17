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

import com.item_productos_ubicacion.item_productos_ubicacion.DTO.ProductoDTO;
import com.item_productos_ubicacion.item_productos_ubicacion.service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {
    @Autowired
    private ProductoService productoService;


    @GetMapping
    @Operation(
        summary = "Listar todos los productos", 
        description = "Retorna una lista completa de todos los productos registrados en el inventario con sus respectivas ubicaciones."
    )
    @ApiResponse(responseCode = "200", description = "Lista de productos recuperada con éxito")
    @ApiResponse(responseCode = "204", description = "No hay productos registrados en el sistema")
    public ResponseEntity<List<ProductoDTO>> listarTodos() {
        List<ProductoDTO> productos = productoService.listarTodos();
        if (productos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar un producto por ID", 
        description = "Obtiene los detalles de un producto específico utilizando su identificador único."
    )
    @ApiResponse(responseCode = "200", description = "Producto encontrado exitosamente")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado en la base de datos")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        try {
            ProductoDTO productoDTO = productoService.obtenerPorId(id);
            return ResponseEntity.ok(productoDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    @Operation(
        summary = "Crear un nuevo producto", 
        description = "Registra un producto en el sistema, vinculándolo opcionalmente a una ubicación mediante su ID."
    )
    @ApiResponse(responseCode = "201", description = "Producto creado de manera exitosa")
    @ApiResponse(responseCode = "400", description = "Error en los datos enviados")
    public ResponseEntity<?> crear(@Valid @RequestBody ProductoDTO productoDTO) {
        try {
            ProductoDTO creado = productoService.crear(productoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar un producto existente", 
        description = "Permite actualizar el nombre, SKU, stock y cambiar su ubicación."
    )
    @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente")
    @ApiResponse(responseCode = "400", description = "Error en la solicitud (ej. Producto no encontrado o datos inválidos)")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @Valid @RequestBody ProductoDTO productoDTO) {
        try {
            ProductoDTO actualizado = productoService.actualizar(id, productoDTO);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar un producto por ID", 
        description = "Remueve permanentemente un producto del inventario utilizando su identificador único."
    )
    @ApiResponse(responseCode = "200", description = "Producto eliminado correctamente")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado para eliminar")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            productoService.eliminar(id);
            return ResponseEntity.ok("Producto eliminado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
