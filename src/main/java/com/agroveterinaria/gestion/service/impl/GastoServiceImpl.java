package com.agroveterinaria.gestion.service.impl;

import com.agroveterinaria.gestion.model.Gasto;
import com.agroveterinaria.gestion.repository.GastoRepository;
import com.agroveterinaria.gestion.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GastoServiceImpl {

    private final GastoRepository gastoRepository;
    private final UsuarioRepository usuarioRepository;

    public List<Gasto> listarGastos() {
        return gastoRepository.findByOrderByFechaDesc();
    }

    @Transactional
    public Gasto registrarGasto(Gasto gasto, Long usuarioId) {
        if (usuarioId != null) {
            var usuario = usuarioRepository.findById(usuarioId)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            gasto.setUsuario(usuario);
        }
        return gastoRepository.save(gasto);
    }

    public void eliminarGasto(Long id) {
        gastoRepository.deleteById(id);
    }
}