package org.is.iscourse.model.dto;

import java.math.BigDecimal;

public class MaintenanceMaterialDto {
    private Long materialId;
    private BigDecimal qty;
    private BigDecimal unitCost;
    
    public MaintenanceMaterialDto() {}
    
    public MaintenanceMaterialDto(Long materialId, BigDecimal qty, BigDecimal unitCost) {
        this.materialId = materialId;
        this.qty = qty;
        this.unitCost = unitCost;
    }
    
    public Long getMaterialId() { return materialId; }
    public void setMaterialId(Long materialId) { this.materialId = materialId; }
    
    public BigDecimal getQty() { return qty; }
    public void setQty(BigDecimal qty) { this.qty = qty; }
    
    public BigDecimal getUnitCost() { return unitCost; }
    public void setUnitCost(BigDecimal unitCost) { this.unitCost = unitCost; }
}