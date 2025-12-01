package org.is.iscourse.model.dto;

import java.math.BigDecimal;

public class InductionDto {
    private String title;
    private String description;
    private String version;
    private String theoryContentLink;
    private Long createdBy;
    private BigDecimal durationHours;

    public InductionDto() {}
    
    public InductionDto(String title, String description, Long createdBy) {
        this.title = title;
        this.description = description;
        this.createdBy = createdBy;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }
    
    public String getTheoryContentLink() { return theoryContentLink; }
    public void setTheoryContentLink(String theoryContentLink) { this.theoryContentLink = theoryContentLink; }
    
    public Long getCreatedBy() { return createdBy; }
    public void setCreatedBy(Long createdBy) { this.createdBy = createdBy; }
    
    public BigDecimal getDurationHours() { return durationHours; }
    public void setDurationHours(BigDecimal durationHours) { this.durationHours = durationHours; }
}
