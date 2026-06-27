package com.proyecto.evaluacion3.controller;

import com.proyecto.evaluacion3.DTO.PickingDTO;
import com.proyecto.evaluacion3.model.Picking;
import com.proyecto.evaluacion3.service.PickingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/pickings")
@Tag(name = "Picking", description = "Operaciones de armado de pedidos")
public class PickingController {

    @Autowired
    private PickingService service;

    @GetMapping
    @Operation(summary = "Obtener todos los procesos de picking")
    public List<PickingDTO> listarTodos() {
        return service.obtenerTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un picking por ID con enlaces HATEOAS")
    public EntityModel<PickingDTO> obtenerPicking(@PathVariable Integer id) {
        PickingDTO pickingDTO = service.buscarPorId(id);

        return EntityModel.of(pickingDTO,
                linkTo(methodOn(PickingController.class).obtenerPicking(id)).withSelfRel(),
                linkTo(methodOn(PickingController.class).listarTodos()).withRel("todos-los-picking")
        );
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo proceso de picking")
    public PickingDTO crearPicking(@RequestBody Picking picking) {
        return service.guardar(picking);
    }
}
