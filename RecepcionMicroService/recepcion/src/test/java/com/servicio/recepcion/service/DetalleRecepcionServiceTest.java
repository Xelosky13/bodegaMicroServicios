package com.servicio.recepcion.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.InjectMocks;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.servicio.recepcion.DTO.DetalleRecepcionDTO;
import com.servicio.recepcion.DTO.ProductoExternoDTO;
import com.servicio.recepcion.model.DetalleRecepcion;
import com.servicio.recepcion.model.OrdenRecepcion;
import com.servicio.recepcion.repository.DetalleRecepcionRepository;

@ExtendWith(MockitoExtension.class)
public class DetalleRecepcionServiceTest {
    @Mock
    private DetalleRecepcionRepository repoDetalle;

    @InjectMocks
    private DetalleRecepcionService serviceDetalle;

    @Test
    void buscarPorId() {
        DetalleRecepcion detalle = new DetalleRecepcion();
        ProductoExternoDTO producto = new ProductoExternoDTO();
        producto.setId(1);
        OrdenRecepcion orden = new OrdenRecepcion();
        orden.setId(1);

        detalle.setIdproducto(producto.getId());
        detalle.setOrden(orden);
        detalle.setId(1);
        detalle.setCantidad(10);
        detalle.setEstado("Recibido");

        when(repoDetalle.findById(1))
                .thenReturn(Optional.of(detalle));

        DetalleRecepcionDTO dto = serviceDetalle.buscarPorId(1);

        assertNotNull(dto);
        assertEquals(dto.getCantidad(), 10);
        assertEquals("Recibido", dto.getEstado());
        assertEquals(1, dto.getOrden().getId());
        assertEquals(1, dto.getProducto().getId());
    }

    @Test
    void buscarPorEstado() {

        DetalleRecepcion detalle1 = new DetalleRecepcion();
        detalle1.setCantidad(10);
        detalle1.setEstado("Recibido");

        DetalleRecepcion detalle2 = new DetalleRecepcion();
        detalle2.setCantidad(5);
        detalle2.setEstado("Recibido");

        ArrayList<DetalleRecepcion> list = new ArrayList<>();
        list.add(detalle1);
        list.add(detalle2);

        when(repoDetalle.findByEstado("Recibido"))
                .thenReturn(list);

        List<DetalleRecepcionDTO> resultado = serviceDetalle.buscarPorEstado("Recibido");

        assertEquals(2, resultado.size());
        assertEquals(10, resultado.get(0).getCantidad());
        assertEquals(5, resultado.get(1).getCantidad());
    }

    @Test
    void eliminarDetalle() {
        serviceDetalle.deleteById(1);
        verify(repoDetalle, times(1)).deleteById(1);
    }

    @Test
    void actualizarDetalle() {
        DetalleRecepcion detalle = new DetalleRecepcion();
        OrdenRecepcion orden = new OrdenRecepcion();
        orden.setId(1);
        ProductoExternoDTO producto = new ProductoExternoDTO();
        producto.setId(1);

        detalle.setCantidad(20);
        detalle.setEstado("Cancelado");
        detalle.setOrden(orden);
        detalle.setIdproducto(producto.getId());
        detalle.setId(1);

        DetalleRecepcionDTO nuevoDetalle = new DetalleRecepcionDTO();

        nuevoDetalle.setCantidad(10);
        nuevoDetalle.setEstado("Recibido");
        nuevoDetalle.setOrden(orden);
        nuevoDetalle.setProducto(producto);

        when(repoDetalle.findById(1))
                .thenReturn(Optional.of(detalle));

        when(repoDetalle.save(any(DetalleRecepcion.class)))
                .thenReturn(detalle);

        DetalleRecepcionDTO resultado = serviceDetalle.actualizarDetalleRecepcion(1, nuevoDetalle);

        assertNotNull(resultado);
        assertEquals(10, resultado.getCantidad());
        assertEquals(1, resultado.getOrden().getId());
        assertEquals(1, resultado.getProducto().getId());

        verify(repoDetalle).save(any(DetalleRecepcion.class));
    }

    @Test
    void guardarDetalle() {
        DetalleRecepcionDTO dto = new DetalleRecepcionDTO();
        OrdenRecepcion orden = new OrdenRecepcion();
        orden.setId(1);
        ProductoExternoDTO producto = new ProductoExternoDTO();
        producto.setId(1);

        dto.setCantidad(10);
        dto.setEstado("Recibido");
        dto.setOrden(orden);
        dto.setProducto(producto);

        DetalleRecepcion guardado = new DetalleRecepcion();
        guardado.setId(1);
        guardado.setCantidad(dto.getCantidad());
        guardado.setEstado(dto.getEstado());
        guardado.setOrden(orden);
        guardado.setIdproducto(producto.getId());

        when(repoDetalle.save(org.mockito.ArgumentMatchers.any(DetalleRecepcion.class)))
                .thenReturn(guardado);

        DetalleRecepcionDTO resultado = serviceDetalle.guardarDetalleRecepcion(dto);

        assertNotNull(resultado);
        assertEquals(10, resultado.getCantidad());
        assertEquals(1, dto.getOrden().getId());
        assertEquals(1, dto.getProducto().getId());
    }

}
