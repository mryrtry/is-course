package org.is.iscourse.model.dto;

import java.time.LocalDateTime;

public class AdmissionDto {
    private Long userId;
    private Long resourceId;
    private String level;
    private Long grantedBy;
    private LocalDateTime expiresAt;
    private String status;
    private String notes;
    
    // Конструкторы
    public AdmissionDto() {}
    
    public AdmissionDto(Long userId, Long resourceId, String level) {
        this.userId = userId;
        this.resourceId = resourceId;
        this.level = level;
        this.status = "active";
    }
    
    // Геттеры и сеттеры
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public Long getResourceId() { return resourceId; }
    public void setResourceId(Long resourceId) { this.resourceId = resourceId; }
    
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
    
    public Long getGrantedBy() { return grantedBy; }
    public void setGrantedBy(Long grantedBy) { this.grantedBy = grantedBy; }
    
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}