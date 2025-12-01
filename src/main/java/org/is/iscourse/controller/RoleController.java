package org.is.iscourse.controller;

import java.util.List;

import org.is.iscourse.model.dto.AssignRoleDto;
import org.is.iscourse.model.entity.Role;
import org.is.iscourse.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    
    @Autowired
    private RoleService roleService;
    
    @PostMapping("/assign")
    public ResponseEntity<String> assignRole(@RequestBody AssignRoleDto assignRoleDto) {
        if (assignRoleDto.getUserId() > 0 & assignRoleDto.getRoleId() > 0){    
            roleService.assignRoleToUser(assignRoleDto.getUserId(), assignRoleDto.getRoleId(), assignRoleDto.getAssignedBy());
            return ResponseEntity.ok("Role assigned successfully");    
        }
        else{
            return ResponseEntity.badRequest().body("Incorrect id");
        }
    }
    @DeleteMapping("/remove")
    public ResponseEntity<String> removeRole(@RequestBody AssignRoleDto assignRoleDto) {
        roleService.removeRoleFromUser(assignRoleDto.getUserId(), assignRoleDto.getRoleId());
        return ResponseEntity.ok("Role removed successfully");
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Role>> getUserRoles(@PathVariable Long userId) {
        List<Role> roles = roleService.getUserRoles(userId);
        return ResponseEntity.ok(roles);
    }
    
    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }
}