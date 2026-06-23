package com.item_productos_ubicacion.item_productos_ubicacion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.item_productos_ubicacion.item_productos_ubicacion.DTO.ProductoDTO;
import com.item_productos_ubicacion.item_productos_ubicacion.DTO.UbicacionDTO;
import com.item_productos_ubicacion.item_productos_ubicacion.model.Producto;
import com.item_productos_ubicacion.item_productos_ubicacion.model.Ubicacion;
import com.item_productos_ubicacion.item_productos_ubicacion.repository.ProductoRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public ProductoDTO convertirDto(Producto produ){
        ProductoDTO dto = new ProductoDTO();
        dto.setId(produ.getId());
        dto.setNombre(produ.getNombre());
        dto.setSku(produ.getSku());
        dto.setStockActual(produ.getStock_actual());

        if(produ.getUbicacion() != null){
            UbicacionDTO ubiDto = new UbicacionDTO();
            ubiDto.setId(produ.getUbicacion().getId());
            ubiDto.setEstante(produ.getUbicacion().getEstante());
            ubiDto.setPasillo(produ.getUbicacion().getPasillo());
            dto.setUbicacion(ubiDto);
        }

        return dto;
    }

    public ProductoDTO obtenerPorId(Integer id){
        Producto producto = productoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
        return convertirDto(producto);
    }

    public List<ProductoDTO> listarTodos(){
        return productoRepository.findAll()
        .stream()
        .map(this :: convertirDto)
        .toList();
    }

    public ProductoDTO crear(ProductoDTO dto){
        if(dto.getUbicacion() == null){
            throw new RuntimeException("Debe indicar la ubicacion");
        }

        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setSku(dto.getSku());
        producto.setStock_actual(dto.getStockActual() != null ? dto.getStockActual() : 0);
        
        Ubicacion ubi = new Ubicacion();
        ubi.setPasillo(dto.getUbicacion().getPasillo());
        ubi.setEstante(dto.getUbicacion().getEstante());
        ubi.setDescripcion(dto.getUbicacion().getDescripcion());

        producto.setUbicacion(ubi);
        Producto guardado = productoRepository.save(producto);

        return convertirDto(guardado);
    }

    @Transactional
    public ProductoDTO actualizar(Integer id, ProductoDTO dto){
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Id no existe"));
        producto.setNombre(dto.getNombre());
        producto.setSku(dto.getSku());
        if(dto.getStockActual() != null){
            producto.setStock_actual(dto.getStockActual());
        }
        if(dto.getUbicacion() != null){
            Ubicacion ubi = producto.getUbicacion();
            if(ubi == null){
                ubi = new Ubicacion();
            }
            ubi.setPasillo(dto.getUbicacion().getPasillo());
            ubi.setEstante(dto.getUbicacion().getEstante());
            ubi.setDescripcion(dto.getUbicacion().getDescripcion());
            
            producto.setUbicacion(ubi);

        } else {
            throw new RuntimeException("La ubicacion es obligatoria");
        }
        Producto actualizado = productoRepository.save(producto);
        return convertirDto(actualizado);
    }

    public void eliminar(Integer id){
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));
        productoRepository.delete(producto);
    }

}
