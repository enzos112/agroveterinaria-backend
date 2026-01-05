package com.agroveterinaria.gestion.dto.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class DashboardDTO {
    private BigDecimal totalVentasHoy;
    private BigDecimal totalGastosHoy;
    private BigDecimal dineroEnCaja;
    private Long cantidadProductosBajoStock;
    private Long cantidadVentasHoy;
}