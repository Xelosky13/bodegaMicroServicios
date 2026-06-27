package com.proyecto.evaluacion3.controller;

import com.proyecto.evaluacion3.DTO.PedidoDTO;
import com.proyecto.evaluacion3.model.Pedido;
import com.proyecto.evaluacion3.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/pedidos")
@Tag(name = "Pedidos", description = "Operaciones de la central de pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @GetMapping
    @Operation(summary = "Obtener todos los pedidos")
    public List<PedidoDTO> listarTodos() {
        return service.obtenerTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un pedido por ID con enlaces HATEOAS")
    public EntityModel<PedidoDTO> obtenerPedido(@PathVariable Integer id) {
        PedidoDTO pedidoDTO = service.buscarPorId(id);

        return EntityModel.of(pedidoDTO,
                linkTo(methodOn(PedidoController.class).obtenerPedido(id)).withSelfRel(),
                linkTo(methodOn(PedidoController.class).listarTodos()).withRel("todos-los-pedidos")
        );
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo pedido")
    public PedidoDTO crearPedido(@RequestBody Pedido pedido) {
        return service.guardar(pedido);
    }
}
