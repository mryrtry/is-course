package org.is.iscourse.model.dto;

import java.math.BigDecimal;

public class ResourceDto {
    private String name;
    private String type;
    private String serialNumber;
    private String model;
    private String technicalSpecs;
    private String status;
    private BigDecimal usageCostPerHour;

    public ResourceDto() {}
    
    public ResourceDto(String name, String type) {
        this.name = name;
        this.type = type;
        this.status = "operational";
        this.usageCostPerHour = BigDecimal.ZERO;
    }

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
}