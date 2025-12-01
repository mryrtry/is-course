package org.is.iscourse.model.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Table(name = "purchase_request_items", schema = "fab")
public class PurchaseRequestItem {
    @Id
    @ManyToOne
    @JoinColumn(name = "pr_id")
    @JsonIgnore
    private PurchaseRequest purchaseRequest;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;
    
    @Column(name = "requested_qty", precision = 12, scale = 3)
    private BigDecimal requestedQty = BigDecimal.ZERO;
    
    @Column(name = "approved_qty", precision = 12, scale = 3)
    private BigDecimal approvedQty;
    
    @Column(name = "price", precision = 12, scale = 2)
    private BigDecimal price;
    
    public PurchaseRequestItem() {}
    
    public PurchaseRequestItem(PurchaseRequest purchaseRequest, Material material, BigDecimal requestedQty, BigDecimal price) {
        this.purchaseRequest = purchaseRequest;
        this.material = material;
        this.requestedQty = requestedQty;
        this.price = price;
    }
    
    public PurchaseRequest getPurchaseRequest() { return purchaseRequest; }
    public void setPurchaseRequest(PurchaseRequest purchaseRequest) { this.purchaseRequest = purchaseRequest; }
    
    public Material getMaterial() { return material; }
    public void setMaterial(Material material) { this.material = material; }
    
    public BigDecimal getRequestedQty() { return requestedQty; }
    public void setRequestedQty(BigDecimal requestedQty) { this.requestedQty = requestedQty; }
    
    public BigDecimal getApprovedQty() { return approvedQty; }
    public void setApprovedQty(BigDecimal approvedQty) { this.approvedQty = approvedQty; }
    
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}
