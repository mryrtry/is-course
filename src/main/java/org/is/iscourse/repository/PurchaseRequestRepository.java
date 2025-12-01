package org.is.iscourse.repository;

import java.util.List;
import java.util.Optional;

import org.is.iscourse.model.entity.PurchaseRequest;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class PurchaseRequestRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public PurchaseRequest save(PurchaseRequest purchaseRequest) {
        if (purchaseRequest.getPrId() == null) {
            entityManager.persist(purchaseRequest);
            return purchaseRequest;
        } else {
            return entityManager.merge(purchaseRequest);
        }
    }
    
    public List<PurchaseRequest> findAll() {
        String jpql = "SELECT pr FROM PurchaseRequest pr ORDER BY pr.createdAt DESC";
        return entityManager.createQuery(jpql, PurchaseRequest.class).getResultList();
    }
    
    public List<PurchaseRequest> findByStatus(String status) {
        String jpql = "SELECT pr FROM PurchaseRequest pr WHERE pr.status = :status ORDER BY pr.createdAt DESC";
        return entityManager.createQuery(jpql, PurchaseRequest.class)
                .setParameter("status", status)
                .getResultList();
    }
    
    public List<PurchaseRequest> findByCreatedBy(Long userId) {
        String jpql = "SELECT pr FROM PurchaseRequest pr WHERE pr.createdBy.userId = :userId ORDER BY pr.createdAt DESC";
        return entityManager.createQuery(jpql, PurchaseRequest.class)
                .setParameter("userId", userId)
                .getResultList();
    }
    
    public Optional<PurchaseRequest> findById(Long prId) {
        PurchaseRequest pr = entityManager.find(PurchaseRequest.class, prId);
        return Optional.ofNullable(pr);
    }
}