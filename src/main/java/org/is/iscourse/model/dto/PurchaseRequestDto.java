package org.is.iscourse.model.dto;

import java.time.LocalDateTime;
import java.util.List;

public class PurchaseRequestDto {
    private Long createdBy;
    private Long supplierId;
    private LocalDateTime expectedDelivery;
    private String notes;
    private List<PurchaseRequestItemDto> items;

    public PurchaseRequestDto() {}
    
    public PurchaseRequestDto(Long createdBy, Long supplierId) {
        this.createdBy = createdBy;
        this.supplierId = supplierId;
    }
    
    public Long getCreatedBy() { return createdBy; }
    public void setCreatedBy(Long createdBy) { this.createdBy = createdBy; }
    
    public Long getSupplierId() { return supplierId; }
    public void setSupplierId(Long supplierId) { this.supplierId = supplierId; }
    
    public LocalDateTime getExpectedDelivery() { return expectedDelivery; }
    public void setExpectedDelivery(LocalDateTime expectedDelivery) { this.expectedDelivery = expectedDelivery; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    
    public List<PurchaseRequestItemDto> getItems() { return items; }
    public void setItems(List<PurchaseRequestItemDto> items) { this.items = items; }
}