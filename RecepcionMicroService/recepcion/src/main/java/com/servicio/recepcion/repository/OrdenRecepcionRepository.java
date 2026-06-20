package com.servicio.recepcion.repository;

import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.servicio.recepcion.model.OrdenRecepcion;

@Repository
public interface OrdenRecepcionRepository extends JpaRepository<OrdenRecepcion, Integer> {

    public OrdenRecepcion findByfechaRecepcion(LocalDate fechaRecepcion);

    public OrdenRecepcion findByProveedor(Integer Id);

}
