package com.agroveterinaria.gestion.dto.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class DashboardStatsDTO {
    private BigDecimal ventasHoy;
    private int transaccionesHoy;
    private BigDecimal deudaPorCobrarTotal;
    private long productosPorVencerCount;
    private long productosStockBajoCount;
}