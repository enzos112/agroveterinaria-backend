package com.agroveterinaria.gestion.service.impl;

import com.agroveterinaria.gestion.dto.response.DashboardDTO;
import com.agroveterinaria.gestion.repository.GastoRepository;
import com.agroveterinaria.gestion.repository.VarianteProductoRepository;
import com.agroveterinaria.gestion.repository.VentaRepository;
import com.agroveterinaria.gestion.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final VentaRepository ventaRepository;
    private final GastoRepository gastoRepository;
    private final VarianteProductoRepository varianteRepository;

    @Override
    public DashboardDTO obtenerResumenHoy() {
        LocalDateTime inicioDia = LocalDate.now().atStartOfDay();
        LocalDateTime finDia = LocalDate.now().atTime(LocalTime.MAX);

        BigDecimal ventasHoy = ventaRepository.sumarVentasEntre(inicioDia, finDia);
        if (ventasHoy == null) ventasHoy = BigDecimal.ZERO;

        BigDecimal gastosHoy = gastoRepository.sumarGastosEntre(inicioDia, finDia);
        if (gastosHoy == null) gastosHoy = BigDecimal.ZERO;

        BigDecimal dineroEnCaja = ventasHoy.subtract(gastosHoy);

        long cantidadVentas = ventaRepository.countByFechaBetween(inicioDia, finDia);

        // 5. Productos Bajo Stock
        long bajosStock = varianteRepository.count();

        DashboardDTO dto = new DashboardDTO();
        dto.setTotalVentasHoy(ventasHoy);
        dto.setTotalGastosHoy(gastosHoy);
        dto.setDineroEnCaja(dineroEnCaja);
        dto.setCantidadVentasHoy(cantidadVentas);
        dto.setCantidadProductosBajoStock(bajosStock);

        return dto;
    }
}