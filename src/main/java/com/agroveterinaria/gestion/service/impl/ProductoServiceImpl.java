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
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final VarianteProductoRepository varianteRepository;
    private final CategoriaRepository categoriaRepository;
    private final LoteProductoRepository loteRepository;

    @Override
    @Transactional
    public ProductoResponseDTO registrarProducto(CrearProductoDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setTieneVencimiento(dto.getTieneVencimiento());
        producto.setCategoria(categoria);

        producto.setStock(0);
        producto.setStockGranel(0.0);

        Producto guardado = productoRepository.save(producto);
        return mapToDTO(guardado);
    }

    @Override
    @Transactional
    public ProductoResponseDTO agregarVariante(CrearVarianteDTO dto) {
        Producto producto = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto padre no encontrado"));

        VarianteProducto variante = new VarianteProducto();
        variante.setProducto(producto);
        variante.setNombreVariante(dto.getNombreVariante());
        variante.setUnidadMedida(dto.getUnidadMedida());
        variante.setPrecioVentaPublico(dto.getPrecioVentaPublico());
        variante.setPrecioVentaMayorista(dto.getPrecioVentaMayorista());
        variante.setStockMinimoAlerta(dto.getStockMinimoAlerta());

        // Configuramos la variante
        variante.setSePuedeAbrir(dto.isSePuedeAbrir());
        if (dto.getFactorConversion() != null) {
            variante.setFactorConversion(dto.getFactorConversion());
        }

        varianteRepository.save(variante);
        return mapToDTO(producto);
    }

    @Override
    public List<ProductoResponseDTO> listarTodos() {
        return productoRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductoResponseDTO> buscarPorNombre(String termino) {
        return productoRepository.findByNombreContainingIgnoreCase(termino).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Producto abrirUnidad(Long idProducto) {
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (producto.getStock() == null || producto.getStock() < 1) {
            throw new RuntimeException("No hay suficiente stock cerrado para abrir.");
        }
        if (producto.getFactorConversion() == null || producto.getFactorConversion() <= 0) {
            throw new RuntimeException("El producto no tiene configurado el factor de conversión (cuánto trae).");
        }

        producto.setStock(producto.getStock() - 1);

        Double granelActual = (producto.getStockGranel() == null) ? 0.0 : producto.getStockGranel();
        producto.setStockGranel(granelActual + producto.getFactorConversion());

        return productoRepository.save(producto);
    }

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

        BigDecimal stockTotal = loteRepository.obtenerStockTotal(v.getId());

        if (stockTotal == null) stockTotal = BigDecimal.ZERO;

        dto.setStockActual(stockTotal);

        boolean esBajoStock = stockTotal.compareTo(v.getStockMinimoAlerta()) <= 0;
        dto.setStockBajo(esBajoStock);

        if (v.getProducto().isTieneVencimiento()) {
            LoteProducto loteMasViejo = loteRepository
                    .findFirstByVarianteIdAndStockActualGreaterThanOrderByFechaVencimientoAsc(v.getId(), BigDecimal.ZERO);

            if (loteMasViejo != null) {
                dto.setFechaVencimientoMasProxima(loteMasViejo.getFechaVencimiento());
            }
        }

        return dto;
    }
}