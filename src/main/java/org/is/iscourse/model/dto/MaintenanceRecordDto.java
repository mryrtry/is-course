package org.is.iscourse.model.dto;

import java.math.BigDecimal;
import java.util.List;

public class MaintenanceRecordDto {
    private Long resourceId;
    private Long performedBy;
    private String workDescription;
    private BigDecimal hoursSpent;
    private List<MaintenanceMaterialDto> usedMaterials;
    
    public MaintenanceRecordDto() {}
    
    public MaintenanceRecordDto(Long resourceId, String workDescription) {
        this.resourceId = resourceId;
        this.workDescription = workDescription;
    }
    
    public Long getResourceId() { return resourceId; }
    public void setResourceId(Long resourceId) { this.resourceId = resourceId; }
    
    public Long getPerformedBy() { return performedBy; }
    public void setPerformedBy(Long performedBy) { this.performedBy = performedBy; }
    
    public String getWorkDescription() { return workDescription; }
    public void setWorkDescription(String workDescription) { this.workDescription = workDescription; }
    
    public BigDecimal getHoursSpent() { return hoursSpent; }
    public void setHoursSpent(BigDecimal hoursSpent) { this.hoursSpent = hoursSpent; }
    
    public List<MaintenanceMaterialDto> getUsedMaterials() { return usedMaterials; }
    public void setUsedMaterials(List<MaintenanceMaterialDto> usedMaterials) { this.usedMaterials = usedMaterials; }
}