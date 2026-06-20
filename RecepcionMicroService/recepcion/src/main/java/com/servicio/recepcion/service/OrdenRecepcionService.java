package com.servicio.recepcion.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servicio.recepcion.DTO.OrdenRecepcionDTO;
import com.servicio.recepcion.model.OrdenRecepcion;
import com.servicio.recepcion.repository.OrdenRecepcionRepository;

@Service
public class OrdenRecepcionService {
    @Autowired
    private OrdenRecepcionRepository repo;

    public OrdenRecepcionDTO buscarOrdenPorId(Integer id) {
        OrdenRecepcion response = repo.findById(id).orElse(null);
        return this.mappearADTO(response);

    }

    public OrdenRecepcionDTO mappearADTO(OrdenRecepcion orden) {
        OrdenRecepcionDTO dto = new OrdenRecepcionDTO();
        dto.setDetalles(orden.getDetalles());
        dto.setProvedor(orden.getProveedor());
        dto.setFechaRecepcion(orden.getFechaRecepcion());
        return dto;

    }

    public OrdenRecepcionDTO buscarPorProveedor(Integer id) {
        OrdenRecepcion ordenRecepcion = repo.findByProveedor(id);
        return this.mappearADTO(ordenRecepcion);
    }

    public OrdenRecepcionDTO buscarPorFechaRecepcion(LocalDate fecha) {
        OrdenRecepcion ordenRecepcion = repo.findByfechaRecepcion(fecha);
        return this.mappearADTO(ordenRecepcion);
    }

    public void EliminarOrden(Integer id) {
        repo.deleteById(id);
    }

    public OrdenRecepcionDTO actualizarOrden(Integer id, OrdenRecepcionDTO ordenRec) {
        OrdenRecepcion ordenRecep = repo.findById(id).orElse(null);
        if (ordenRecep != null) {
            ordenRecep.setDetalles(ordenRec.getDetalles());
            ordenRecep.setProveedor(ordenRec.getProvedor());
            repo.save(ordenRecep);

        }
        return this.mappearADTO(ordenRecep);
    }

    public OrdenRecepcionDTO guardarOrden(OrdenRecepcionDTO dto) {
        OrdenRecepcion ordenRecepcion = new OrdenRecepcion();
        ordenRecepcion.setDetalles(dto.getDetalles());
        ordenRecepcion.setFechaRecepcion(dto.getFechaRecepcion());
        ordenRecepcion.setProveedor(dto.getProvedor());

        OrdenRecepcion guardado = repo.save(ordenRecepcion);

        return this.mappearADTO(guardado);
    }

    public List<OrdenRecepcionDTO> listarOrdenes() {
        List<OrdenRecepcion> orden = repo.findAll();
        return orden.stream().map(this::mappearADTO).toList();

    }

}
