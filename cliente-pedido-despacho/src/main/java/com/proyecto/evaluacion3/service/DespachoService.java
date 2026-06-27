package com.proyecto.evaluacion3.service;

import com.proyecto.evaluacion3.DTO.DespachoDTO;
import com.proyecto.evaluacion3.model.Despacho;
import com.proyecto.evaluacion3.repository.DespachoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DespachoService {

    @Autowired
    private DespachoRepository repository;

    @Autowired
    private DespachoValidaciones validaciones;

    public List<DespachoDTO> obtenerTodos() {
        List<DespachoDTO> listaDTOs = new ArrayList<>();
        for (Despacho d : repository.findAll()) {
            listaDTOs.add(validaciones.convertirADTO(d));
        }
        return listaDTOs;
    }

    public DespachoDTO buscarPorId(Integer id) {
        Despacho despacho = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despacho no encontrado"));
        return validaciones.convertirADTO(despacho);
    }

    public DespachoDTO guardar(Despacho nuevoDespacho) {
        Despacho guardado = repository.save(nuevoDespacho);
        return validaciones.convertirADTO(guardado);
    }
}