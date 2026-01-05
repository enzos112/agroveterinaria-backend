package com.agroveterinaria.gestion.service.impl;

import com.agroveterinaria.gestion.dto.request.DetalleCompraRequest;
import com.agroveterinaria.gestion.dto.request.RegistrarCompraDTO;
import com.agroveterinaria.gestion.model.*;
import com.agroveterinaria.gestion.repository.*;
import com.agroveterinaria.gestion.service.CompraService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CompraServiceImpl implements CompraService {

    private final CompraRepository compraRepository;
    private final DetalleCompraRepository detalleCompraRepository;
    private final ProveedorRepository proveedorRepository;
    private final VarianteProductoRepository varianteRepository;
    private final LoteProductoRepository loteRepository;
    private final PagoProveedorRepository pagoProveedorRepository;

    @Override
    @Transactional
    public Compra registrarCompra(RegistrarCompraDTO dto) {
        // 1. Validar Proveedor
        Proveedor proveedor = proveedorRepository.findById(dto.getProveedorId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        // 2. Crear Cabecera de Compra
        Compra compra = new Compra();
        compra.setProveedor(proveedor);
        compra.setNumeroDocumento(dto.getNumeroDocumento());
        compra.setFechaCompra(dto.getFechaCompra());
        compra.setCostoTotalProductos(dto.getCostoTotalProductos());
        compra.setCostoFlete(dto.getCostoFlete());

        // Lógica de Pago Inicial
        BigDecimal pagado = BigDecimal.ZERO;
        if (dto.getPagoInicial() != null) {
            pagado = dto.getPagoInicial().getMonto();
        }
        compra.setMontoPagado(pagado);

        if (pagado.compareTo(dto.getCostoTotalProductos()) >= 0) {
            compra.setEstadoPago(Compra.EstadoPagoCompra.PAGADO);
        } else if (pagado.compareTo(BigDecimal.ZERO) > 0) {
            compra.setEstadoPago(Compra.EstadoPagoCompra.PARCIAL);
        } else {
            compra.setEstadoPago(Compra.EstadoPagoCompra.DEUDA);
        }

        Compra compraGuardada = compraRepository.save(compra);

        // 3. Guardar Detalles y CREAR LOTES (Stock)
        for (DetalleCompraRequest item : dto.getDetalles()) {
            VarianteProducto variante = varianteRepository.findById(item.getVarianteId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + item.getVarianteId()));

            DetalleCompra detalle = new DetalleCompra();
            detalle.setCompra(compraGuardada);
            detalle.setVariante(variante);
            detalle.setCantidadComprada(item.getCantidadComprada());
            detalle.setCantidadBonificacion(item.getCantidadBonificacion());
            detalle.setCostoUnitario(item.getCostoUnitario());
            detalle.setSubtotal(item.getCantidadComprada().multiply(item.getCostoUnitario()));

            detalleCompraRepository.save(detalle);

            LoteProducto lote = new LoteProducto();
            lote.setVariante(variante);
            lote.setNumeroLote(item.getNumeroLote() != null ? item.getNumeroLote() : "SIN-LOTE-" + LocalDate.now());
            lote.setFechaVencimiento(item.getFechaVencimiento());
            lote.setFechaIngreso(LocalDate.now());

            BigDecimal cantidadTotal = item.getCantidadComprada().add(item.getCantidadBonificacion());
            lote.setStockActual(cantidadTotal);

            loteRepository.save(lote);
        }

        if (dto.getPagoInicial() != null && dto.getPagoInicial().getMonto().compareTo(BigDecimal.ZERO) > 0) {
            PagoProveedor pago = new PagoProveedor();
            pago.setCompra(compraGuardada);
            pago.setMonto(dto.getPagoInicial().getMonto());
            pago.setFechaPago(java.time.LocalDateTime.now());
            pago.setMetodoPago(dto.getPagoInicial().getMetodoPago());

            pagoProveedorRepository.save(pago);
        }

        return compraGuardada;
    }
}