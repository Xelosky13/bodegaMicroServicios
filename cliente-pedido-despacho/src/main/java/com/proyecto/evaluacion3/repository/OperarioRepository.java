package com.proyecto.evaluacion3.repository;

import com.proyecto.evaluacion3.model.Operario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperarioRepository extends JpaRepository<Operario, Integer> {
}
