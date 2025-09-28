/*
  Repo de ProductoStock con búsqueda por producto.
*/
package com.pharmatrack.inventario.repo;

import com.pharmatrack.inventario.entity.ProductoStock;
import com.pharmatrack.inventario.entity.StockId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface StockRepo extends JpaRepository<ProductoStock, StockId> {

    @Query("select ps from ProductoStock ps " +
           "where (:producto is null or ps.idProducto = :producto)")
    List<ProductoStock> searchByProducto(@Param("producto") String producto);

    @Query("select ps from ProductoStock ps " +
           "join Sucursal s on s.idSucursal = ps.idSucursal " +
           "where (:producto is null or ps.idProducto = :producto) " +
           "and (:distrito is null or s.distrito = :distrito)")
    List<ProductoStock> searchByProductoAndDistrito(@Param("producto") String producto,
                                                    @Param("distrito") String distrito);
}
