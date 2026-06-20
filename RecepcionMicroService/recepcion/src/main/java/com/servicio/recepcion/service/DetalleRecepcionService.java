package com.servicio.recepcion.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servicio.recepcion.DTO.DetalleRecepcionDTO;
import com.servicio.recepcion.DTO.ProductoExternoDTO;
import com.servicio.recepcion.model.DetalleRecepcion;
import com.servicio.recepcion.repository.DetalleRecepcionRepository;

@Service
public class DetalleRecepcionService {
    @Autowired
    private DetalleRecepcionRepository repository;
    @Autowired
    private ProductoService proService;

    public DetalleRecepcionDTO buscarPorId(Integer id) {
        DetalleRecepcion response = repository.findDetalleRecepcionById(id);
        return this.mappearADTO(response);

    }

    public DetalleRecepcionDTO mappearADTO(DetalleRecepcion detalle) {
        DetalleRecepcionDTO dto = new DetalleRecepcionDTO();
        dto.setCantidad(detalle.getCantidad());
        dto.setEstado(detalle.getEstado());
        dto.setOrden(detalle.getOrden());
        dto.setProducto_id(detalle.getProducto_id());
        return dto;

    }

    public DetalleRecepcionDTO mappearAMODELO(DetalleRecepcionDTO detalle) {
        DetalleRecepcionDTO modelo = new DetalleRecepcionDTO();
        modelo.setCantidad(detalle.getCantidad());
        modelo.setEstado(detalle.getEstado());
        modelo.setOrden(detalle.getOrden());
        modelo.setProducto_id(detalle.getProducto_id());
        return modelo;

    }

    public List<DetalleRecepcionDTO> buscarPorEstado(String estado) {
        List<DetalleRecepcion> listDetalle = repository.findByEstado(estado);
        return listDetalle.stream().map(this::mappearADTO).toList();

    }

    public ProductoExternoDTO buscarPorProducto_id(Integer idProducto) {

        // DetalleRecepcion detalle = repository.findByProducto(idProducto);
        ProductoExternoDTO pdto = proService.obtenerProducto(idProducto);
        if (pdto == null) {
            System.out.println("Producto no encontrado");
        }
        // return this.mappearADTO(detalle);
        return pdto;

    }

    public void deleteById(Integer id) {
        repository.deleteById(id);

    }

    public DetalleRecepcionDTO guardarDetalleRecepcion(DetalleRecepcionDTO dto) {
        DetalleRecepcion detalleRecepcion = new DetalleRecepcion();
        detalleRecepcion.setCantidad(dto.getCantidad());
        detalleRecepcion.setEstado(dto.getEstado());
        detalleRecepcion.setOrden(dto.getOrden());
        detalleRecepcion.setProducto_id(dto.getProducto_id());

        DetalleRecepcion guardado = repository.save(detalleRecepcion);

        return this.mappearADTO(guardado);
    }

    public DetalleRecepcionDTO actualizarDetalleRecepcion(Integer id, DetalleRecepcionDTO detalle) {
        DetalleRecepcion detalleRec = repository.findById(id).orElse(null);

        if (detalleRec != null) {
            detalleRec.setCantidad(detalle.getCantidad());
            detalleRec.setEstado(detalle.getEstado());
            detalleRec.setOrden(detalle.getOrden());
            detalleRec.setProducto_id(detalle.getProducto_id());
            repository.save(detalleRec);
        }
        return this.mappearADTO(detalleRec);
    }
}
