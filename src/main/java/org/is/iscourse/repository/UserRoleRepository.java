package org.is.iscourse.repository;

import java.util.List;

import org.is.iscourse.model.entity.UserRole;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class UserRoleRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public UserRole save(UserRole userRole) {
        entityManager.merge(userRole);
        return userRole;
    }
    
    public List<UserRole> findByUserId(Long userId) {
        String jpql = "SELECT ur FROM UserRole ur WHERE ur.user.userId = :userId";
        return entityManager.createQuery(jpql, UserRole.class)
                .setParameter("userId", userId)
                .getResultList();
    }
    
    public List<UserRole> findByRoleId(Long roleId) {
        String jpql = "SELECT ur FROM UserRole ur WHERE ur.role.roleId = :roleId";
        return entityManager.createQuery(jpql, UserRole.class)
                .setParameter("roleId", roleId)
                .getResultList();
    }
    
    public void deleteByUserAndRole(Long userId, Long roleId) {
        String jpql = "DELETE FROM UserRole ur WHERE ur.user.userId = :userId AND ur.role.roleId = :roleId";
        entityManager.createQuery(jpql)
                .setParameter("userId", userId)
                .setParameter("roleId", roleId)
                .executeUpdate();
    }
    
    public boolean existsByUserAndRole(Long userId, Long roleId) {
        String jpql = "SELECT COUNT(ur) > 0 FROM UserRole ur WHERE ur.user.userId = :userId AND ur.role.roleId = :roleId";
        return entityManager.createQuery(jpql, Boolean.class)
                .setParameter("userId", userId)
                .setParameter("roleId", roleId)
                .getSingleResult();
    }
}