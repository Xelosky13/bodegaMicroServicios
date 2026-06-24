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

import com.item_productos_ubicacion.item_productos_ubicacion.DTO.UbicacionDTO;
import com.item_productos_ubicacion.item_productos_ubicacion.model.Producto;
import com.item_productos_ubicacion.item_productos_ubicacion.model.Ubicacion;
import com.item_productos_ubicacion.item_productos_ubicacion.repository.UbicacionRepository;

import jakarta.persistence.EntityNotFoundException;


@ExtendWith(MockitoExtension.class)
public class UbicacionServiceTest {
    @Mock
    private UbicacionRepository ubicacionRepository;

    @InjectMocks
    private UbicacionService ubicacionService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConvertirDto_ConProducto(){
        Ubicacion ubi = new Ubicacion();
        ubi.setId(1);
        ubi.setPasillo(12);
        ubi.setEstante(3);
        ubi.setDescripcion("Pasillo electronica");

        Producto p1 = new Producto();
        p1.setId(101);
        p1.setNombre("Mouse Gamer");
        p1.setSku("MSE-01");
        p1.setStock_actual(20);

        ubi.setProductos(List.of(p1));

        UbicacionDTO resultado = ubicacionService.convertirDto(ubi);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Pasillo electronica", resultado.getDescripcion());
        assertNotNull(resultado.getProductos());
        assertEquals(1, resultado.getProductos().size());
        assertEquals("Mouse Gamer", resultado.getProductos().get(0).getNombre());
        assertNull(resultado.getProductos().get(0).getUbicacion(), "La ubicacion dentro del producto DTO debe ser null para evitar recursion");
    }

    @Test
    void testObtenerPorId_Exitoso(){
        Integer idSimulado = 10;
        Ubicacion ubi = new Ubicacion();
        ubi.setId(idSimulado);
        ubi.setPasillo(3);

        when(ubicacionRepository.findById(idSimulado)).thenReturn(Optional.of(ubi));

        UbicacionDTO resultado = ubicacionService.obtenerPorId(idSimulado);

        assertNotNull(resultado);
        assertEquals(idSimulado, resultado.getId());
        verify(ubicacionRepository, times(1)).findById(idSimulado);
    }

    @Test
    void testObtenerPorId_NoEncontrado_LanzaRuntimeException(){
        Integer idInexistente = 99;
        when(ubicacionRepository.findById(idInexistente)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ubicacionService.obtenerPorId(idInexistente);
        });

        assertEquals("Ubicacion no encontrada", exception.getMessage());
    }

    @Test
    void testObtenerTodas_Exitoso(){
        List<Ubicacion> lista = new ArrayList<>();
        Ubicacion u1 = new Ubicacion(); u1.setId(1);
        Ubicacion u2 = new Ubicacion(); u2.setId(2);
        lista.add(u1);
        lista.add(u2);

        when(ubicacionRepository.findAll()).thenReturn(lista);

        List<UbicacionDTO> resultado = ubicacionService.obtenerTodas();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(ubicacionRepository, times(1)).findAll();
    }

    @Test
    void testGuardarUbicacion_Exitoso(){
        UbicacionDTO ubiDto = new UbicacionDTO();
        ubiDto.setPasillo(44);
        ubiDto.setEstante(5);
        ubiDto.setDescripcion("Zona Linea Blanca");

        Ubicacion guardada = new Ubicacion();
        guardada.setId(50);
        guardada.setPasillo(44);
        guardada.setEstante(5);
        guardada.setDescripcion("Zona Linea Blanca");

        when(ubicacionRepository.save(any(Ubicacion.class))).thenReturn(guardada);

        UbicacionDTO resultado = ubicacionService.guardarUbicacion(ubiDto);

        assertNotNull(resultado);
        assertEquals(50, resultado.getId());
        assertEquals(44, resultado.getPasillo());
        verify(ubicacionRepository, times(1)).save(any(Ubicacion.class));
    }

    @Test
    void testEliminar_Exitoso(){
        Integer idEliminar = 7;
        Ubicacion ubi = new Ubicacion();
        ubi.setId(idEliminar);

        when(ubicacionRepository.findById(idEliminar)).thenReturn(Optional.of(ubi));
         
        ubicacionService.eliminar(idEliminar);

        verify(ubicacionRepository, times(1)).delete(ubi);
    }

    @Test
    void testEliminar_NoEncontrado_LanzaEntityNotFoundException(){
        Integer idInexistente = 888;
        when(ubicacionRepository.findById(idInexistente)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            ubicacionService.eliminar(idInexistente);
        });

        assertEquals("Producto no encontrado", exception.getMessage());
    }

    @Test
    void testActualizarUbicacion_Exitoso(){
        Integer idActulizar = 5;
        Ubicacion ubiOriginal = new Ubicacion();
        ubiOriginal.setId(idActulizar);
        ubiOriginal.setPasillo(1);
        ubiOriginal.setEstante(1);

        UbicacionDTO ubiDto = new UbicacionDTO();
        ubiDto.setPasillo(2);
        ubiDto.setEstante(4);
        ubiDto.setDescripcion("Modificado");

        Ubicacion actualizada = new Ubicacion();
        actualizada.setId(idActulizar);
        actualizada.setPasillo(2);
        actualizada.setEstante(4);
        actualizada.setDescripcion("Modificado");

        when(ubicacionRepository.findById(idActulizar)).thenReturn(Optional.of(ubiOriginal));
        when(ubicacionRepository.save(any(Ubicacion.class))).thenReturn(actualizada);

        UbicacionDTO resultado = ubicacionService.actualizarUbicacion(idActulizar, ubiDto);

        assertNotNull(actualizada);
        assertEquals(2, resultado.getPasillo());
        assertEquals(4, resultado.getEstante());
        assertEquals("Modificado", resultado.getDescripcion());
        verify(ubicacionRepository, times(1)).save(any(Ubicacion.class));
    }

    @Test
    void testActualizarUbicacion_NoExiste_LanzaRuntimeException(){
        Integer idInexistente = 123;
        UbicacionDTO dto = new UbicacionDTO();

        when(ubicacionRepository.findById(idInexistente)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ubicacionService.actualizarUbicacion(idInexistente, dto);
        });

        assertEquals("La ubicación con ID " + idInexistente + " no existe", exception.getMessage());
    }
}