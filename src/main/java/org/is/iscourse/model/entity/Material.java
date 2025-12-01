package org.is.iscourse.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.Type;

import com.vladmihalcea.hibernate.type.json.JsonType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "materials", schema = "fab")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "material_id")
    private Long materialId;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "category")
    private String category;
    
    @Type(JsonType.class)
    @Column(name = "specifications", columnDefinition = "jsonb")
    private String specifications = "{}";
    
    @Column(name = "unit")
    private String unit = "pcs";
    
    @Column(name = "unit_cost", precision = 12, scale = 2)
    private BigDecimal unitCost = BigDecimal.ZERO;
    
    @Column(name = "stock_qty", precision = 12, scale = 3)
    private BigDecimal stockQty = BigDecimal.ZERO;
    
    @Column(name = "min_stock_threshold", precision = 12, scale = 3)
    private BigDecimal minStockThreshold = BigDecimal.ZERO;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    public Material() {}
    
    public Material(String name, String category) {
        this.name = name;
        this.category = category;
    }
    
    public Long getMaterialId() { return materialId; }
    public void setMaterialId(Long materialId) { this.materialId = materialId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public String getSpecifications() { return specifications; }
    public void setSpecifications(String specifications) { this.specifications = specifications; }
    
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    
    public BigDecimal getUnitCost() { return unitCost; }
    public void setUnitCost(BigDecimal unitCost) { this.unitCost = unitCost; }
    
    public BigDecimal getStockQty() { return stockQty; }
    public void setStockQty(BigDecimal stockQty) { this.stockQty = stockQty; }
    
    public BigDecimal getMinStockThreshold() { return minStockThreshold; }
    public void setMinStockThreshold(BigDecimal minStockThreshold) { this.minStockThreshold = minStockThreshold; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
