/*
  StockController: búsqueda, y upsert masivo /stock:bulk.
*/
package com.pharmatrack.inventario.controller;

import com.pharmatrack.inventario.dto.BulkResult;
import com.pharmatrack.inventario.dto.BulkStockItem;
import com.pharmatrack.inventario.entity.ProductoStock;
import com.pharmatrack.inventario.repo.StockRepo;
import com.pharmatrack.inventario.service.InventarioService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stock")
public class StockController {

    private final InventarioService svc;
    private final StockRepo stockRepo;

    public StockController(InventarioService svc, StockRepo stockRepo) {
        this.svc = svc;
        this.stockRepo = stockRepo;
    }

    @GetMapping
    public List<ProductoStock> search(@RequestParam(required=false) String producto,
                                      @RequestParam(required=false) String distrito) {
        if (distrito != null && !distrito.isBlank()) {
            return stockRepo.searchByProductoAndDistrito(producto, distrito);
        }
        return stockRepo.searchByProducto(producto);
    }

    @PutMapping("/bulk")
    public Map<String, Object> bulk(@Valid @RequestBody List<BulkStockItem> body) {
        BulkResult r = svc.upsertStock(body);
        return Map.of("inserted", r.getInserted(), "updated", r.getUpdated());
    }

    @GetMapping("/{idSucursal}/{idProducto}")
    public ProductoStock one(@PathVariable Integer idSucursal, @PathVariable String idProducto) {
        return stockRepo.findById(new com.pharmatrack.inventario.entity.StockId(idSucursal, idProducto)).orElse(null);
    }
}
