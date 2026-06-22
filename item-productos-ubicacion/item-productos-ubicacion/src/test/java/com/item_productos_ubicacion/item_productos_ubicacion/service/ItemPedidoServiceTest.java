package com.item_productos_ubicacion.item_productos_ubicacion.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.item_productos_ubicacion.item_productos_ubicacion.DTO.ItemPedidoDTO;
import com.item_productos_ubicacion.item_productos_ubicacion.DTO.ProductoDTO;
import com.item_productos_ubicacion.item_productos_ubicacion.model.ItemPedido;
import com.item_productos_ubicacion.item_productos_ubicacion.model.Producto;
import com.item_productos_ubicacion.item_productos_ubicacion.repository.ItemPedidoRepository;
import com.item_productos_ubicacion.item_productos_ubicacion.repository.ProductoRepository;

import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
public class ItemPedidoServiceTest {
    @Mock
    private ItemPedidoRepository itemPedidoRepository;

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private ProductoService productoService;

    @Mock
    private ItemPedidoValidaciones itemPedidoValidaciones;

    @InjectMocks
    private ItemPedidoService itemPedidoService;

    private Faker faker = new Faker();

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerTodos_Exitoso(){
        List<ItemPedido> listaFalsa = new ArrayList<>();
        Producto p = Producto.builder().id(1).nombre(faker.commerce().productName()).sku(faker.code().asin()).build();
        listaFalsa.add(ItemPedido.builder().id(1).cantidad(2).pedido_id(10).producto(p).build());
        listaFalsa.add(ItemPedido.builder().id(2).cantidad(5).pedido_id(11).producto(p).build());

        when(itemPedidoRepository.findAll()).thenReturn(listaFalsa);

        List<ItemPedidoDTO> resultado = itemPedidoService.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size(), "Deberia retornar exactamente los 2 items");
        verify(itemPedidoRepository, times(1)).findAll();
    }

    @Test
    void testBuscarPorId_Exitoso(){
        Integer idSimulado = 1;
        Producto p = Producto.builder().id(1).nombre(faker.commerce().productName()).sku(faker.code().asin()).build();
        ItemPedido itemFalso = ItemPedido.builder().id(idSimulado).cantidad(12).pedido_id(88).producto(p).build();
        
        when(itemPedidoRepository.findById(idSimulado)).thenReturn(Optional.of(itemFalso));
        
        ItemPedidoDTO resultado = itemPedidoService.buscarPorId(idSimulado);

        assertNotNull(resultado);
        assertEquals(12, resultado.getCantidad(), "La cantidad mapeada debe ser correcta");
        verify(itemPedidoRepository, times(1)).findById(idSimulado);
    }

    @Test
    void testBuscarPorId_NoEncontrado_LanzaException(){
        Integer idInexistente = 99;
        when(itemPedidoRepository.findById(idInexistente)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->{
            itemPedidoService.buscarPorId(idInexistente);
        },"Deberia lanzar un RuntimeException si el ID no existe");
    }

    @Test
    void testEliminar_Exitoso(){
        Integer idEliminar = 3;
        ItemPedido itemExistente = ItemPedido.builder().id(idEliminar).cantidad(1).pedido_id(5).build();
        
        when(itemPedidoRepository.findById(idEliminar)).thenReturn(Optional.of(itemExistente));

        String mensaje = itemPedidoService.eliminar(idEliminar);

        assertEquals("Item3 eliminado exitosamente", mensaje);
        verify(itemPedidoRepository, times(1)).delete(itemExistente);
    }

    @Test
    void testEliminar_NoEncontrado_RetornaMensajeError(){
        Integer idInexistente = 44;
        when(itemPedidoRepository.findById(idInexistente)).thenReturn(Optional.empty());

        String mensaje = itemPedidoService.eliminar(idInexistente);

        assertEquals("Item de pedido no encontrado", mensaje);
    }

    @Test
    void testGuardasrItemPedido_Exitoso(){
        ProductoDTO pDto = new ProductoDTO();
        pDto.setId(5);

        ItemPedidoDTO dtoInput = new ItemPedidoDTO();
        dtoInput.setCantidad(8);
        dtoInput.setPedido_id(450);
        dtoInput.setProducto(pDto);

        Producto productoReal = Producto.builder().id(5).nombre("Audifonos").sku("AUD-99").build();
        ItemPedido itemGuardado = ItemPedido.builder().id(1).cantidad(8).pedido_id(450).producto(productoReal).build();

        when(productoRepository.findById(5)).thenReturn(Optional.of(productoReal));
        when(itemPedidoRepository.save(any(ItemPedido.class))).thenReturn(itemGuardado);
        when(itemPedidoValidaciones.obtenerPedidoIdExterno(anyInt())).thenReturn(450);

        ItemPedidoDTO resultado = itemPedidoService.guardarItemPedido(dtoInput);
        assertNotNull(resultado);
        assertEquals(8, resultado.getCantidad());
        verify(itemPedidoRepository, times(1)).save(any(ItemPedido.class));
    }

    @Test
    void testGuardarItemPedido_SinProducto_LanzaException(){
        ItemPedidoDTO dtoInput = new ItemPedidoDTO();
        dtoInput.setCantidad(10);
        dtoInput.setProducto(null);
        
        assertThrows(RuntimeException.class, () -> {
            itemPedidoService.guardarItemPedido(dtoInput);
        }, "Deberia lanzar excepcion si no se asoocia un producto");
    }

    @Test
    void testActualizarItemPedidoo_Exitoso(){
        Integer idActualizar = 1;
        ProductoDTO pDto = new ProductoDTO();
        pDto.setId(2);

        ItemPedidoDTO dtoModificado = new ItemPedidoDTO();
        dtoModificado.setCantidad(50);
        dtoModificado.setProducto(pDto);

        Producto produ = Producto.builder().id(2).nombre("Monitor").sku("MON-40").build();
        ItemPedido itemOriginal = ItemPedido.builder().id(idActualizar).cantidad(5).pedido_id(100).producto(produ).build();
        ItemPedido itemGuardado = ItemPedido.builder().id(idActualizar).cantidad(50).pedido_id(100).producto(produ).build();

        when(itemPedidoRepository.findById(idActualizar)).thenReturn(Optional.of(itemOriginal));
        when(productoRepository.findById(2)).thenReturn(Optional.of(produ));
        when(itemPedidoRepository.save(any(ItemPedido.class))).thenReturn(itemGuardado);
        
        ItemPedidoDTO resultado = itemPedidoService.actualizarItemPedido(idActualizar, dtoModificado);

        assertNotNull(resultado);
        assertEquals(50, resultado.getCantidad(), "La cantidad debio subir a 50");
    }

    @Test
    void testObtenerTotalUnidadesPorPedido_Exitoso() {
        Integer pedidoId = 15;
        when(itemPedidoRepository.sumCantidadByPedidoId(pedidoId)).thenReturn(30);

        Integer total = itemPedidoService.obtenerTotalUnidadesPorPedido(pedidoId);

        assertEquals(30, total, "Si el repositorio da null, debe transformarse a 0");
    }
}
