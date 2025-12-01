package org.is.iscourse.repository;

import java.util.List;
import java.util.Optional;

import org.is.iscourse.model.entity.Supplier;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class SupplierRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public Supplier save(Supplier supplier) {
        if (supplier.getSupplierId() == null) {
            entityManager.persist(supplier);
            return supplier;
        } else {
            return entityManager.merge(supplier);
        }
    }
    
    public List<Supplier> findAll() {
        String jpql = "SELECT s FROM Supplier s ORDER BY s.name";
        return entityManager.createQuery(jpql, Supplier.class).getResultList();
    }
    
    public Optional<Supplier> findById(Long supplierId) {
        Supplier supplier = entityManager.find(Supplier.class, supplierId);
        return Optional.ofNullable(supplier);
    }
}