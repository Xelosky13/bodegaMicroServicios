package com.servicio.recepcion.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.servicio.recepcion.DTO.DetalleRecepcionDTO;
import com.servicio.recepcion.DTO.ProductoExternoDTO;
import com.servicio.recepcion.exception.RessourceNotfoundException;
import com.servicio.recepcion.model.DetalleRecepcion;
import com.servicio.recepcion.repository.DetalleRecepcionRepository;

@Service
public class DetalleRecepcionService {
    private DetalleRecepcionRepository repository;
    private ProductoService proService;

    public DetalleRecepcionService(DetalleRecepcionRepository repo, ProductoService service) {
        this.repository = repo;
        this.proService = service;
    }

    public DetalleRecepcionDTO buscarPorId(Integer id) {

        DetalleRecepcion response = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle no encontrado con id: " + id));

        return this.mappearADTO(response);
    }

    public DetalleRecepcionDTO mappearADTO(DetalleRecepcion detalle) {

        DetalleRecepcionDTO dto = new DetalleRecepcionDTO();

        dto.setCantidad(detalle.getCantidad());
        dto.setEstado(detalle.getEstado());
        dto.setOrden(detalle.getOrden());

        if (detalle.getIdproducto() != null) {
            ProductoExternoDTO producto = proService.obtenerProducto(detalle.getIdproducto());
            dto.setProducto(producto);
        }

        return dto;
    }

    public DetalleRecepcionDTO mappearAdto(DetalleRecepcionDTO detalle) {
        DetalleRecepcionDTO dto = new DetalleRecepcionDTO();
        dto.setCantidad(detalle.getCantidad());
        dto.setEstado(detalle.getEstado());
        dto.setOrden(detalle.getOrden());
        dto.setProducto(detalle.getProducto());
        return dto;

    }

    public List<DetalleRecepcionDTO> buscarPorEstado(String estado) {
        List<DetalleRecepcion> listDetalle = repository.findByEstado(estado);
        return listDetalle.stream().map(this::mappearADTO).toList();

    }

    public DetalleRecepcionDTO buscarPorProductoId(Integer idProducto) {
        DetalleRecepcionDTO recepcionDto = buscarPorId(idProducto);
        if (recepcionDto == null) {
            throw new RessourceNotfoundException("No se ha encontrado el id de este producto");
        }
        ProductoExternoDTO pdto = proService.obtenerProducto(idProducto);
        System.out.println(" producto encontrado" + pdto);
        if (pdto == null) {
            throw new RessourceNotfoundException("No se ha encontrado el id de este producto");
        }

        DetalleRecepcionDTO detalle = new DetalleRecepcionDTO();
        detalle.setCantidad(recepcionDto.getCantidad());
        detalle.setEstado(recepcionDto.getEstado());
        detalle.setOrden(recepcionDto.getOrden());
        detalle.setProducto(pdto);
        return detalle;

    }

    public void deleteById(Integer id) {
        repository.deleteById(id);

    }

    public DetalleRecepcionDTO guardarDetalleRecepcion(DetalleRecepcionDTO dto) {
        DetalleRecepcion detalleRecepcion = new DetalleRecepcion();

        detalleRecepcion.setCantidad(dto.getCantidad());
        detalleRecepcion.setEstado(dto.getEstado());
        detalleRecepcion.setOrden(dto.getOrden());

        if (dto.getProducto() == null || dto.getProducto().getId() == null) {
            throw new IllegalArgumentException("El producto es obligatorio");
        }
        detalleRecepcion.setIdproducto(dto.getProducto().getId());

        DetalleRecepcion guardado = repository.save(detalleRecepcion);

        return this.mappearADTO(guardado);
    }

    public DetalleRecepcionDTO actualizarDetalleRecepcion(Integer id, DetalleRecepcionDTO detalle) {
        DetalleRecepcion detalleRec = repository.findById(id).orElse(null);

        if (detalleRec != null) {
            detalleRec.setCantidad(detalle.getCantidad());
            detalleRec.setEstado(detalle.getEstado());
            detalleRec.setOrden(detalle.getOrden());
            repository.save(detalleRec);
        }
        return this.mappearADTO(detalleRec);
    }
}
