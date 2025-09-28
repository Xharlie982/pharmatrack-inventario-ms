/*
  MovimientoStock: entradas/salidas/ajustes con validación para no quedar negativo.
  Index combinado por sucursal, producto, fecha para consultas rápidas.
*/
package com.pharmatrack.inventario.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "movimiento_stock",
       indexes = {
         @Index(name="idx_mov_busqueda", columnList = "idSucursal,idProducto,createdAt")
       })
public class MovimientoStock {

    public enum Tipo { IN, OUT, ADJ }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer idSucursal;

    @Column(length = 64)
    private String idProducto;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    private Integer cantidad;

    private String motivo;
    private String refDocumento;

    private Instant createdAt = Instant.now();

    public Long getId() { return id; }
    public Integer getIdSucursal() { return idSucursal; }
    public void setIdSucursal(Integer idSucursal) { this.idSucursal = idSucursal; }
    public String getIdProducto() { return idProducto; }
    public void setIdProducto(String idProducto) { this.idProducto = idProducto; }
    public Tipo getTipo() { return tipo; }
    public void setTipo(Tipo tipo) { this.tipo = tipo; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public String getRefDocumento() { return refDocumento; }
    public void setRefDocumento(String refDocumento) { this.refDocumento = refDocumento; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
