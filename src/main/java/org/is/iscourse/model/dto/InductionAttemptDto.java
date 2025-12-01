package org.is.iscourse.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class InductionAttemptDto {
    private Long userId;
    private Long inductionId;
    private String status;
    private BigDecimal score;
    private Long practicalSignedBy;
    private LocalDateTime expiresAt;

    public InductionAttemptDto() {}
    
    public InductionAttemptDto(Long userId, Long inductionId) {
        this.userId = userId;
        this.inductionId = inductionId;
        this.status = "in_progress";
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public Long getInductionId() { return inductionId; }
    public void setInductionId(Long inductionId) { this.inductionId = inductionId; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public BigDecimal getScore() { return score; }
    public void setScore(BigDecimal score) { this.score = score; }
    
    public Long getPracticalSignedBy() { return practicalSignedBy; }
    public void setPracticalSignedBy(Long practicalSignedBy) { this.practicalSignedBy = practicalSignedBy; }
    
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }
}
