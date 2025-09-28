/*
  Repo de MovimientoStock con filtro por rango de fechas y opcional sucursal/producto.
*/
package com.pharmatrack.inventario.repo;

import com.pharmatrack.inventario.entity.MovimientoStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface MovRepo extends JpaRepository<MovimientoStock, Long> {

    @Query("select m from MovimientoStock m where " +
           "(m.createdAt between :desde and :hasta) " +
           "and (:suc is null or m.idSucursal = :suc) " +
           "and (:prod is null or m.idProducto = :prod) " +
           "order by m.createdAt desc")
    List<MovimientoStock> filtrar(@Param("desde") Instant desde,
                                  @Param("hasta") Instant hasta,
                                  @Param("suc") Integer sucursal,
                                  @Param("prod") String producto);
}
