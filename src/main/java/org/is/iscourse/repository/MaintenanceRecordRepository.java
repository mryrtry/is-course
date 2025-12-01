package org.is.iscourse.repository;

import java.util.List;
import java.util.Optional;

import org.is.iscourse.model.entity.MaintenanceRecord;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class MaintenanceRecordRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public MaintenanceRecord save(MaintenanceRecord record) {
        if (record.getMaintenanceId() == null) {
            entityManager.persist(record);
            return record;
        } else {
            return entityManager.merge(record);
        }
    }
    
    public List<MaintenanceRecord> findByResourceId(Long resourceId) {
        String jpql = "SELECT mr FROM MaintenanceRecord mr WHERE mr.resourceId = :resourceId ORDER BY mr.performedAt DESC";
        return entityManager.createQuery(jpql, MaintenanceRecord.class)
                .setParameter("resourceId", resourceId)
                .getResultList();
    }
    
    public List<MaintenanceRecord> findByPerformedBy(Long userId) {
        String jpql = "SELECT mr FROM MaintenanceRecord mr WHERE mr.performedBy.userId = :userId ORDER BY mr.performedAt DESC";
        return entityManager.createQuery(jpql, MaintenanceRecord.class)
                .setParameter("userId", userId)
                .getResultList();
    }
    
    public Optional<MaintenanceRecord> findById(Long maintenanceId) {
        MaintenanceRecord record = entityManager.find(MaintenanceRecord.class, maintenanceId);
        return Optional.ofNullable(record);
    }
    
    public List<MaintenanceRecord> findAll() {
        String jpql = "SELECT mr FROM MaintenanceRecord mr ORDER BY mr.performedAt DESC";
        return entityManager.createQuery(jpql, MaintenanceRecord.class).getResultList();
    }
}
