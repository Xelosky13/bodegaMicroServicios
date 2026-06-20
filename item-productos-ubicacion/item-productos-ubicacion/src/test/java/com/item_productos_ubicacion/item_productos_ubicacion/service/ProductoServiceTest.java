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

import com.item_productos_ubicacion.item_productos_ubicacion.DTO.ProductoDTO;
import com.item_productos_ubicacion.item_productos_ubicacion.model.Producto;
import com.item_productos_ubicacion.item_productos_ubicacion.model.Ubicacion;
import com.item_productos_ubicacion.item_productos_ubicacion.repository.ProductoRepository;

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
    void testBuscarPorId_Exitoso(){
        Integer idSimulado = 1;
        String nombreAleatorio = faker.commerce().productName();
        String  skuAleatorio = faker.code().asin();
        Integer stockAleatorio = faker.number().numberBetween(1, 100);

        Ubicacion ubicacionFalsa = new Ubicacion();
        ubicacionFalsa.setId(1);

        Producto productoFalso = Producto.builder()
            .id(idSimulado)
            .nombre(nombreAleatorio)
            .sku(skuAleatorio)
            .stock_actual(stockAleatorio)
            .ubicacion(ubicacionFalsa)
            .build();

        when(productoRepository.findById(idSimulado)).thenReturn(Optional.of(productoFalso));

        ProductoDTO resultado = productoService.obtenerPorId(idSimulado);
        
        assertNotNull(resultado, "El DTO no deberia ser nulo");
        assertEquals(nombreAleatorio, resultado.getNombre(), "El nombre debe coincidir");
        assertEquals(skuAleatorio, resultado.getSku(), "El SKU debe coincidir");

        verify(productoRepository, times(1)).findById(idSimulado);

    }

}
