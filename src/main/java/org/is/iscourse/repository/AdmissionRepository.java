package org.is.iscourse.repository;

import java.util.List;
import java.util.Optional;

import org.is.iscourse.model.entity.Admission;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

@Repository
public class AdmissionRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public Admission save(Admission admission) {
        if (admission.getAdmissionId() == null) {
            entityManager.persist(admission);
            return admission;
        } else {
            return entityManager.merge(admission);
        }
    }
    
    public List<Admission> findByUserId(Long userId) {
        String jpql = "SELECT a FROM Admission a WHERE a.user.userId = :userId ORDER BY a.grantedAt DESC";
        return entityManager.createQuery(jpql, Admission.class)
                .setParameter("userId", userId)
                .getResultList();
    }
    
    public List<Admission> findByResourceId(Long resourceId) {
        String jpql = "SELECT a FROM Admission a WHERE a.resourceId = :resourceId ORDER BY a.grantedAt DESC";
        return entityManager.createQuery(jpql, Admission.class)
                .setParameter("resourceId", resourceId)
                .getResultList();
    }
    
    public Optional<Admission> findByUserAndResource(Long userId, Long resourceId) {
        String jpql = "SELECT a FROM Admission a WHERE a.user.userId = :userId AND a.resourceId = :resourceId";
        try {
            Admission admission = entityManager.createQuery(jpql, Admission.class)
                    .setParameter("userId", userId)
                    .setParameter("resourceId", resourceId)
                    .getSingleResult();
            return Optional.of(admission);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<Admission> findById(Long admissionId) {
        Admission admission = entityManager.find(Admission.class, admissionId);
        return Optional.ofNullable(admission);
    }
    
    public List<Admission> findActiveAdmissions() {
        String jpql = "SELECT a FROM Admission a WHERE a.status = 'active' AND (a.expiresAt IS NULL OR a.expiresAt > CURRENT_TIMESTAMP)";
        return entityManager.createQuery(jpql, Admission.class).getResultList();
    }
    
    public boolean hasActiveAdmission(Long userId, Long resourceId) {
        String jpql = """
            SELECT COUNT(a) > 0 FROM Admission a 
            WHERE a.user.userId = :userId 
            AND a.resourceId = :resourceId 
            AND a.status = 'active' 
            AND (a.expiresAt IS NULL OR a.expiresAt > CURRENT_TIMESTAMP)
            """;
        return entityManager.createQuery(jpql, Boolean.class)
                .setParameter("userId", userId)
                .setParameter("resourceId", resourceId)
                .getSingleResult();
    }
}

