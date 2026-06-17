package com.item_productos_ubicacion.item_productos_ubicacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.item_productos_ubicacion.item_productos_ubicacion.model.Ubicacion;

@Repository
public interface UbicacionRepository extends JpaRepository<Ubicacion, Integer> {
    public Ubicacion findByPasillo(Integer pasillo);

    public Ubicacion findByEstante(Integer estante);
}
