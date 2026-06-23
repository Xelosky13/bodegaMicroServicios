package com.servicio.recepcion.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.servicio.recepcion.model.DetalleRecepcion;

public interface DetalleRecepcionRepository extends JpaRepository<DetalleRecepcion, Integer> {

    public List<DetalleRecepcion> findByEstado(String estado);

    public DetalleRecepcion findByIdproducto(Integer id_producto);

    @Query("SELECT d from DetalleRecepcion d WHERE d.estado=?1")
    public ArrayList<DetalleRecepcion> buscarDetallePorEstado(String estado);

    @Query("SELECT d FROM DetalleRecepcion d WHERE d.cantidad>?1")
    public ArrayList<DetalleRecepcion> buscarDetallePorCantidad(Integer cantidad);

}
