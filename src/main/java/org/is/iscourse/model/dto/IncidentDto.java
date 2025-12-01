package org.is.iscourse.model.dto;

import java.time.LocalDateTime;

public class IncidentDto {
    private Long incidentId;
    private Long reportedBy;
    private Long resourceId;
    private Long assignedTo;
    private String type;
    private String description;
    private String status;
    private String severity;
    private LocalDateTime createdAt;
    private String resolutionNotes;

    public IncidentDto() {}
    
    public IncidentDto(Long reportedBy, Long resourceId, String type, String description) {
        this.reportedBy = reportedBy;
        this.resourceId = resourceId;
        this.type = type;
        this.description = description;
    }
   
    public Long getIncidentId() { return incidentId; }
    public void setIncidentId(Long incidentId) { this.incidentId = incidentId; }
    
    public Long getReportedBy() { return reportedBy; }
    public void setReportedBy(Long reportedBy) { this.reportedBy = reportedBy; }
    
    public Long getResourceId() { return resourceId; }
    public void setResourceId(Long resourceId) { this.resourceId = resourceId; }
    
    public Long getAssignedTo() { return assignedTo; }
    public void setAssignedTo(Long assignedTo) { this.assignedTo = assignedTo; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public String getResolutionNotes() { return resolutionNotes; }
    public void setResolutionNotes(String resolutionNotes) { this.resolutionNotes = resolutionNotes; }
}