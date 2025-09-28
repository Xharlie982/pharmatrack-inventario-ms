/*
  SucursalController: CRUD mínimo y filtros por distrito/estado.
*/
package com.pharmatrack.inventario.controller;

import com.pharmatrack.inventario.entity.Sucursal;
import com.pharmatrack.inventario.repo.SucursalRepo;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sucursales")
public class SucursalController {

    private final SucursalRepo repo;
    public SucursalController(SucursalRepo repo){ this.repo = repo; }

    @GetMapping
    public List<Sucursal> listar(@RequestParam(required=false) String distrito,
                                 @RequestParam(required=false) String estado) {
        if (distrito != null && estado != null) return repo.findByDistritoAndEstado(distrito, estado);
        if (distrito != null) return repo.findByDistrito(distrito);
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Sucursal get(@PathVariable Integer id){ return repo.findById(id).orElse(null); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Sucursal crear(@Valid @RequestBody Sucursal s){ return repo.save(s); }

    @PatchMapping("/{id}")
    public Sucursal patch(@PathVariable Integer id, @RequestBody Sucursal body){
        return repo.findById(id).map(s -> {
            if (body.getNombre()!=null) s.setNombre(body.getNombre());
            if (body.getDistrito()!=null) s.setDistrito(body.getDistrito());
            if (body.getDireccion()!=null) s.setDireccion(body.getDireccion());
            if (body.getEstado()!=null) s.setEstado(body.getEstado());
            if (body.getLat()!=null) s.setLat(body.getLat());
            if (body.getLon()!=null) s.setLon(body.getLon());
            return repo.save(s);
        }).orElse(null);
    }
}
