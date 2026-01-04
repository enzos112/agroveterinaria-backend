package com.agroveterinaria.gestion.service.impl;

import com.agroveterinaria.gestion.dto.request.CrearProductoDTO;
import com.agroveterinaria.gestion.dto.request.CrearVarianteDTO;
import com.agroveterinaria.gestion.dto.response.ProductoResponseDTO;
import com.agroveterinaria.gestion.dto.response.VarianteResponseDTO;
import com.agroveterinaria.gestion.model.Categoria;
import com.agroveterinaria.gestion.model.LoteProducto;
import com.agroveterinaria.gestion.model.Producto;
import com.agroveterinaria.gestion.model.VarianteProducto;
import com.agroveterinaria.gestion.repository.CategoriaRepository;
import com.agroveterinaria.gestion.repository.LoteProductoRepository;
import com.agroveterinaria.gestion.repository.ProductoRepository;
import com.agroveterinaria.gestion.repository.VarianteProductoRepository;
import com.agroveterinaria.gestion.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // Inyección de dependencias por constructor automática
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final VarianteProductoRepository varianteRepository;
    private final CategoriaRepository categoriaRepository;
    private final LoteProductoRepository loteRepository;

    @Override
    @Transactional // Si falla algo, hace rollback de todo
    public ProductoResponseDTO registrarProducto(CrearProductoDTO dto) {
        // 1. Validar Categoría
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        // 2. Crear Entidad
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setTieneVencimiento(dto.getTieneVencimiento());
        producto.setCategoria(categoria);

        // 3. Guardar en BD
        Producto guardado = productoRepository.save(producto);

        // 4. Convertir a DTO Response
        return mapToDTO(guardado);
    }

    @Override
    @Transactional
    public ProductoResponseDTO agregarVariante(CrearVarianteDTO dto) {
        // 1. Buscar al padre
        Producto producto = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto padre no encontrado"));

        // 2. Crear la variante (La presentación física)
        VarianteProducto variante = new VarianteProducto();
        variante.setProducto(producto);
        variante.setNombreVariante(dto.getNombreVariante());
        variante.setUnidadMedida(dto.getUnidadMedida());
        variante.setPrecioVentaPublico(dto.getPrecioVentaPublico());
        variante.setPrecioVentaMayorista(dto.getPrecioVentaMayorista());
        variante.setStockMinimoAlerta(dto.getStockMinimoAlerta());

        // Lógica de "Abrir Saco"
        variante.setSePuedeAbrir(dto.isSePuedeAbrir());
        if (dto.getFactorConversion() != null) {
            variante.setFactorConversion(dto.getFactorConversion());
        }

        varianteRepository.save(variante);

        // Retornamos el producto padre actualizado con su nueva variante
        return mapToDTO(producto);
    }

    @Override
    public List<ProductoResponseDTO> listarTodos() {
        return productoRepository.findAll().stream()
                .map(this::mapToDTO) // Usamos el método helper de abajo
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductoResponseDTO> buscarPorNombre(String termino) {
        return productoRepository.findByNombreContainingIgnoreCase(termino).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ==========================================
    // MÉTODOS PRIVADOS (Mappers manuales)
    // ==========================================

    private ProductoResponseDTO mapToDTO(Producto entidad) {
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setId(entidad.getId());
        dto.setNombre(entidad.getNombre());
        dto.setCategoria(entidad.getCategoria().getNombre());
        dto.setTieneVencimiento(entidad.isTieneVencimiento());

        List<VarianteProducto> variantes = varianteRepository.findByProductoId(entidad.getId());

        List<VarianteResponseDTO> variantesDTO = variantes.stream()
                .map(this::mapVarianteToDTO)
                .collect(Collectors.toList());

        dto.setPresentaciones(variantesDTO);
        return dto;
    }

    private VarianteResponseDTO mapVarianteToDTO(VarianteProducto v) {
        VarianteResponseDTO dto = new VarianteResponseDTO();
        dto.setId(v.getId());
        dto.setNombreCompleto(v.getProducto().getNombre() + " - " + v.getNombreVariante());
        dto.setPrecioVenta(v.getPrecioVentaPublico());
        dto.setUnidad(v.getUnidadMedida());

        // 2. OBTENER STOCK REAL DESDE BD
        BigDecimal stockTotal = loteRepository.obtenerStockTotal(v.getId());
        dto.setStockActual(stockTotal);

        // 3. LOGICA DEL SEMÁFORO (Alertas)

        // Alerta Amarilla: ¿Es bajo stock?
        // Si stockTotal <= stockMinimoAlerta, entonces es true
        boolean esBajoStock = stockTotal.compareTo(v.getStockMinimoAlerta()) <= 0;
        dto.setStockBajo(esBajoStock);

        // Alerta Roja: Fecha de vencimiento más próxima
        if (v.getProducto().isTieneVencimiento()) {
            // Buscamos el primer lote con stock > 0 ordenado por fecha
            LoteProducto loteMasViejo = loteRepository
                    .findFirstByVarianteIdAndStockActualGreaterThanOrderByFechaVencimientoAsc(v.getId(), BigDecimal.ZERO);

            if (loteMasViejo != null) {
                dto.setFechaVencimientoMasProxima(loteMasViejo.getFechaVencimiento());
            }
        }

        return dto;
    }
}