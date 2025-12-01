package org.is.iscourse.repository;

import java.math.BigDecimal;
import java.util.List;

import org.is.iscourse.model.entity.PurchaseRequestItem;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class PurchaseRequestItemRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public PurchaseRequestItem save(PurchaseRequestItem item) {
        entityManager.persist(item);
        return item;
    }
    
    public List<PurchaseRequestItem> findByPrId(Long prId) {
        String jpql = "SELECT pri FROM PurchaseRequestItem pri WHERE pri.purchaseRequest.prId = :prId";
        return entityManager.createQuery(jpql, PurchaseRequestItem.class)
                .setParameter("prId", prId)
                .getResultList();
    }
    
    public void updateApprovedQty(Long prId, Long materialId, BigDecimal approvedQty) {
        String jpql = "UPDATE PurchaseRequestItem pri SET pri.approvedQty = :approvedQty WHERE pri.purchaseRequest.prId = :prId AND pri.material.materialId = :materialId";
        entityManager.createQuery(jpql)
                .setParameter("approvedQty", approvedQty)
                .setParameter("prId", prId)
                .setParameter("materialId", materialId)
                .executeUpdate();
    }
}