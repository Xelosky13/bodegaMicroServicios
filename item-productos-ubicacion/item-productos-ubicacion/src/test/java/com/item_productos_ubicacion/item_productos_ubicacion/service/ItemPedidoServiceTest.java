package com.item_productos_ubicacion.item_productos_ubicacion.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.item_productos_ubicacion.item_productos_ubicacion.DTO.ItemPedidoDTO;
import com.item_productos_ubicacion.item_productos_ubicacion.model.ItemPedido;
import com.item_productos_ubicacion.item_productos_ubicacion.model.Producto;
import com.item_productos_ubicacion.item_productos_ubicacion.repository.ItemPedidoRepository;

import jakarta.inject.Inject;
import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
public class ItemPedidoServiceTest {
    
    @Mock
    private ItemPedidoRepository itemPedidoRepository;

    @InjectMocks
    private ItemPedidoService itemPedidoService;

    private Faker faker = new Faker();

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testBuscarItemPedidopPorId_Exitoso(){
        Integer idSimulado = 1;
        Integer cantidadAleatoria = faker.number().numberBetween(1, 100);
        Integer pedidoIdFalso = faker.number().numberBetween(500, 999);

        Producto productoFalso = Producto.builder()
            .id(10)
            .nombre(faker.commerce().productName())
            .sku(faker.code().asin())
            .build();
        
        ItemPedido itemFalso = ItemPedido.builder()
            .id(idSimulado)
            .cantidad(cantidadAleatoria)
            .pedido_id(pedidoIdFalso)
            .producto(productoFalso)
            .build();

        when(itemPedidoRepository.findById(idSimulado)).thenReturn(Optional.of(itemFalso));

        ItemPedidoDTO resultado = itemPedidoService.buscarPorId(idSimulado);

        assertNotNull(resultado, "El DTO resultante no deberia  ser nulo");
        assertEquals(cantidadAleatoria, resultado.getCantidad(), "La cantidad debe coincidir ");
        assertEquals(pedidoIdFalso, resultado.getPedido_id(), "El id pedido externo debe guardarse correctamente");
        
        verify(itemPedidoRepository, times(1)).findById(idSimulado);
    }
}
