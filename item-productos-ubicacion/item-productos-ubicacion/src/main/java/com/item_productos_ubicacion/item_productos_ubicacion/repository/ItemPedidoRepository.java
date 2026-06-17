package com.item_productos_ubicacion.item_productos_ubicacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.item_productos_ubicacion.item_productos_ubicacion.model.ItemPedido;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {
    @Query("SELECT COALESCE(SUM(i.cantidad), 0) FROM ItemPedido i WHERE i.pedido_id = :pedidoId")
    Integer sumCantidadByPedidoId(@Param("pedidoId") Integer pedidoId);
}
