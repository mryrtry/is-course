package org.is.iscourse.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.is.iscourse.model.entity.Booking;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class BookingRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public Booking save(Booking booking) {
        if (booking.getBookingId() == null) {
            entityManager.persist(booking);
            return booking;
        } else {
            return entityManager.merge(booking);
        }
    }
    
    public Optional<Booking> findById(Long bookingId) {
        Booking booking = entityManager.find(Booking.class, bookingId);
        return Optional.ofNullable(booking);
    }
    
    public List<Booking> findByUserId(Long userId) {
        String jpql = "SELECT b FROM Booking b WHERE b.user.userId = :userId ORDER BY b.startTime DESC";
        return entityManager.createQuery(jpql, Booking.class)
                .setParameter("userId", userId)
                .getResultList();
    }
    
    public List<Booking> findByResourceId(Long resourceId) {
        String jpql = "SELECT b FROM Booking b WHERE b.resourceId = :resourceId ORDER BY b.startTime";
        return entityManager.createQuery(jpql, Booking.class)
                .setParameter("resourceId", resourceId)
                .getResultList();
    }
    
    public List<Booking> findConflictingBookings(Long resourceId, LocalDateTime startTime, LocalDateTime endTime) {
        String jpql = """
            SELECT b FROM Booking b 
            WHERE b.resourceId = :resourceId 
            AND b.status IN ('pending', 'confirmed')
            AND (b.startTime < :endTime AND b.endTime > :startTime)
            """;
        return entityManager.createQuery(jpql, Booking.class)
                .setParameter("resourceId", resourceId)
                .setParameter("startTime", startTime)
                .setParameter("endTime", endTime)
                .getResultList();
    }
    
    public List<Booking> findByStatus(String status) {
        String jpql = "SELECT b FROM Booking b WHERE b.status = :status ORDER BY b.startTime";
        return entityManager.createQuery(jpql, Booking.class)
                .setParameter("status", status)
                .getResultList();
    }
    
    public void delete(Booking booking) {
        entityManager.remove(entityManager.contains(booking) ? booking : entityManager.merge(booking));
    }
    
    public List<Booking> findAll() {
        String jpql = "SELECT b FROM Booking b ORDER BY b.createdAt DESC";
        return entityManager.createQuery(jpql, Booking.class).getResultList();
    }
}