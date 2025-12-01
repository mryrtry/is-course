package org.is.iscourse.model.dto;

import java.time.LocalDateTime;
import java.util.List;

public class BookingDto {
    private Long userId;
    private Long resourceId;
    private String purpose;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<BookingMaterialDto> materials;

    public BookingDto() {}
    
    public BookingDto(Long userId, Long resourceId, String purpose, LocalDateTime startTime, LocalDateTime endTime) {
        this.userId = userId;
        this.resourceId = resourceId;
        this.purpose = purpose;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public Long getResourceId() { return resourceId; }
    public void setResourceId(Long resourceId) { this.resourceId = resourceId; }
    
    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }
    
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    
    public List<BookingMaterialDto> getMaterials() { return materials; }
    public void setMaterials(List<BookingMaterialDto> materials) { this.materials = materials; }
}