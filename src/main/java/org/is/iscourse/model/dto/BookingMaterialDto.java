package org.is.iscourse.model.dto;

import java.math.BigDecimal;

public class BookingMaterialDto {
    private Long materialId;
    private BigDecimal plannedQty;

    public BookingMaterialDto() {}
    
    public BookingMaterialDto(Long materialId, BigDecimal plannedQty) {
        this.materialId = materialId;
        this.plannedQty = plannedQty;
    }
    
    public Long getMaterialId() { return materialId; }
    public void setMaterialId(Long materialId) { this.materialId = materialId; }
    
    public BigDecimal getPlannedQty() { return plannedQty; }
    public void setPlannedQty(BigDecimal plannedQty) { this.plannedQty = plannedQty; }
}