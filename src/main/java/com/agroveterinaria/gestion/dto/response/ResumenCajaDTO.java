package com.agroveterinaria.gestion.dto.response;

import com.agroveterinaria.gestion.model.ArqueoCaja.EstadoCaja;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ResumenCajaDTO {
    private Long id;
    private LocalDateTime fechaApertura;
    private EstadoCaja estado;

    // Para el vendedor esto vendrá en null (Cierre Ciego)
    // Para el admin vendrá con datos.
    private BigDecimal totalVendidoEfectivo;
    private BigDecimal totalVendidoDigital;
    private BigDecimal totalSalidasFletes;

    private BigDecimal saldoEsperadoEnCajon;
}