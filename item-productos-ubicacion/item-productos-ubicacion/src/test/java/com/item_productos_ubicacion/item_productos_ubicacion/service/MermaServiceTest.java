package com.item_productos_ubicacion.item_productos_ubicacion.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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

import com.item_productos_ubicacion.item_productos_ubicacion.DTO.MermaDTO;
import com.item_productos_ubicacion.item_productos_ubicacion.DTO.ProductoDTO;
import com.item_productos_ubicacion.item_productos_ubicacion.model.Merma;
import com.item_productos_ubicacion.item_productos_ubicacion.model.Producto;
import com.item_productos_ubicacion.item_productos_ubicacion.model.Ubicacion;
import com.item_productos_ubicacion.item_productos_ubicacion.repository.MermaRepository;
import com.item_productos_ubicacion.item_productos_ubicacion.repository.ProductoRepository;

import jakarta.persistence.EntityNotFoundException;
import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
public class MermaServiceTest {

    @Mock
    private MermaRepository mermaRepository;

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private MermaService mermaService;

    private Faker faker = new Faker();

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(faker);
    }

    @Test
    void testConvertirDto_Completo(){
        Ubicacion ubi = new Ubicacion();
        ubi.setId(10);
        ubi.setPasillo(5);
        ubi.setEstante(2);
        ubi.setDescripcion("Pasillo central");

        Producto prod = new Producto();
        prod.setId(1);
        prod.setNombre("Parlante");
        prod.setSku("PAR-001");
        prod.setStock_actual(15);
        prod.setUbicacion(ubi);

        Merma merma = new Merma();
        merma.setId(100);
        merma.setCantidad(5);
        merma.setMotivo("Caja Rota");
        merma.setFechaReporte(LocalDate.now());
        merma.setProducto(prod);

        MermaDTO resultado = mermaService.convertirDto(merma);
        
        assertNotNull(resultado);
        assertEquals(100, resultado.getId());
        assertEquals("Caja Rota", resultado.getMotivo());
        assertNotNull(resultado.getProducto());
        assertEquals("Parlante", resultado.getProducto().getNombre());
        assertNotNull(resultado.getProducto().getUbicacion());
        assertEquals(5, resultado.getProducto().getUbicacion().getPasillo());   
    }

    @Test
    void testeConvertirDto_ProductoSinUbicacion(){
        Producto prod = new Producto();
        prod.setId(2);
        prod.setNombre("Teclado");
        prod.setUbicacion(null);

        Merma merma = new Merma();
        merma.setId(200);
        merma.setProducto(prod);

        MermaDTO resultado = mermaService.convertirDto(merma);

        assertNotNull(resultado);
        assertNotNull(resultado.getProducto());
        assertNull(resultado.getProducto().getUbicacion(), "Si el producto no tiene ubicación, el DTO de ubicación debe ser null");       
    }

    @Test
    void testConvertirDto_SinProducto(){
        Merma merma = new Merma();
        merma.setId(300);
        merma.setProducto(null);

        MermaDTO resultado = mermaService.convertirDto(merma);

        assertNotNull(resultado);
        assertNull(resultado.getProducto(), "Si la merma no tiene producto, el DTO de producto debe ser null");
    }

    @Test
    void testListarTodos_Exito(){
        List<Merma> mermasFalsas = new ArrayList<>();
        Merma m1 = new Merma(); m1.setId(1); m1.setMotivo("Vencimiento");
        Merma m2 = new Merma(); m2.setId(2); m1.setMotivo("Humedad");
        mermasFalsas.add(m1);
        mermasFalsas.add(m2);

        when(mermaRepository.findAll()).thenReturn(mermasFalsas);

        List<MermaDTO> resultado = mermaService.listarTodos();

        assertNotNull(resultado);
        assertEquals(2 , resultado.size());
        verify(mermaRepository, times(1)).findAll();
    }

    @Test
    void testObtenerPorId_Exitoso(){
        Integer idSimulado = 45;
        Merma mermaFalsa = new Merma();
        mermaFalsa.setId(idSimulado);
        mermaFalsa.setMotivo(faker.lorem().sentence());

        when(mermaRepository.findById(idSimulado)).thenReturn(Optional.of(mermaFalsa));

        MermaDTO resultado = mermaService.obtenerPorId(idSimulado);

        assertNotNull(resultado);
        assertEquals(idSimulado, resultado.getId());
        verify(mermaRepository, times(1)).findById(idSimulado);
    }

    @Test
    void testObtenerPorId_NoEncontrado_LanzaRuntimeException(){
        Integer idInexistente = 99;
        when(mermaRepository.findById(idInexistente)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            mermaService.obtenerPorId(idInexistente);
        });

        assertEquals("Merma no encontrada con id: " + idInexistente, exception.getMessage());
    }

    @Test
    void testRegistrar_Exitoso(){
        Integer idProducto = 10;

        ProductoDTO prodDto = new ProductoDTO();
        prodDto.setId(idProducto);

        MermaDTO mermaDto = new MermaDTO();
        mermaDto.setCantidad(5);
        mermaDto.setMotivo("Abollado");
        mermaDto.setFechaReporte(LocalDate.now());
        mermaDto.setProducto(prodDto);

        Producto productoExistente = new Producto();
        productoExistente.setId(idProducto);
        productoExistente.setNombre("Lata de Atun");
        productoExistente.setStock_actual(20);

        Merma mermaGuardada = new Merma();
        mermaGuardada.setId(77);
        mermaGuardada.setCantidad(5);
        mermaGuardada.setMotivo("Abolladura");
        mermaGuardada.setProducto(productoExistente);

        when(productoRepository.findById(idProducto)).thenReturn(Optional.of(productoExistente));
        when(mermaRepository.save(any(Merma.class))).thenReturn(mermaGuardada);

        MermaDTO resultado = mermaService.registrar(mermaDto);

        assertNotNull(resultado);
        assertEquals(77, resultado.getId());
        assertEquals(15, productoExistente.getStock_actual(), "El stock debio bajar de 20 a 15");
    
        verify(productoRepository, times(1)).findById(idProducto);
        verify(productoRepository, times(1)).save(productoExistente);
        verify(mermaRepository, times(1)).save(any(Merma.class));    
    }

    @Test
    void testRegistrar_ProductoNoExiste_LanzaRuntimeException(){
        Integer idInexistente = 404;
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setId(idInexistente);

        MermaDTO mermaDTO = new MermaDTO();
        mermaDTO.setProducto(productoDTO);

        when(productoRepository.findById(idInexistente)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            mermaService.registrar(mermaDTO);
        });

        assertEquals("Producto no encontrado", exception.getMessage());
    }

    @Test
    void testRegistrar_StockInsuficiente_LanzaRuntimeException(){
        Integer idProducto = 12;
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setId(idProducto);

        MermaDTO mermaDTO = new MermaDTO();
        mermaDTO.setCantidad(50);
        mermaDTO.setProducto(productoDTO);

        Producto producto = new Producto(); 
        producto.setId(idProducto);
        producto.setStock_actual(10);

        when(productoRepository.findById(idProducto)).thenReturn(Optional.of(producto));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            mermaService.registrar(mermaDTO);
        });

        assertEquals("Stock insuficiente. Stock actual : 10", exception.getMessage());
    }

    @Test
    void testEliminar_Exitoso(){
        Integer idEliminar = 8;
        Merma merma = new Merma();
        merma.setId(idEliminar);

        when(mermaRepository.findById(idEliminar)).thenReturn(Optional.of(merma));

        mermaService.eliminar(idEliminar);

        verify(mermaRepository, times(1)).delete(merma);
    }

    @Test
    void testEliminar_NoEncontrado_LanzaEntityNotFound(){
        Integer idInexistente = 999;
        when(mermaRepository.findById(idInexistente)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            mermaService.eliminar(idInexistente);
        });
        
        assertEquals("Merma no encontrada", exception.getMessage());
    }
}
