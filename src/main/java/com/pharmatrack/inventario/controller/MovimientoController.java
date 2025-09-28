/*
  MovimientoController: aplica movimientos con @Transactional y valida stock no negativo.
*/
package com.pharmatrack.inventario.controller;

import com.pharmatrack.inventario.dto.MovimientoRequest;
import com.pharmatrack.inventario.entity.MovimientoStock;
import com.pharmatrack.inventario.repo.MovRepo;
import com.pharmatrack.inventario.service.InventarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    private final InventarioService svc;
    private final MovRepo movRepo;

    public MovimientoController(InventarioService svc, MovRepo movRepo) {
        this.svc = svc;
        this.movRepo = movRepo;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovimientoStock crear(@Valid @RequestBody MovimientoRequest req) {
        MovimientoStock m = new MovimientoStock();
        m.setIdSucursal(req.getIdSucursal());
        m.setIdProducto(req.getIdProducto());
        m.setTipo(req.getTipo());
        m.setCantidad(req.getCantidad());
        m.setMotivo(req.getMotivo());
        m.setRefDocumento(req.getRefDocumento());
        return svc.aplicarMovimiento(m);
    }

    @GetMapping
    public List<MovimientoStock> listar(@RequestParam Instant desde,
                                        @RequestParam Instant hasta,
                                        @RequestParam(required=false) Integer sucursal,
                                        @RequestParam(required=false) String producto) {
        return movRepo.filtrar(desde, hasta, sucursal, producto);
    }
}
