package org.is.iscourse.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.is.iscourse.model.entity.BookingMaterial;
import org.is.iscourse.model.entity.User;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

@Repository
public class BookingMaterialRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public BookingMaterial save(BookingMaterial bookingMaterial) {
        entityManager.persist(bookingMaterial);
        return bookingMaterial;
    }
    
    public List<BookingMaterial> findByBookingId(Long bookingId) {
        String jpql = "SELECT bm FROM BookingMaterial bm WHERE bm.booking.bookingId = :bookingId";
        return entityManager.createQuery(jpql, BookingMaterial.class)
                .setParameter("bookingId", bookingId)
                .getResultList();
    }
    
    public Optional<BookingMaterial> findByBookingAndMaterial(Long bookingId, Long materialId) {
        String jpql = "SELECT bm FROM BookingMaterial bm WHERE bm.booking.bookingId = :bookingId AND bm.material.materialId = :materialId";
        try {
            BookingMaterial bookingMaterial = entityManager.createQuery(jpql, BookingMaterial.class)
                    .setParameter("bookingId", bookingId)
                    .setParameter("materialId", materialId)
                    .getSingleResult();
            return Optional.of(bookingMaterial);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
    
    public void issueMaterials(Long bookingId, Long materialId, BigDecimal actualQty, Long issuedBy) {
        String jpql = """
            UPDATE BookingMaterial bm 
            SET bm.actualQty = :actualQty, 
                bm.issuedBy = :issuedBy, 
                bm.issuedAt = :issuedAt,
                bm.cost = bm.material.unitCost * :actualQty
            WHERE bm.booking.bookingId = :bookingId 
            AND bm.material.materialId = :materialId
            """;
        
        entityManager.createQuery(jpql)
            .setParameter("actualQty", actualQty)
            .setParameter("issuedBy", entityManager.find(User.class, issuedBy))
            .setParameter("issuedAt", LocalDateTime.now())
            .setParameter("bookingId", bookingId)
            .setParameter("materialId", materialId)
            .executeUpdate();
    }
}
