package com.proyecto.evaluacion3.repository;

import com.proyecto.evaluacion3.model.Picking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PickingRepository extends JpaRepository<Picking, Integer> {
}

