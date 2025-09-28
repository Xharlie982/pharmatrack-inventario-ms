/*
  DTO para crear movimientos: request body limpio en ISO-8601.
*/
package com.pharmatrack.inventario.dto;

import com.pharmatrack.inventario.entity.MovimientoStock;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MovimientoRequest {
    @NotNull
    private Integer idSucursal;

    @NotBlank
    private String idProducto;

    @NotNull
    private MovimientoStock.Tipo tipo;

    @NotNull @Min(1)
    private Integer cantidad;

    private String motivo;
    private String refDocumento;

    public Integer getIdSucursal() { return idSucursal; }
    public void setIdSucursal(Integer idSucursal) { this.idSucursal = idSucursal; }
    public String getIdProducto() { return idProducto; }
    public void setIdProducto(String idProducto) { this.idProducto = idProducto; }
    public MovimientoStock.Tipo getTipo() { return tipo; }
    public void setTipo(MovimientoStock.Tipo tipo) { this.tipo = tipo; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public String getRefDocumento() { return refDocumento; }
    public void setRefDocumento(String refDocumento) { this.refDocumento = refDocumento; }
}
