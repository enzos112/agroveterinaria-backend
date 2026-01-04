package com.agroveterinaria.gestion.repository;

import com.agroveterinaria.gestion.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}