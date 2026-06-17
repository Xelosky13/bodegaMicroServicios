package com.item_productos_ubicacion.item_productos_ubicacion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.item_productos_ubicacion.item_productos_ubicacion.DTO.MermaDTO;
import com.item_productos_ubicacion.item_productos_ubicacion.service.MermaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/mermas")
public class MermaController {
   
    @Autowired
    private MermaService mermaService;

    @PostMapping
    @Operation(
        summary = "Registrar una nueva merma", 
        description = "Registra la pérdida o desecho de un producto, descuenta la cantidad especificada del stock actual y retorna los datos de la merma grabada."
    )
    @ApiResponse(responseCode = "201", description = "Merma registrada exitosamente y stock actualizado")
    @ApiResponse(responseCode = "400", description = "Error en la solicitud (ej. Stock insuficiente o producto inexistente)")
    public ResponseEntity<?> registrarMerma(@Valid @RequestBody MermaDTO mermaDTO) {
        try {
            MermaDTO mermaGuardada = mermaService.registrar(mermaDTO);
            return new ResponseEntity<>(mermaGuardada, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
