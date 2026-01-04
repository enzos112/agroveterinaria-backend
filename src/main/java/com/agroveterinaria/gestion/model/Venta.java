package com.agroveterinaria.gestion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "ventas")
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario; // Vendedor

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente; // Puede ser null (Venta anónima)

    @NotNull
    private LocalDateTime fechaVenta;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal montoTotal;

    @PositiveOrZero
    @Column(precision = 10, scale = 2)
    private BigDecimal montoDescuentoGlobal = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoVenta estado;

    // Relación bidireccional para facilitar guardado en cascada
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private List<DetalleVenta> detalles;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private List<PagoVenta> pagos;

    public enum EstadoVenta {
        PAGADO, PENDIENTE_PAGO, ANULADO
    }
}