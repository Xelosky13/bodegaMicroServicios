package com.servicio.recepcion.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.servicio.recepcion.DTO.OrdenRecepcionDTO;
import com.servicio.recepcion.model.DetalleRecepcion;
import com.servicio.recepcion.model.OrdenRecepcion;
import com.servicio.recepcion.model.Proveedor;
import com.servicio.recepcion.repository.OrdenRecepcionRepository;

@ExtendWith(MockitoExtension.class)
public class OrdenRecepcionServiceTest {

    @Mock
    private OrdenRecepcionRepository repo;
    @InjectMocks
    private OrdenRecepcionService ordenService;

    @Test
    void buscarPorId() {

        OrdenRecepcion orden = new OrdenRecepcion();
        Proveedor prov = new Proveedor();
        prov.setId(1);
        DetalleRecepcion detalle = new DetalleRecepcion();
        detalle.setId(1);

        orden.setDetalles(new ArrayList<>());
        orden.setFechaRecepcion(LocalDate.now());
        orden.setProveedor(prov);
        orden.setId(1);

        when(repo.findById(1))
                .thenReturn(Optional.of(orden));

        OrdenRecepcionDTO dto = ordenService.buscarOrdenPorId(1);

        assertNotNull(dto);
        assertEquals(prov, dto.getProvedor());

    }

    @Test
    void buscarPorProveedor() {
        OrdenRecepcion orden = new OrdenRecepcion();
        Proveedor prov = new Proveedor();
        prov.setId(1);

        orden.setProveedor(prov);
        orden.setId(1);

        when(repo.findByProveedor(1))
                .thenReturn(orden);

        OrdenRecepcionDTO dto = ordenService.buscarPorProveedor(1);

        assertNotNull(dto);
        assertEquals(prov, dto.getProvedor());

    }

    @Test
    void buscarPorFechaRecepcion() {
        OrdenRecepcion orden = new OrdenRecepcion();
        orden.setId(2);
        orden.setFechaRecepcion(LocalDate.of(2026, 6, 1));

        when(repo.findByfechaRecepcion(LocalDate.of(2026, 6, 1)))
                .thenReturn(orden);

        OrdenRecepcionDTO dto = ordenService.buscarPorFechaRecepcion(LocalDate.of(2026, 6, 1));

        assertNotNull(dto);
        assertEquals(LocalDate.of(2026, 6, 1), dto.getFechaRecepcion());
    }

    @Test
    void actualizarOrden() {
        OrdenRecepcion orden = new OrdenRecepcion();
        Proveedor pro = new Proveedor();
        pro.setId(2);
        DetalleRecepcion detalle = new DetalleRecepcion();
        detalle.setId(2);

        orden.setId(2);
        orden.setDetalles(new ArrayList<>());
        orden.setProveedor(pro);
        orden.setFechaRecepcion(LocalDate.of(2026, 6, 1));

        OrdenRecepcionDTO dto = new OrdenRecepcionDTO();

        dto.setDetalles(new ArrayList<>());
        dto.setProvedor(pro);
        dto.setFechaRecepcion(LocalDate.of(2026, 6, 1));

        when(repo.findById(2))
                .thenReturn(Optional.of(orden));

        when(repo.save(any(OrdenRecepcion.class)))
                .thenReturn(orden);

        OrdenRecepcionDTO actualizado = ordenService.actualizarOrden(2, dto);

        assertNotNull(actualizado);
        assertEquals(dto, actualizado);
        assertEquals(LocalDate.of(2026, 6, 1), dto.getFechaRecepcion());

    }

    @Test
    void eliminarDetalle() {
        ordenService.EliminarOrden(1);
        verify(repo, times(1)).deleteById(1);
    }

    @Test
    void guardarOrden() {
        OrdenRecepcionDTO orden = new OrdenRecepcionDTO();
        Proveedor pro = new Proveedor();
        pro.setId(1);
        DetalleRecepcion detalle = new DetalleRecepcion();
        detalle.setId(1);

        orden.setDetalles(new ArrayList<>());
        orden.setFechaRecepcion(LocalDate.of(2026, 6, 1));
        orden.setProvedor(pro);

        OrdenRecepcion ord = new OrdenRecepcion();
        ord.setId(2);
        ord.setFechaRecepcion(orden.getFechaRecepcion());
        ord.setDetalles(orden.getDetalles());
        ord.setProveedor(orden.getProvedor());

        when(repo.save(any(OrdenRecepcion.class)))
                .thenReturn(ord);

        OrdenRecepcionDTO guardado = ordenService.guardarOrden(orden);

        assertNotNull(guardado);
        assertEquals(pro, ord.getProveedor());
        assertEquals(1, detalle.getId());
        assertEquals(LocalDate.of(2026, 6, 1), ord.getFechaRecepcion());

    }

}
