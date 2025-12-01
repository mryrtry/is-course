package org.is.iscourse.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.Type;

import com.vladmihalcea.hibernate.type.json.JsonType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "resources", schema = "fab")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resource_id")
    private Long resourceId;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "type", nullable = false)
    private String type;
    
    @Column(name = "serial_number")
    private String serialNumber;
    
    @Column(name = "model")
    private String model;
    
    @Type(JsonType.class)
    @Column(name = "technical_specs", columnDefinition = "jsonb")
    private String technicalSpecs = "{}";
    
    @Column(name = "status", nullable = false)
    private String status = "operational";
    
    @Column(name = "usage_cost_per_hour", precision = 10, scale = 2)
    private BigDecimal usageCostPerHour = BigDecimal.ZERO;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    public Resource() {}
    
    public Resource(String name, String type) {
        this.name = name;
        this.type = type;
    }
    
    public Long getResourceId() { return resourceId; }
    public void setResourceId(Long resourceId) { this.resourceId = resourceId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }
    
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    
    public String getTechnicalSpecs() { return technicalSpecs; }
    public void setTechnicalSpecs(String technicalSpecs) { this.technicalSpecs = technicalSpecs; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public BigDecimal getUsageCostPerHour() { return usageCostPerHour; }
    public void setUsageCostPerHour(BigDecimal usageCostPerHour) { this.usageCostPerHour = usageCostPerHour; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
