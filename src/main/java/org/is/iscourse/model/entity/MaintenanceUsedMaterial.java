package org.is.iscourse.model.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.Table;

@Entity
@Table(name = "maintenance_used_materials", schema = "fab")
public class MaintenanceUsedMaterial {
    @Id
    @ManyToOne
    @JoinColumn(name = "maintenance_id")
    @JsonIgnore
    private MaintenanceRecord maintenanceRecord;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;
    
    @Column(name = "qty", precision = 12, scale = 3)
    private BigDecimal qty = BigDecimal.ZERO;
    
    @Column(name = "unit_cost", precision = 12, scale = 2)
    private BigDecimal unitCost;
    
    public MaintenanceUsedMaterial() {}
    
    public MaintenanceUsedMaterial(MaintenanceRecord maintenanceRecord, Material material, BigDecimal qty, BigDecimal unitCost) {
        this.maintenanceRecord = maintenanceRecord;
        this.material = material;
        this.qty = qty;
        this.unitCost = unitCost;
    }
    
    public MaintenanceRecord getMaintenanceRecord() { return maintenanceRecord; }
    public void setMaintenanceRecord(MaintenanceRecord maintenanceRecord) { this.maintenanceRecord = maintenanceRecord; }
    
    public Material getMaterial() { return material; }
    public void setMaterial(Material material) { this.material = material; }
    
    public BigDecimal getQty() { return qty; }
    public void setQty(BigDecimal qty) { this.qty = qty; }
    
    public BigDecimal getUnitCost() { return unitCost; }
    public void setUnitCost(BigDecimal unitCost) { this.unitCost = unitCost; }
}