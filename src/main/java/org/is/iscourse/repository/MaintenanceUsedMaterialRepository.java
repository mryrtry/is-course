package org.is.iscourse.repository;

import java.util.List;

import org.is.iscourse.model.entity.MaintenanceUsedMaterial;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class MaintenanceUsedMaterialRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public MaintenanceUsedMaterial save(MaintenanceUsedMaterial usedMaterial) {
        entityManager.persist(usedMaterial);
        return usedMaterial;
    }
    
    public List<MaintenanceUsedMaterial> findByMaintenanceId(Long maintenanceId) {
        String jpql = "SELECT mum FROM MaintenanceUsedMaterial mum WHERE mum.maintenanceRecord.maintenanceId = :maintenanceId";
        return entityManager.createQuery(jpql, MaintenanceUsedMaterial.class)
                .setParameter("maintenanceId", maintenanceId)
                .getResultList();
    }
}