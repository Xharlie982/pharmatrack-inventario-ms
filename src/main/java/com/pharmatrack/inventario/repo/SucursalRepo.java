/*
  Repo de Sucursal con filtros por distrito y estado.
*/
package com.pharmatrack.inventario.repo;

import com.pharmatrack.inventario.entity.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SucursalRepo extends JpaRepository<Sucursal, Integer> {
    List<Sucursal> findByDistritoAndEstado(String distrito, String estado);
    List<Sucursal> findByDistrito(String distrito);
}
