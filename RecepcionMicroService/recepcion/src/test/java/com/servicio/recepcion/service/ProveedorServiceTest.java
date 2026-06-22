package com.servicio.recepcion.service;

import com.servicio.recepcion.DTO.ProveedorDTO;
import com.servicio.recepcion.model.Proveedor;
import com.servicio.recepcion.repository.ProveedorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProveedorServiceTest {
    @Mock
    private ProveedorRepository proveRepo;

    @InjectMocks
    public ProveedorService proveedorService;

    @Test
    void buscarPorId() {
        Proveedor proveedor = new Proveedor();
        proveedor.setId(1);
        proveedor.setNombre("Proveedor ABC");
        proveedor.setRut("11111111-1");
        proveedor.setTelefono("111111111");
        proveedor.setNombreContacto("Juan Perez");

        when(proveRepo.findById(1))
                .thenReturn(Optional.of(proveedor));

        ProveedorDTO resultado = proveedorService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals("Proveedor ABC", resultado.getNombre());
        assertEquals("11111111-1", resultado.getRut());
        assertEquals("111111111", resultado.getTelefono());
        assertEquals("Juan Perez", resultado.getNombreContacto());
    }

    @Test
    void buscarPorRut() {
        Proveedor proveedor = new Proveedor();
        proveedor.setNombre("Proveedor ABC");
        proveedor.setRut("11111111-1");

        when(proveRepo.findByRut("11111111-1"))
                .thenReturn(proveedor);

        ProveedorDTO resultado = proveedorService.buscarPorRut("11111111-1");

        assertNotNull(resultado);
        assertEquals("Proveedor ABC", resultado.getNombre());
        assertEquals("11111111-1", resultado.getRut());
    }

    @Test
    void guardarProveedor() {
        ProveedorDTO dto = new ProveedorDTO();
        dto.setNombre("Proveedor Nuevo");
        dto.setRut("11111111-1");
        dto.setTelefono("111111111");
        dto.setNombreContacto("Pedro");

        Proveedor guardado = new Proveedor();
        guardado.setId(1);
        guardado.setNombre(dto.getNombre());
        guardado.setRut(dto.getRut());
        guardado.setTelefono(dto.getTelefono());
        guardado.setNombreContacto(dto.getNombreContacto());

        when(proveRepo.save(org.mockito.ArgumentMatchers.any(Proveedor.class)))
                .thenReturn(guardado);

        ProveedorDTO resultado = proveedorService.guardarProveedor(dto);

        assertNotNull(resultado);
        assertEquals("Proveedor Nuevo", resultado.getNombre());
        assertEquals("11111111-1", resultado.getRut());
    }

    @Test
    void eliminarProveedor() {
        proveedorService.eliminarProveedor(1);
        verify(proveRepo, times(1)).deleteById(1);
    }

}
