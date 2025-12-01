package org.is.iscourse.service;

import java.util.List;
import java.util.stream.Collectors;

import org.is.iscourse.model.entity.Role;
import org.is.iscourse.model.entity.User;
import org.is.iscourse.model.entity.UserRole;
import org.is.iscourse.repository.RoleRepository;
import org.is.iscourse.repository.UserRepository;
import org.is.iscourse.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RoleService {
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private UserRoleRepository userRoleRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public void assignRoleToUser(Long userId, Long roleId, Long assignerId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        User assigner = userRepository.findById(assignerId)
            .orElseThrow(() -> new RuntimeException("Assigner not found"));
        
        Role role = roleRepository.findById(roleId)
            .orElseThrow(() -> new RuntimeException("Role not found"));
        
        if (userRoleRepository.existsByUserAndRole(userId, roleId)) {
            throw new RuntimeException("Role already assigned to user");
        }
        
        UserRole userRole = new UserRole(user, role, assigner);
        userRoleRepository.save(userRole);
    }
    
    public void removeRoleFromUser(Long userId, Long roleId) {
        userRoleRepository.deleteByUserAndRole(userId, roleId);
    }
    
    public List<Role> getUserRoles(Long userId) {
        List<UserRole> userRoles = userRoleRepository.findByUserId(userId);
        return userRoles.stream()
                .map(UserRole::getRole)
                .collect(Collectors.toList());
    }
    
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}