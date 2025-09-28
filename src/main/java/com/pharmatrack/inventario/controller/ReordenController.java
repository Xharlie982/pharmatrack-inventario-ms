/*
  ReordenController: sugiere reposiciones cuando stock <= punto de reorden.
*/
package com.pharmatrack.inventario.controller;

import com.pharmatrack.inventario.entity.ProductoStock;
import com.pharmatrack.inventario.repo.StockRepo;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reorden")
public class ReordenController {

    private final StockRepo stockRepo;

    public ReordenController(StockRepo stockRepo) {
        this.stockRepo = stockRepo;
    }

    @GetMapping
    public List<Map<String, Object>> alertas(@RequestParam(required=false) String distrito) {
        List<ProductoStock> lista = (distrito != null && !distrito.isBlank())
                ? stockRepo.searchByProductoAndDistrito(null, distrito)
                : stockRepo.findAll();

        return lista.stream()
                .collect(Collectors.groupingBy(ps -> Map.of(
                        "idSucursal", ps.getIdSucursal(),
                        "idProducto", ps.getIdProducto()
                )))
                .values().stream()
                .map(group -> {
                    ProductoStock ps = group.get(0);
                    boolean alerta = ps.getStockDisponible() != null &&
                                     ps.getPtoReorden() != null &&
                                     ps.getStockDisponible() <= ps.getPtoReorden();
                    return Map.of(
                            "idSucursal", ps.getIdSucursal(),
                            "idProducto", ps.getIdProducto(),
                            "stockDisponible", ps.getStockDisponible(),
                            "ptoReorden", ps.getPtoReorden(),
                            "alerta", alerta
                    );
                })
                .filter(m -> (Boolean)m.get("alerta"))
                .toList();
    }
}
