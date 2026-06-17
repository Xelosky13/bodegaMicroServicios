package com.item_productos_ubicacion.item_productos_ubicacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.item_productos_ubicacion.item_productos_ubicacion.model.Merma;

@Repository
public interface MermaRepository extends JpaRepository<Merma, Integer> {

}
