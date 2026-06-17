package com.item_productos_ubicacion.item_productos_ubicacion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.item_productos_ubicacion.item_productos_ubicacion.DTO.ProductoDTO;
import com.item_productos_ubicacion.item_productos_ubicacion.DTO.UbicacionDTO;
import com.item_productos_ubicacion.item_productos_ubicacion.model.Ubicacion;
import com.item_productos_ubicacion.item_productos_ubicacion.repository.UbicacionRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UbicacionService {
    @Autowired
    private UbicacionRepository ubicacionRepository;

    public UbicacionDTO convertirDto(Ubicacion ubi){
        UbicacionDTO dto = new UbicacionDTO();
        dto.setDescripcion(ubi.getDescripcion());
        dto.setEstante(ubi.getEstante());
        dto.setPasillo(ubi.getPasillo());
        dto.setId(ubi.getId());
        if(ubi.getProductos() != null){
            List<ProductoDTO> productos = ubi.getProductos().stream()
                .map(prod -> {
                    ProductoDTO prodDTO = new ProductoDTO();
                    prodDTO.setId(prod.getId());
                    prodDTO.setNombre(prod.getNombre());
                    prodDTO.setSku(prod.getSku());
                    prodDTO.setStockActual(prod.getStock_actual());
                    prodDTO.setUbicacion(null);
                    return prodDTO;
                })
                .toList();
            dto.setProductos(productos);
        }
        return dto;
    }

    public UbicacionDTO obtenerPorId(Integer id){
        Ubicacion ubicacion = ubicacionRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Ubicacion no encontrada"));
        return convertirDto(ubicacion);
    }

    public List<UbicacionDTO> obtenerTodas(){
        List<Ubicacion> ubi = ubicacionRepository.findAll();
        return ubi.stream().map(this :: convertirDto).toList();
    }

    public UbicacionDTO guardarUbicacion(UbicacionDTO dto){
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setDescripcion(dto.getDescripcion());
        ubicacion.setEstante(dto.getEstante());
        ubicacion.setPasillo(dto.getPasillo());
        ubicacion.setId(dto.getId());
        Ubicacion guardada = ubicacionRepository.save(ubicacion);
        return convertirDto(guardada);
    }

    public void eliminar(Integer id){
        Ubicacion ubicacion = ubicacionRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));
        ubicacionRepository.delete(ubicacion);
    }

    public UbicacionDTO actualizarUbicacion(Integer id, UbicacionDTO dto) {
        Ubicacion ubicacion = ubicacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La ubicación con ID " + id + " no existe"));
                
        ubicacion.setPasillo(dto.getPasillo());
        ubicacion.setEstante(dto.getEstante());
        ubicacion.setDescripcion(dto.getDescripcion());
                
        Ubicacion guardada = ubicacionRepository.save(ubicacion);
        return convertirDto(guardada);
    }
}
