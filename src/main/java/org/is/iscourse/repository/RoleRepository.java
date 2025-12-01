package org.is.iscourse.repository;

import java.util.List;
import java.util.Optional;

import org.is.iscourse.model.entity.Role;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

@Repository
public class RoleRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public Optional<Role> findById(Long roleId) {
        Role role = entityManager.find(Role.class, roleId);
        return Optional.ofNullable(role);
    }
    
    public Optional<Role> findByName(String name) {
        String jpql = "SELECT r FROM Role r WHERE r.name = :name";
        try {
            Role role = entityManager.createQuery(jpql, Role.class)
                    .setParameter("name", name)
                    .getSingleResult();
            return Optional.of(role);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
    
    public List<Role> findAll() {
        String jpql = "SELECT r FROM Role r";
        return entityManager.createQuery(jpql, Role.class).getResultList();
    }
}