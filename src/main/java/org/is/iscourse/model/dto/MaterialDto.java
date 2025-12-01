package org.is.iscourse.model.dto;

import java.math.BigDecimal;

public class MaterialDto {
    private String name;
    private String category;
    private String specifications;
    private String unit;
    private BigDecimal unitCost;
    private BigDecimal stockQty;
    private BigDecimal minStockThreshold;

    public MaterialDto() {}
    
    public MaterialDto(String name, String category) {
        this.name = name;
        this.category = category;
        this.unit = "pcs";
        this.unitCost = BigDecimal.ZERO;
        this.stockQty = BigDecimal.ZERO;
    }

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
}