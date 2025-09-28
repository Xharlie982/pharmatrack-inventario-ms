/*
  ProductoStock: stock por sucursal y producto (producto viene de Catálogo Mongo).
  Índices: PK compuesto y por idProducto para búsquedas.
*/
package com.pharmatrack.inventario.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "producto_stock",
       indexes = {
         @Index(name="idx_stock_producto", columnList = "idProducto")
       })
@IdClass(StockId.class)
public class ProductoStock {

    @Id
    private Integer idSucursal;

    @Id
    @Column(length = 64)
    private String idProducto;

    private Integer stockDisponible = 0;
    private Integer ptoReorden = 10;

    private Instant updatedAt = Instant.now();

    public Integer getIdSucursal() { return idSucursal; }
    public void setIdSucursal(Integer idSucursal) { this.idSucursal = idSucursal; }
    public String getIdProducto() { return idProducto; }
    public void setIdProducto(String idProducto) { this.idProducto = idProducto; }
    public Integer getStockDisponible() { return stockDisponible; }
    public void setStockDisponible(Integer stockDisponible) { this.stockDisponible = stockDisponible; }
    public Integer getPtoReorden() { return ptoReorden; }
    public void setPtoReorden(Integer ptoReorden) { this.ptoReorden = ptoReorden; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
