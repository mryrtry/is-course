package org.is.iscourse.repository;

import java.util.List;
import java.util.Optional;

import org.is.iscourse.model.entity.InductionAttempt;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class InductionAttemptRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public InductionAttempt save(InductionAttempt attempt) {
        if (attempt.getAttemptId() == null) {
            entityManager.persist(attempt);
            return attempt;
        } else {
            return entityManager.merge(attempt);
        }
    }
    
    public List<InductionAttempt> findByUserId(Long userId) {
        String jpql = "SELECT ia FROM InductionAttempt ia WHERE ia.user.userId = :userId ORDER BY ia.createdAt DESC";
        return entityManager.createQuery(jpql, InductionAttempt.class)
                .setParameter("userId", userId)
                .getResultList();
    }
    
    public List<InductionAttempt> findByInductionId(Long inductionId) {
        String jpql = "SELECT ia FROM InductionAttempt ia WHERE ia.induction.inductionId = :inductionId ORDER BY ia.createdAt DESC";
        return entityManager.createQuery(jpql, InductionAttempt.class)
                .setParameter("inductionId", inductionId)
                .getResultList();
    }
    
    public Optional<InductionAttempt> findById(Long attemptId) {
        InductionAttempt attempt = entityManager.find(InductionAttempt.class, attemptId);
        return Optional.ofNullable(attempt);
    }
    
    public List<InductionAttempt> findByStatus(String status) {
        String jpql = "SELECT ia FROM InductionAttempt ia WHERE ia.status = :status ORDER BY ia.createdAt DESC";
        return entityManager.createQuery(jpql, InductionAttempt.class)
                .setParameter("status", status)
                .getResultList();
    }
}