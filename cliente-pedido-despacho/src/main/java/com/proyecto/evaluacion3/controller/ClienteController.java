package com.proyecto.evaluacion3.controller;

import com.proyecto.evaluacion3.DTO.ClienteDTO;
import com.proyecto.evaluacion3.model.Cliente;
import com.proyecto.evaluacion3.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/clientes")
@Tag(name = "Clientes", description = "Gestión de los clientes del sistema")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping
    @Operation(summary = "Obtener todos los clientes")
    public List<ClienteDTO> listarTodos() {
        return service.obtenerTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener cliente por ID con enlaces HATEOAS")
    public EntityModel<ClienteDTO> obtenerCliente(@PathVariable Integer id) {
        ClienteDTO dto = service.buscarPorId(id);

        return EntityModel.of(dto,
                linkTo(methodOn(ClienteController.class).obtenerCliente(id)).withSelfRel(),
                linkTo(methodOn(ClienteController.class).listarTodos()).withRel("todos-los-clientes")
        );
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo cliente")
    public ClienteDTO crearCliente(@RequestBody Cliente cliente) {
        return service.guardar(cliente);
    }
}
