package com.proyecto.evaluacion3.controller;

import com.proyecto.evaluacion3.DTO.OperarioDTO;
import com.proyecto.evaluacion3.model.Operario;
import com.proyecto.evaluacion3.service.OperarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/operarios")
public class OperarioController {

    @Autowired
    private OperarioService service;

    @GetMapping
    public List<OperarioDTO> listarTodos() {
        return service.obtenerTodos();
    }

    @GetMapping("/{id}")
    public EntityModel<OperarioDTO> obtenerOperario(@PathVariable Integer id) {
        OperarioDTO operario = service.buscarPorId(id);
        return EntityModel.of(operario,
                linkTo(methodOn(OperarioController.class).obtenerOperario(id)).withSelfRel(),
                linkTo(methodOn(OperarioController.class).listarTodos()).withRel("todos-los-operarios")
        );
    }

    @PostMapping
    public OperarioDTO crearOperario(@RequestBody Operario operario) {
        return service.guardar(operario);
    }
}