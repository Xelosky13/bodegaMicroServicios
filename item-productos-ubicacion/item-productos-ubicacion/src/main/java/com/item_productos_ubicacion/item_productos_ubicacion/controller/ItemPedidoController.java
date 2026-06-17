package com.item_productos_ubicacion.item_productos_ubicacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.item_productos_ubicacion.item_productos_ubicacion.DTO.ItemPedidoDTO;
import com.item_productos_ubicacion.item_productos_ubicacion.service.ItemPedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/items")
@Tag(name = "ItemPedido")
public class ItemPedidoController {
    
    @Autowired
    private ItemPedidoService itemPedidoService;

    @GetMapping
    @Operation(
        summary = "Listar Todos los Items",
        description = "Todos los items de todos los pedidos"
    )
    @ApiResponse(responseCode = "200", description = "Lista de items encontrada")
    @ApiResponse(responseCode = "204", description = "No hay items en el sistema")
    public ResponseEntity<List<ItemPedidoDTO>> todosLosClientes(){
        List<ItemPedidoDTO> items = itemPedidoService.obtenerTodos();
        if(items.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/{id}")
        @Operation(
        summary = "Buscar un ítem de pedido por ID", 
        description = "Obtiene los detalles de un ítem de pedido específico utilizando su identificador único.")
    @ApiResponse(responseCode = "200", description = "Ítem de pedido encontrado exitosamente")
    @ApiResponse(responseCode = "404", description = "Ítem de pedido no encontrado")
    public ResponseEntity<ItemPedidoDTO> buscarPorId(@PathVariable Integer id){
        try {
            ItemPedidoDTO item = itemPedidoService.buscarPorId(id);
            return new ResponseEntity<>(item, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(
        summary = "Agregar un nuevo ítem de pedido", 
        description = "Registra un nuevo ítem dentro de un pedido, validando la existencia del producto. Retorna el DTO creado.")
    @ApiResponse(responseCode = "201", description = "Ítem de pedido creado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o error en la solicitud")
    public ResponseEntity<ItemPedidoDTO> agregarItemPedido(@Valid @RequestBody ItemPedidoDTO item) {
        try {
            ItemPedidoDTO guardado = itemPedidoService.guardarItemPedido(item);
            return new ResponseEntity<>(guardado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    @Operation(
        summary = "Actualizar parcialmente un ítem de pedido", 
        description = "Modifica los campos permitidos de un ítem de pedido existente utilizando su ID.")
    @ApiResponse(responseCode = "200", description = "Ítem de pedido actualizado con éxito")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    @ApiResponse(responseCode = "404", description = "Ítem de pedido no encontrado")
    public ResponseEntity<ItemPedidoDTO> editarItemPedido(@PathVariable Integer id, @Valid @RequestBody ItemPedidoDTO dto) {
        try {
            ItemPedidoDTO editado = itemPedidoService.actualizarItemPedido(id, dto);
            return new ResponseEntity<>(editado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar un ítem de pedido existente", 
        description = "Modifica los datos de un ítem de pedido basándose en su ID de ruta y los nuevos datos enviados en el cuerpo."
    )
    @ApiResponse(responseCode = "200", description = "Ítem de pedido actualizado con éxito")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    @ApiResponse(responseCode = "404", description = "Ítem de pedido no encontrado")
    public ResponseEntity<ItemPedidoDTO> actualizarItemPedido(@PathVariable Integer id, @Valid @RequestBody ItemPedidoDTO dto){
        try {
            ItemPedidoDTO actualizado = itemPedidoService.actualizarItemPedido(id, dto);
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar un ítem de pedido por ID", 
        description = "Remueve permanentemente un ítem de pedido del sistema utilizando su identificador único."
    )
    @ApiResponse(responseCode = "200", description = "Ítem de pedido eliminado exitosamente")
    @ApiResponse(responseCode = "404", description = "Ítem de pedido no encontrado para eliminar")
    public ResponseEntity<String> eliminarItemPedido(@PathVariable Integer id) {
        String resultado = itemPedidoService.eliminar(id);
        
        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/total-unidades/{idPedido}")
    @Operation(
        summary = "Obtener el total de unidades de un pedido", 
        description = "Calcula la suma total de las cantidades de todos los ítems que pertenecen a un pedido específico."
    )
    @ApiResponse(responseCode = "200", description = "Total de unidades calculado exitosamente")
    @ApiResponse(responseCode = "404", description = "Pedido no encontrado o sin ítems asociados")
    public ResponseEntity<Integer> totalUnidades(@PathVariable Integer idPedido){
        Integer total = itemPedidoService.obtenerTotalUnidadesPorPedido(idPedido);
        return ResponseEntity.ok(total);
    }
}
