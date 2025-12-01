package org.is.iscourse.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_roles", schema = "fab")
public class UserRole {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    
    @ManyToOne
    @JoinColumn(name = "assigned_by")
    private User assignedBy;  
    
    @Column(name = "assigned_at")
    private LocalDateTime assignedAt = LocalDateTime.now();
    
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
    
    public UserRole() {}
    
    public UserRole(User user, Role role, User assignedBy) {
        this.user = user;
        this.role = role;
        this.assignedBy = assignedBy;
    }
    
    public User getUser() { return user; }
    public Role getRole() { return role; }
    public User getAssignedBy() { return assignedBy; }
    public LocalDateTime getAssignedAt() { return assignedAt; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
}
