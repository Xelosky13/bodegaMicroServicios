package com.item_productos_ubicacion.item_productos_ubicacion.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

import com.item_productos_ubicacion.item_productos_ubicacion.DTO.ProductoDTO;
import com.item_productos_ubicacion.item_productos_ubicacion.DTO.UbicacionDTO;
import com.item_productos_ubicacion.item_productos_ubicacion.model.Producto;
import com.item_productos_ubicacion.item_productos_ubicacion.model.Ubicacion;
import com.item_productos_ubicacion.item_productos_ubicacion.repository.ProductoRepository;

import jakarta.persistence.EntityNotFoundException;
import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;
    
    private Faker faker = new Faker();

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConvertirDto_ConUbicacion(){
        Ubicacion ubi = new Ubicacion();
        ubi.setId(10);
        ubi.setPasillo(1);
        ubi.setEstante(3);

        Producto prod = new Producto();
        prod.setId(1);
        prod.setNombre("Teclado Mecanico");
        prod.setSku("TEC-123");
        prod.setStock_actual(15);
        prod.setUbicacion(ubi);

        ProductoDTO resultado = productoService.convertirDto(prod);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Teclado Mecanico", resultado.getNombre());
        assertEquals(15, resultado.getStockActual());
        assertNotNull(resultado.getUbicacion());
        assertEquals(1, resultado.getUbicacion().getPasillo());

    }
    
    @Test
    void testConvertirDto_SinUbicacion(){
        Producto prod = new Producto();
        prod.setId(2);
        prod.setNombre("Mouse gamer");
        prod.setStock_actual(0);
        prod.setUbicacion(null);

        ProductoDTO resultado = productoService.convertirDto(prod);

        assertNotNull(resultado);
        assertNull(resultado.getUbicacion(), "Si la ubicacion es null, el DTO no debe tener ubicacion");

    }

    @Test
    void testObtenerPorId_Exitoso(){
        Integer idSimulado = 5;
        Producto prodFalso = new Producto();
        prodFalso.setId(idSimulado);
        prodFalso.setNombre(faker.commerce().productName());
        prodFalso.setSku(faker.code().asin());

        when(productoRepository.findById(idSimulado)).thenReturn(Optional.of(prodFalso));
        
        ProductoDTO resultado = productoService.obtenerPorId(idSimulado);

        assertNotNull(resultado);
        assertEquals(idSimulado, resultado.getId());
        verify(productoRepository, times(1)).findById(idSimulado);        
    }

    @Test
    void testObtenerPorId_NoEncontrado_LanzaRuntimeException(){
        Integer idInexistente = 99;
        when(productoRepository.findById(idInexistente)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productoService.obtenerPorId(idInexistente);
        });

        assertEquals("Producto no encontrado con id: " + idInexistente, exception.getMessage());  
    }

    @Test
    void testListarTodos_Exitoso(){
        List<Producto> productosFalsos = new ArrayList<>();
        Producto p1 = new Producto(); p1.setId(1); p1.setNombre("Producto A");
        Producto p2 = new Producto(); p2.setId(1); p2.setNombre("Producto B");
        productosFalsos.add(p1);
        productosFalsos.add(p2);

        when(productoRepository.findAll()).thenReturn(productosFalsos);

        List<ProductoDTO> resultado = productoService.listarTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(productoRepository, times(1)).findAll();
    }

    @Test
    void testCrear_Exitoso(){
        UbicacionDTO ubiDTO = new UbicacionDTO();
        ubiDTO.setPasillo(33);
        ubiDTO.setEstante(3);
        ubiDTO.setDescripcion("Zona de tecnologia");

        ProductoDTO inputDto = new ProductoDTO();
        inputDto.setNombre("Audifonos Bluetooth");
        inputDto.setSku("AUD-BT");
        inputDto.setStockActual(50);
        inputDto.setUbicacion(ubiDTO);

        Producto productoGuardado = new Producto();
        productoGuardado.setId(100);
        productoGuardado.setNombre("Audifonos Bluetooth");
        productoGuardado.setSku("AUD-BT");
        productoGuardado.setStock_actual(50);

        when(productoRepository.save(any(Producto.class))).thenReturn(productoGuardado);

        ProductoDTO resultado = productoService.crear(inputDto);

        assertNotNull(resultado);
        assertEquals(100, resultado.getId());
        assertEquals("Audifonos Bluetooth", resultado.getNombre());
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    void testCrear_SinUbicacion_LanzaRuntimeException(){
        ProductoDTO inputDto = new ProductoDTO();
        inputDto.setNombre("Laptop");
        inputDto.setUbicacion(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productoService.crear(inputDto);
        });

        assertEquals("Debe indicar la ubicacion", exception.getMessage());
    }

    @Test
    void testActualizar_Exitoso(){
        Integer idActualizar = 1;
        Ubicacion ubiOriginal = new Ubicacion();
        ubiOriginal.setId(10);
        ubiOriginal.setPasillo(1);

        Producto productoOriginal = new Producto();
        productoOriginal.setId(idActualizar);
        productoOriginal.setNombre("Monitor Antiguo");
        productoOriginal.setSku("MON-OLD");
        productoOriginal.setUbicacion(ubiOriginal);

        UbicacionDTO ubiModificaciondDto = new UbicacionDTO();
        ubiModificaciondDto.setPasillo(9);
        ubiModificaciondDto.setEstante(7);

        ProductoDTO dtoModificaciones = new ProductoDTO();
        dtoModificaciones.setNombre("Monitor Gamer 4K");
        dtoModificaciones.setSku("MON-4K");
        dtoModificaciones.setStockActual(5);
        dtoModificaciones.setUbicacion(ubiModificaciondDto);

        Producto productoActualizado = new Producto();
        productoActualizado.setId(idActualizar);
        productoActualizado.setNombre("Monitor Gamer 4K");
        productoActualizado.setSku("MON-4K");
        productoActualizado.setStock_actual(8);
        productoActualizado.setUbicacion(ubiOriginal);

        when(productoRepository.findById(idActualizar)).thenReturn(Optional.of(productoOriginal));
        when(productoRepository.save(any(Producto.class))).thenReturn(productoActualizado);

        ProductoDTO resultado = productoService.actualizar((idActualizar), dtoModificaciones);

        assertNotNull(resultado);
        assertEquals("Monitor Gamer 4K", productoActualizado.getNombre());
        assertEquals(8, resultado.getStockActual());
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    void testActualizar_IdNoExiste_LanzarRuntimeException(){
        Integer idInexistente = 999;
        ProductoDTO dto = new ProductoDTO();

        when(productoRepository.findById(idInexistente)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productoService.actualizar(idInexistente, dto);
        });

        assertEquals("Id no existe", exception.getMessage());
    }

    @Test
    void testActualizar_SinUbicacion_LanzaRuntimeException(){
        Integer idExistente = 2;
        Producto productoOriginal = new Producto();
        productoOriginal.setId(idExistente);

        ProductoDTO dtoSinUbi = new ProductoDTO();
        dtoSinUbi.setUbicacion(null);

        when(productoRepository.findById(idExistente)).thenReturn(Optional.of(productoOriginal));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productoService.actualizar(idExistente, dtoSinUbi);
        });

        assertEquals("La ubicacion es obligatoria", exception.getMessage());
    }

    @Test
    void testEliminar_Exitoso(){
        Integer idEliminar = 7;
        Producto prodExistente = new Producto();

        when(productoRepository.findById(idEliminar)).thenReturn(Optional.of(prodExistente));

        productoService.eliminar(idEliminar);
        verify(productoRepository, times(1)).delete(prodExistente);
    }

    @Test
    void testEliminar_NoEncontrado_LanzaEntityNotFoundException(){
        Integer idInexistente = 88;
        when(productoRepository.findById(idInexistente)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->{
            productoService.eliminar(idInexistente);
        });

        assertEquals("Producto no encontrado", exception.getMessage());
    }
}
