package com.item_productos_ubicacion.item_productos_ubicacion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.item_productos_ubicacion.item_productos_ubicacion.DTO.ItemPedidoDTO;
import com.item_productos_ubicacion.item_productos_ubicacion.model.ItemPedido;
import com.item_productos_ubicacion.item_productos_ubicacion.model.Producto;
import com.item_productos_ubicacion.item_productos_ubicacion.repository.ItemPedidoRepository;
import com.item_productos_ubicacion.item_productos_ubicacion.repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
public class ItemPedidoService {
    @Autowired
    private ProductoService productoService;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;
    
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public ItemPedidoDTO convertirDto(ItemPedido itemPedido) {
        ItemPedidoDTO dto = new ItemPedidoDTO();
        dto.setId(itemPedido.getId());
        dto.setCantidad(itemPedido.getCantidad());
        try {
            Integer pedidoDetectado = webClientBuilder.build()
            .get()
            .uri("  ")
            .retrieve()
            .bodyToMono(Integer.class)
            .block();
        dto.setPedido_id(pedidoDetectado);
        } catch (Exception e) {
            dto.setPedido_id(null);
        }
        if (itemPedido.getProducto() != null) {
            dto.setProducto(productoService.convertirDto(itemPedido.getProducto()));
        }

        return dto;
    }


    public List<ItemPedidoDTO> obtenerTodos() {
        return itemPedidoRepository.findAll().stream()
        .map(this::convertirDto).toList();
    }

    public ItemPedidoDTO buscarPorId(Integer id) {
        ItemPedido item = itemPedidoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Item de pedido no encontrado"));
        return convertirDto(item);
    }

    public String eliminar(Integer id) {
        try {
            ItemPedido item = itemPedidoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException(
                "Item de pedido no encontrado"));
            itemPedidoRepository.delete(item);
            return "Item" +item.getId() + " eliminado exitosamente";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Transactional
    public ItemPedidoDTO guardarItemPedido(ItemPedidoDTO dto) {
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setCantidad(dto.getCantidad());
        itemPedido.setPedido_id(dto.getPedido_id());

        if (dto.getProducto() != null && dto.getProducto().getId() != null) {
            Producto productoReal = productoRepository.findById(dto.getProducto().getId())
                    .orElseThrow(() -> new RuntimeException("El producto especificado no existe"));
            itemPedido.setProducto(productoReal);
        } else {
            throw new RuntimeException("Debe asociar un producto válido al ítem de pedido");
        }

        ItemPedido guardado = itemPedidoRepository.save(itemPedido);

        return convertirDto(guardado);
    }

    @Transactional
    public ItemPedidoDTO actualizarItemPedido(Integer id, ItemPedidoDTO dto) {
        ItemPedido actualizado = itemPedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item no encontrado con id: " + id));

        if (dto.getCantidad() != null) {
            actualizado.setCantidad(dto.getCantidad());
        }

        if (dto.getPedido_id() != null) {
            actualizado.setPedido_id(dto.getPedido_id());
        }

        if (dto.getProducto() != null && dto.getProducto().getId() != null) {
            Producto productoReal = productoRepository.findById(dto.getProducto().getId())
                    .orElseThrow(() -> new RuntimeException("El producto especificado no existe"));
            
            actualizado.setProducto(productoReal);
        }

        ItemPedido itemGuardado = itemPedidoRepository.save(actualizado);

        return convertirDto(itemGuardado);
    }

    public Integer obtenerTotalUnidadesPorPedido(Integer pedidoId) {
        Integer total =  itemPedidoRepository.sumCantidadByPedidoId(pedidoId);
        return total != null ? total : 0;
    }
}
