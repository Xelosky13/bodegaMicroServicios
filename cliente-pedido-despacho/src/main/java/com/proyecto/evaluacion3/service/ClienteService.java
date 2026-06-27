package com.proyecto.evaluacion3.service;

import com.proyecto.evaluacion3.DTO.ClienteDTO;
import com.proyecto.evaluacion3.model.Cliente;
import com.proyecto.evaluacion3.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public List<ClienteDTO> obtenerTodos() {
        List<ClienteDTO> listaDTOs = new ArrayList<>();
        for (Cliente c : repository.findAll()) {
            listaDTOs.add(convertirADTO(c));
        }
        return listaDTOs;
    }

    public ClienteDTO buscarPorId(Integer id) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        return convertirADTO(cliente);
    }

    public ClienteDTO guardar(Cliente nuevoCliente) {
        Cliente guardado = repository.save(nuevoCliente);
        return convertirADTO(guardado);
    }

    private ClienteDTO convertirADTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setRut(cliente.getRut());
        dto.setNombre(cliente.getNombre());
        dto.setEmail(cliente.getEmail());
        return dto;
    }
}
