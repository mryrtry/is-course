package org.is.iscourse.model.dto;

public class AssignRoleDto {
    private Long userId;
    private Long roleId;
    private Long assignedBy;
    
    public AssignRoleDto() {}
    
    public AssignRoleDto(Long userId, Long roleId, Long assignedBy) {
        this.userId = userId;
        this.roleId = roleId;
        this.assignedBy = assignedBy;
    }
    
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public Long getRoleId() { return roleId; }
    public void setRoleId(Long roleId) { this.roleId = roleId; }

    public Long getAssignedBy() { return assignedBy; }
    public void setAssignedBy(Long assignedBy) { this.assignedBy = assignedBy; }
}