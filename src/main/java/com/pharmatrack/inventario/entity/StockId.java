/*
  Clave compuesta para ProductoStock (idSucursal + idProducto).
*/
package com.pharmatrack.inventario.entity;

import java.io.Serializable;
import java.util.Objects;

public class StockId implements Serializable {
    private Integer idSucursal;
    private String idProducto;

    public StockId() {}
    public StockId(Integer idSucursal, String idProducto) {
        this.idSucursal = idSucursal;
        this.idProducto = idProducto;
    }

    public Integer getIdSucursal() { return idSucursal; }
    public void setIdSucursal(Integer idSucursal) { this.idSucursal = idSucursal; }
    public String getIdProducto() { return idProducto; }
    public void setIdProducto(String idProducto) { this.idProducto = idProducto; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StockId)) return false;
        StockId other = (StockId) o;
        return Objects.equals(idSucursal, other.idSucursal) &&
               Objects.equals(idProducto, other.idProducto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSucursal, idProducto);
    }
}
