package com.agroveterinaria.gestion.repository;

import com.agroveterinaria.gestion.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Buscar cliente por DNI/Teléfono/Nombre
    List<Cliente> findByNombreCompletoContainingIgnoreCaseOrTelefonoContaining(String nombre, String telefono);
}