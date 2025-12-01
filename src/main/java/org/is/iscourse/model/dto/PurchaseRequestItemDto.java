package org.is.iscourse.model.dto;

import java.math.BigDecimal;

public class PurchaseRequestItemDto {
    private Long materialId;
    private BigDecimal requestedQty;
    private BigDecimal price;

    public PurchaseRequestItemDto() {}
    
    public PurchaseRequestItemDto(Long materialId, BigDecimal requestedQty, BigDecimal price) {
        this.materialId = materialId;
        this.requestedQty = requestedQty;
        this.price = price;
    }
    
    public Long getMaterialId() { return materialId; }
    public void setMaterialId(Long materialId) { this.materialId = materialId; }
    
    public BigDecimal getRequestedQty() { return requestedQty; }
    public void setRequestedQty(BigDecimal requestedQty) { this.requestedQty = requestedQty; }
    
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}