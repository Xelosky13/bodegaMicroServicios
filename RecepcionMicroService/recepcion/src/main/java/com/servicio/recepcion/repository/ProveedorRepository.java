package com.servicio.recepcion.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.servicio.recepcion.model.Proveedor;

@Repository

public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {

    public List<Proveedor> findByNombre(String nombre);

    public Proveedor findByRut(String rut);

}
