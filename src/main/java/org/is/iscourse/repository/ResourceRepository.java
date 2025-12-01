package org.is.iscourse.repository;

import java.util.List;
import java.util.Optional;

import org.is.iscourse.model.entity.Resource;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

@Repository
public class ResourceRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public Resource save(Resource resource) {
        if (resource.getResourceId() == null) {
            entityManager.persist(resource);
            return resource;
        } else {
            return entityManager.merge(resource);
        }
    }
    
    public List<Resource> findAll() {
        String jpql = "SELECT r FROM Resource r ORDER BY r.name";
        return entityManager.createQuery(jpql, Resource.class).getResultList();
    }
    
    public Optional<Resource> findById(Long resourceId) {
        Resource resource = entityManager.find(Resource.class, resourceId);
        return Optional.ofNullable(resource);
    }
    
    public List<Resource> findByType(String type) {
        String jpql = "SELECT r FROM Resource r WHERE r.type = :type ORDER BY r.name";
        return entityManager.createQuery(jpql, Resource.class)
                .setParameter("type", type)
                .getResultList();
    }
    
    public List<Resource> findByStatus(String status) {
        String jpql = "SELECT r FROM Resource r WHERE r.status = :status ORDER BY r.name";
        return entityManager.createQuery(jpql, Resource.class)
                .setParameter("status", status)
                .getResultList();
    }
    
    public Optional<Resource> findBySerialNumber(String serialNumber) {
        String jpql = "SELECT r FROM Resource r WHERE r.serialNumber = :serialNumber";
        try {
            Resource resource = entityManager.createQuery(jpql, Resource.class)
                    .setParameter("serialNumber", serialNumber)
                    .getSingleResult();
            return Optional.of(resource);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
      // Добавьте этот метод
      public void delete(Resource resource) {
        entityManager.remove(entityManager.contains(resource) ? resource : entityManager.merge(resource));
    }
    
    // Или альтернативный вариант с ID:
    public void deleteById(Long resourceId) {
        Resource resource = entityManager.find(Resource.class, resourceId);
        if (resource != null) {
            entityManager.remove(resource);
        }
    }
}