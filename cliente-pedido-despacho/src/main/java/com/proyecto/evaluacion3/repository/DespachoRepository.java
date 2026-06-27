package com.proyecto.evaluacion3.repository;

import com.proyecto.evaluacion3.model.Despacho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DespachoRepository extends JpaRepository<Despacho, Integer> {
}
