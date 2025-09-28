/*
  DTO para ítems de upsert masivo de stock.
*/
package com.pharmatrack.inventario.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BulkStockItem {
    @NotNull
    private Integer idSucursal;
    @NotBlank
    private String idProducto;
    @NotNull @Min(0)
    private Integer stockDisponible;
    @NotNull @Min(0)
    private Integer ptoReorden;

    public Integer getIdSucursal() { return idSucursal; }
    public void setIdSucursal(Integer idSucursal) { this.idSucursal = idSucursal; }
    public String getIdProducto() { return idProducto; }
    public void setIdProducto(String idProducto) { this.idProducto = idProducto; }
    public Integer getStockDisponible() { return stockDisponible; }
    public void setStockDisponible(Integer stockDisponible) { this.stockDisponible = stockDisponible; }
    public Integer getPtoReorden() { return ptoReorden; }
    public void setPtoReorden(Integer ptoReorden) { this.ptoReorden = ptoReorden; }
}
