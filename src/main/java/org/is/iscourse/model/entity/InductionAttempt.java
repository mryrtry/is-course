package org.is.iscourse.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "induction_attempts", schema = "fab")
public class InductionAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attempt_id")
    private Long attemptId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "induction_id", nullable = false)
    private Induction induction;
    
    @Column(name = "status", nullable = false)
    private String status = "in_progress";
    
    @Column(name = "score", precision = 5, scale = 2)
    private BigDecimal score;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "practical_signed_by")
    private User practicalSignedBy;
    
    @Column(name = "passed_at")
    private LocalDateTime passedAt;
    
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    public InductionAttempt() {}
    
    public InductionAttempt(User user, Induction induction) {
        this.user = user;
        this.induction = induction;
    }
    
    public Long getAttemptId() { return attemptId; }
    public void setAttemptId(Long attemptId) { this.attemptId = attemptId; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public Induction getInduction() { return induction; }
    public void setInduction(Induction induction) { this.induction = induction; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public BigDecimal getScore() { return score; }
    public void setScore(BigDecimal score) { this.score = score; }
    
    public User getPracticalSignedBy() { return practicalSignedBy; }
    public void setPracticalSignedBy(User practicalSignedBy) { this.practicalSignedBy = practicalSignedBy; }
    
    public LocalDateTime getPassedAt() { return passedAt; }
    public void setPassedAt(LocalDateTime passedAt) { this.passedAt = passedAt; }
    
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}