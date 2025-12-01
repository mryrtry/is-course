package org.is.iscourse.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "maintenance_records", schema = "fab")
public class MaintenanceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maintenance_id")
    private Long maintenanceId;
    
    @Column(name = "resource_id", nullable = false)
    private Long resourceId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performed_by")
    private User performedBy;
    
    @Column(name = "performed_at")
    private LocalDateTime performedAt = LocalDateTime.now();
    
    @Column(name = "work_description")
    private String workDescription;
    
    @Column(name = "hours_spent", precision = 6, scale = 2)
    private BigDecimal hoursSpent;
    
    @Column(name = "cost", precision = 12, scale = 2)
    private BigDecimal cost = BigDecimal.ZERO;
    
    @OneToMany(mappedBy = "maintenanceRecord", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<MaintenanceUsedMaterial> usedMaterials = new HashSet<>();
    
    public MaintenanceRecord() {}
    
    public MaintenanceRecord(Long resourceId, String workDescription) {
        this.resourceId = resourceId;
        this.workDescription = workDescription;
    }
    
    public Long getMaintenanceId() { return maintenanceId; }
    public void setMaintenanceId(Long maintenanceId) { this.maintenanceId = maintenanceId; }
    
    public Long getResourceId() { return resourceId; }
    public void setResourceId(Long resourceId) { this.resourceId = resourceId; }
    
    public User getPerformedBy() { return performedBy; }
    public void setPerformedBy(User performedBy) { this.performedBy = performedBy; }
    
    public LocalDateTime getPerformedAt() { return performedAt; }
    public void setPerformedAt(LocalDateTime performedAt) { this.performedAt = performedAt; }
    
    public String getWorkDescription() { return workDescription; }
    public void setWorkDescription(String workDescription) { this.workDescription = workDescription; }
    
    public BigDecimal getHoursSpent() { return hoursSpent; }
    public void setHoursSpent(BigDecimal hoursSpent) { this.hoursSpent = hoursSpent; }
    
    public BigDecimal getCost() { return cost; }
    public void setCost(BigDecimal cost) { this.cost = cost; }
    
    public Set<MaintenanceUsedMaterial> getUsedMaterials() { return usedMaterials; }
    public void setUsedMaterials(Set<MaintenanceUsedMaterial> usedMaterials) { this.usedMaterials = usedMaterials; }
    
    public void addUsedMaterial(Material material, BigDecimal qty, BigDecimal unitCost) {
        MaintenanceUsedMaterial usedMaterial = new MaintenanceUsedMaterial(this, material, qty, unitCost);
        this.usedMaterials.add(usedMaterial);
    }
}