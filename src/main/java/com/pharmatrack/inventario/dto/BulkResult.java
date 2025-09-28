/*
  DTO resultado para /stock:bulk con contadores insertados/actualizados.
*/
package com.pharmatrack.inventario.dto;

public class BulkResult {
    private int inserted;
    private int updated;

    public BulkResult() {}
    public BulkResult(int inserted, int updated) { this.inserted = inserted; this.updated = updated; }
    public int getInserted() { return inserted; }
    public void setInserted(int inserted) { this.inserted = inserted; }
    public int getUpdated() { return updated; }
    public void setUpdated(int updated) { this.updated = updated; }
}
