package com.item_productos_ubicacion.item_productos_ubicacion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.item_productos_ubicacion.item_productos_ubicacion.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer>    {
    @Query(value = "SELECT * FROM productos WHERE stock_actual <= :limite ORDER BY stock_actual ASC", nativeQuery = true)
    List<Producto> buscarStockCritico(@Param("limite") Integer limite);

    @Modifying
    @Transactional
    @Query(value = "UPDATE productos SET stock_actual = stock_actual - :cantidad WHERE id = :id", nativeQuery = true)
    void actualizarStock(@Param("id") Integer id, @Param("cantidad") Integer cantidad);
}
    