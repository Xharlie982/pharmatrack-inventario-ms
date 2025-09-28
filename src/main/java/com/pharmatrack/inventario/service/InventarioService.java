/*
  Lógica de inventario: aplicar movimientos de forma atómica y upsert masivo de stock.
*/
package com.pharmatrack.inventario.service;

import com.pharmatrack.inventario.dto.BulkResult;
import com.pharmatrack.inventario.dto.BulkStockItem;
import com.pharmatrack.inventario.entity.MovimientoStock;
import com.pharmatrack.inventario.entity.ProductoStock;
import com.pharmatrack.inventario.entity.StockId;
import com.pharmatrack.inventario.repo.MovRepo;
import com.pharmatrack.inventario.repo.StockRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class InventarioService {

    private final StockRepo stockRepo;
    private final MovRepo movRepo;

    public InventarioService(StockRepo stockRepo, MovRepo movRepo) {
        this.stockRepo = stockRepo;
        this.movRepo = movRepo;
    }

    @Transactional
    public MovimientoStock aplicarMovimiento(MovimientoStock mov) {
        StockId id = new StockId(mov.getIdSucursal(), mov.getIdProducto());
        ProductoStock st = stockRepo.findById(id).orElseGet(() -> {
            ProductoStock n = new ProductoStock();
            n.setIdSucursal(id.getIdSucursal());
            n.setIdProducto(id.getIdProducto());
            n.setStockDisponible(0);
            n.setPtoReorden(10);
            return n;
        });

        int delta = switch (mov.getTipo()) {
            case IN -> mov.getCantidad();
            case OUT -> -mov.getCantidad();
            case ADJ -> 0;
        };

        int nuevo = (st.getStockDisponible() == null ? 0 : st.getStockDisponible()) + delta;
        if (nuevo < 0) {
            throw new IllegalArgumentException("Stock insuficiente para realizar la salida.");
        }
        st.setStockDisponible(nuevo);
        st.setUpdatedAt(Instant.now());
        stockRepo.save(st);

        mov.setCreatedAt(Instant.now());
        return movRepo.save(mov);
    }

    @Transactional
    public BulkResult upsertStock(List<BulkStockItem> items) {
        int inserted = 0, updated = 0;
        for (BulkStockItem in : items) {
            StockId id = new StockId(in.getIdSucursal(), in.getIdProducto());
            boolean exists = stockRepo.existsById(id);
            ProductoStock st = new ProductoStock();
            st.setIdSucursal(in.getIdSucursal());
            st.setIdProducto(in.getIdProducto());
            st.setStockDisponible(in.getStockDisponible());
            st.setPtoReorden(in.getPtoReorden());
            st.setUpdatedAt(Instant.now());
            stockRepo.save(st);
            if (exists) updated++; else inserted++;
        }
        return new BulkResult(inserted, updated);
    }
}
