package com.proyecto.evaluacion3.controller;

import com.proyecto.evaluacion3.DTO.DespachoDTO;
import com.proyecto.evaluacion3.model.Despacho;
import com.proyecto.evaluacion3.service.DespachoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/despachos")
@Tag(name = "Despachos", description = "Operaciones de la central de despachos")
public class DespachoController {

    @Autowired
    private DespachoService service;

    @GetMapping
    @Operation(summary = "Obtener todos los despachos")
    public List<DespachoDTO> listarTodos() {
        return service.obtenerTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un despacho por ID con enlaces HATEOAS")
    public EntityModel<DespachoDTO> obtenerDespacho(@PathVariable Integer id) {
        DespachoDTO despachoDTO = service.buscarPorId(id);

        return EntityModel.of(despachoDTO,
                linkTo(methodOn(DespachoController.class).obtenerDespacho(id)).withSelfRel(),
                linkTo(methodOn(DespachoController.class).listarTodos()).withRel("todos-los-despachos")
        );
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo despacho")
    public DespachoDTO crearDespacho(@RequestBody Despacho despacho) {
        return service.guardar(despacho);
    }
}
