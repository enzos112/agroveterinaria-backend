package com.agroveterinaria.gestion.service.impl;

import com.agroveterinaria.gestion.dto.request.DetalleVentaRequest;
import com.agroveterinaria.gestion.dto.request.PagoVentaRequest;
import com.agroveterinaria.gestion.dto.request.RegistrarVentaDTO;
import com.agroveterinaria.gestion.dto.response.VentaResponseDTO;
import com.agroveterinaria.gestion.model.*;
import com.agroveterinaria.gestion.repository.*;
import com.agroveterinaria.gestion.service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VentaServiceImpl implements VentaService {

    private final VentaRepository ventaRepository;
    private final DetalleVentaRepository detalleVentaRepository; // ¡Créalo! (Interface vacía)
    private final PagoVentaRepository pagoVentaRepository; // ¡Créalo! (Interface vacía)
    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final VarianteProductoRepository varianteRepository;
    private final LoteProductoRepository loteRepository;
    private final ServicioRepository servicioRepository; // ¡Créalo! (Interface vacía)

    @Override
    @Transactional
    public VentaResponseDTO registrarVenta(RegistrarVentaDTO dto) {

        // 1. Obtener Usuario (Por ahora hardcodeado ID 1 hasta que tengamos Login real)
        Usuario vendedor = usuarioRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Usuario vendedor no encontrado"));

        // 2. Crear Cabecera
        Venta venta = new Venta();
        venta.setUsuario(vendedor);
        venta.setFechaVenta(LocalDateTime.now());
        venta.setMontoTotal(dto.getMontoTotal());
        venta.setMontoDescuentoGlobal(dto.getDescuentoGlobal());

        // Manejo de Cliente y Deuda
        BigDecimal totalPagado = dto.getPagos().stream()
                .map(PagoVentaRequest::getMonto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (dto.getClienteId() != null) {
            Cliente cliente = clienteRepository.findById(dto.getClienteId())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
            venta.setCliente(cliente);

            // Si pagó menos del total, aumentar deuda
            if (totalPagado.compareTo(dto.getMontoTotal()) < 0) {
                BigDecimal deudaGenerada = dto.getMontoTotal().subtract(totalPagado);
                cliente.setDeudaActual(cliente.getDeudaActual().add(deudaGenerada));
                clienteRepository.save(cliente);
                venta.setEstado(Venta.EstadoVenta.PENDIENTE_PAGO);
            } else {
                venta.setEstado(Venta.EstadoVenta.PAGADO);
            }
        } else {
            // Venta anónima: Debe pagar completo
            if (totalPagado.compareTo(dto.getMontoTotal()) < 0) {
                throw new RuntimeException("Las ventas anónimas deben pagarse en su totalidad");
            }
            venta.setEstado(Venta.EstadoVenta.PAGADO);
        }

        Venta ventaGuardada = ventaRepository.save(venta);

        // 3. Procesar Detalles y DESCONTAR STOCK
        for (DetalleVentaRequest item : dto.getDetalles()) {
            DetalleVenta detalle = new DetalleVenta();
            detalle.setVenta(ventaGuardada);
            detalle.setTipoItem(item.getTipoItem());
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitarioVenta(item.getPrecioUnitarioPactado());
            detalle.setSubtotal(item.getCantidad().multiply(item.getPrecioUnitarioPactado()));
            detalle.setTipoPrecioAplicado(item.getTipoPrecio());

            if (item.getTipoItem() == DetalleVenta.TipoItem.PRODUCTO) {
                VarianteProducto variante = varianteRepository.findById(item.getVarianteId())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
                detalle.setVariante(variante);

                // --- LÓGICA FIFO PARA DESCONTAR STOCK ---
                descontarStock(variante.getId(), item.getCantidad());

            } else {
                Servicio servicio = servicioRepository.findById(item.getServicioId())
                        .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
                detalle.setServicio(servicio);
            }

            detalleVentaRepository.save(detalle);
        }

        // 4. Registrar Pagos
        for (PagoVentaRequest p : dto.getPagos()) {
            PagoVenta pago = new PagoVenta();
            pago.setVenta(ventaGuardada);
            pago.setMonto(p.getMonto());
            pago.setMetodoPago(p.getMetodoPago());
            pago.setFechaPago(LocalDateTime.now());
            pagoVentaRepository.save(pago);
        }

        // Retornamos un DTO vacío o con ID por ahora (luego mapeamos el completo)
        VentaResponseDTO response = new VentaResponseDTO();
        response.setId(ventaGuardada.getId());
        response.setTotal(ventaGuardada.getMontoTotal());
        return response;
    }

    // Método auxiliar para descontar stock FIFO
    private void descontarStock(Long varianteId, BigDecimal cantidadRequerida) {
        // Traemos los lotes ordenados por fecha de vencimiento (los más viejos primero)
        List<LoteProducto> lotes = loteRepository.findByVarianteIdOrderByFechaVencimientoAsc(varianteId);

        BigDecimal pendiente = cantidadRequerida;

        for (LoteProducto lote : lotes) {
            if (pendiente.compareTo(BigDecimal.ZERO) <= 0) break; // Ya terminamos

            if (lote.getStockActual().compareTo(BigDecimal.ZERO) > 0) {
                if (lote.getStockActual().compareTo(pendiente) >= 0) {
                    // El lote tiene suficiente para cubrir todo lo que falta
                    lote.setStockActual(lote.getStockActual().subtract(pendiente));
                    pendiente = BigDecimal.ZERO;
                } else {
                    // El lote tiene algo, pero no alcanza para todo. Lo vaciamos y seguimos al siguiente.
                    pendiente = pendiente.subtract(lote.getStockActual());
                    lote.setStockActual(BigDecimal.ZERO);
                }
                loteRepository.save(lote);
            }
        }

        if (pendiente.compareTo(BigDecimal.ZERO) > 0) {
            throw new RuntimeException("Stock insuficiente para el producto ID " + varianteId + ". Faltan: " + pendiente);
        }
    }
}