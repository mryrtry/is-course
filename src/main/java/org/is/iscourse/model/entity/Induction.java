package org.is.iscourse.model.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "inductions", schema = "fab")
public class Induction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "induction_id")
    private Long inductionId;
    
    @Column(name = "title", nullable = false)
    private String title;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "version")
    private String version;
    
    @Column(name = "theory_content_link")
    private String theoryContentLink;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "duration_hours", precision = 5, scale = 2)
    private BigDecimal durationHours;
    
    public Induction() {}
    
    public Induction(String title, String description, User createdBy) {
        this.title = title;
        this.description = description;
        this.createdBy = createdBy;
    }
    
    public Long getInductionId() { return inductionId; }
    public void setInductionId(Long inductionId) { this.inductionId = inductionId; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }
    
    public String getTheoryContentLink() { return theoryContentLink; }
    public void setTheoryContentLink(String theoryContentLink) { this.theoryContentLink = theoryContentLink; }
    
    public User getCreatedBy() { return createdBy; }
    public void setCreatedBy(User createdBy) { this.createdBy = createdBy; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public BigDecimal getDurationHours() { return durationHours; }
    public void setDurationHours(BigDecimal durationHours) { this.durationHours = durationHours; }
}
