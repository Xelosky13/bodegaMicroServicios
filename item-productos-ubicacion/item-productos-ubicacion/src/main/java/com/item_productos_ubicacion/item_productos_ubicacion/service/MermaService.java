package com.item_productos_ubicacion.item_productos_ubicacion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.item_productos_ubicacion.item_productos_ubicacion.DTO.MermaDTO;
import com.item_productos_ubicacion.item_productos_ubicacion.DTO.ProductoDTO;
import com.item_productos_ubicacion.item_productos_ubicacion.DTO.UbicacionDTO;
import com.item_productos_ubicacion.item_productos_ubicacion.model.Merma;
import com.item_productos_ubicacion.item_productos_ubicacion.model.Producto;
import com.item_productos_ubicacion.item_productos_ubicacion.repository.MermaRepository;
import com.item_productos_ubicacion.item_productos_ubicacion.repository.ProductoRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class MermaService {

    @Autowired
    private MermaRepository mermaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public MermaDTO convertirDto(Merma merma) {
        MermaDTO dto = new MermaDTO();
        dto.setId(merma.getId());
        dto.setFechaReporte(merma.getFechaReporte());
        dto.setCantidad(merma.getCantidad());
        dto.setMotivo(merma.getMotivo());
        if(merma.getProducto() != null) {
            ProductoDTO prodDto = new ProductoDTO();
            prodDto.setId(merma.getProducto().getId());
            prodDto.setNombre(merma.getProducto().getNombre());
            prodDto.setSku(merma.getProducto().getSku());
            if(merma.getProducto().getUbicacion() != null) {
                UbicacionDTO ubiDto = new UbicacionDTO();
                ubiDto.setId(merma.getProducto().getUbicacion().getId());
                ubiDto.setPasillo(merma.getProducto().getUbicacion().getPasillo());
                ubiDto.setEstante(merma.getProducto().getUbicacion().getEstante());
                ubiDto.setDescripcion(merma.getProducto().getUbicacion().getDescripcion());
                prodDto.setUbicacion(ubiDto);
            }
            dto.setProducto(prodDto);
        }
        return dto;
    }

    public List<MermaDTO> listarTodos(){
        return mermaRepository.findAll()
        .stream()
        .map(this::convertirDto).toList();
    }

    public MermaDTO obtenerPorId(Integer id){
        Merma merma = mermaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Merma no encontrada con id: " + id));
        return convertirDto(merma);
    }

    @Transactional
    public MermaDTO registrar(MermaDTO dto){
        Producto producto = productoRepository.findById(dto.getProducto().getId())
                .orElseThrow(() -> new RuntimeException(
                    "Producto no encontrado"));
        if(producto.getStock_actual() < dto.getCantidad()){
            throw new RuntimeException("Stock insuficiente. Stock actual : "
                + producto.getStock_actual());
        }

        producto.setStock_actual(producto.getStock_actual() - dto.getCantidad());
        productoRepository.save(producto);

        Merma merma = new Merma();
        merma.setProducto(producto);
        merma.setCantidad(dto.getCantidad());
        merma.setMotivo(dto.getMotivo());
        merma.setFechaReporte(dto.getFechaReporte());

        return convertirDto(mermaRepository.save(merma));
    }

    public void eliminar(Integer id){
        Merma merma = mermaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Merma no encontrada"));
        mermaRepository.delete(merma);
    }
}
