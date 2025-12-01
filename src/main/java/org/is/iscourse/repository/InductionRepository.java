package org.is.iscourse.repository;

import org.is.iscourse.model.entity.Induction;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class InductionRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public Induction save(Induction induction) {
        if (induction.getInductionId() == null) {
            entityManager.persist(induction);
            return induction;
        } else {
            return entityManager.merge(induction);
        }
    }
    
    public List<Induction> findAll() {
        String jpql = "SELECT i FROM Induction i ORDER BY i.createdAt DESC";
        return entityManager.createQuery(jpql, Induction.class).getResultList();
    }
    
    public Optional<Induction> findById(Long id) {
        Induction induction = entityManager.find(Induction.class, id);
        return Optional.ofNullable(induction);
    }
}