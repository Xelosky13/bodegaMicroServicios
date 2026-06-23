package com.servicio.recepcion.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.servicio.recepcion.DTO.ProveedorDTO;
import com.servicio.recepcion.model.OrdenRecepcion;
import com.servicio.recepcion.model.Proveedor;
import com.servicio.recepcion.repository.OrdenRecepcionRepository;
import com.servicio.recepcion.repository.ProveedorRepository;

@Service
public class ProveedorService {
    @Autowired
    private ProveedorRepository proveRepo;
    @Autowired
    private OrdenRecepcionRepository ordenrepo;

    public ProveedorDTO mapearADTO(Proveedor prov) {
        ProveedorDTO dto = new ProveedorDTO();
        dto.setNombre(prov.getNombre());
        dto.setNombreContacto(prov.getNombreContacto());
        dto.setTelefono(prov.getTelefono());
        dto.setRut(prov.getRut());
        return dto;

    }

    public ProveedorDTO buscarPorId(Integer id) {
        Proveedor prov = null;
        try {
            prov = proveRepo.findById(id).orElse(null);
        } catch (Exception e) {
            System.out.println("ERROR" + e);
        }

        return this.mapearADTO(prov);
    }

    public List<ProveedorDTO> buscarPorNombre(String nombre) {
        List<Proveedor> prov = proveRepo.findByNombre(nombre);

        return prov.stream().map(this::mapearADTO).toList();
    }

    public ProveedorDTO buscarPorRut(String rut) {
        Proveedor prov = proveRepo.findByRut(rut);
        return this.mapearADTO(prov);
    }

    public ProveedorDTO buscarPorOrdenes(Integer id) {
        OrdenRecepcion orden = ordenrepo.findById(id).orElse(null);

        if (orden == null) {
            return null;
        }

        return this.mapearADTO(orden.getProveedor());

    }

    public List<ProveedorDTO> Proveedores() {
        List<Proveedor> provs = proveRepo.findAll();

        return provs.stream().map(this::mapearADTO).toList();
    }

    public ProveedorDTO guardarProveedor(ProveedorDTO dto) {
        Proveedor pro = new Proveedor();
        pro.setNombre(dto.getNombre());
        pro.setNombreContacto(dto.getNombreContacto());
        pro.setRut(dto.getRut());
        pro.setTelefono(dto.getTelefono());
        Proveedor guardado = proveRepo.save(pro);
        return this.mapearADTO(guardado);
    }

    public void eliminarProveedor(Integer id) {
        proveRepo.deleteById(id);
    }

    public void actualizarProveedor(Integer id, Proveedor pro) {
        Proveedor prove = proveRepo.findById(id).orElse(null);

        if (prove != null) {
            prove.setRut(pro.getRut());
            prove.setNombre(pro.getNombre());
            prove.setTelefono(pro.getTelefono());
            prove.setNombreContacto(pro.getNombreContacto());

            proveRepo.save(prove);
        }
    }
}
