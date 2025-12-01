package org.is.iscourse.repository;

import java.util.List;
import java.util.Optional;

import org.is.iscourse.model.entity.Material;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class MaterialRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public Material save(Material material) {
        if (material.getMaterialId() == null) {
            entityManager.persist(material);
            return material;
        } else {
            return entityManager.merge(material);
        }
    }
    
    public List<Material> findAll() {
        String jpql = "SELECT m FROM Material m ORDER BY m.name";
        return entityManager.createQuery(jpql, Material.class).getResultList();
    }
    
    public Optional<Material> findById(Long materialId) {
        Material material = entityManager.find(Material.class, materialId);
        return Optional.ofNullable(material);
    }
    
    public List<Material> findByCategory(String category) {
        String jpql = "SELECT m FROM Material m WHERE m.category = :category ORDER BY m.name";
        return entityManager.createQuery(jpql, Material.class)
                .setParameter("category", category)
                .getResultList();
    }
    
    public List<Material> findLowStock() {
        String jpql = "SELECT m FROM Material m WHERE m.stockQty <= m.minStockThreshold ORDER BY m.stockQty";
        return entityManager.createQuery(jpql, Material.class).getResultList();
    }
    
    public void delete(Material material) {
        entityManager.remove(entityManager.contains(material) ? material : entityManager.merge(material));
    }
}
