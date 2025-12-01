package org.is.iscourse.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles", schema = "fab")
public class Role {
    @Id
    @Column(name = "role_id")
    private Long roleId;
    
    @Column(name = "name")
    private String name;
    
    public Role() {}
    
    public Role(Long roleId, String name) {
        this.roleId = roleId;
        this.name = name;
    }
    
    public Long getRoleId() { return roleId; }
    public String getName() { return name; }
}