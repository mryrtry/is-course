package org.is.iscourse.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;


@Entity
@Table(name = "booking_materials", schema = "fab")
public class BookingMaterial {
    @Id
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "booking_id")
    private Booking booking;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;
    
    @Column(name = "planned_qty", precision = 12, scale = 3)
    private BigDecimal plannedQty = BigDecimal.ZERO;
    
    @Column(name = "actual_qty", precision = 12, scale = 3)
    private BigDecimal actualQty;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issued_by")
    private User issuedBy;
    
    @Column(name = "issued_at")
    private LocalDateTime issuedAt;
    
    @Column(name = "cost", precision = 12, scale = 2)
    private BigDecimal cost;

    public BookingMaterial() {}
    
    public BookingMaterial(Booking booking, Material material, BigDecimal plannedQty) {
        this.booking = booking;
        this.material = material;
        this.plannedQty = plannedQty;
    }

    public Booking getBooking() { return booking; }
    public void setBooking(Booking booking) { this.booking = booking; }
    
    public Material getMaterial() { return material; }
    public void setMaterial(Material material) { this.material = material; }
    
    public BigDecimal getPlannedQty() { return plannedQty; }
    public void setPlannedQty(BigDecimal plannedQty) { this.plannedQty = plannedQty; }
    
    public BigDecimal getActualQty() { return actualQty; }
    public void setActualQty(BigDecimal actualQty) { this.actualQty = actualQty; }
    
    public User getIssuedBy() { return issuedBy; }
    public void setIssuedBy(User issuedBy) { this.issuedBy = issuedBy; }
    
    public LocalDateTime getIssuedAt() { return issuedAt; }
    public void setIssuedAt(LocalDateTime issuedAt) { this.issuedAt = issuedAt; }
    
    public BigDecimal getCost() { return cost; }
    public void setCost(BigDecimal cost) { this.cost = cost; }
}