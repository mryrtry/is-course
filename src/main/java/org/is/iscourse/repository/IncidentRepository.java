package org.is.iscourse.repository;

import org.is.iscourse.model.entity.Incident;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class IncidentRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public Incident save(Incident incident) {
        entityManager.persist(incident);
        return incident;
    }
    
    public Optional<Incident> findById(Long incidentId) {
        Incident incident = entityManager.find(Incident.class, incidentId);
        return Optional.ofNullable(incident);
    }
    
    public List<Incident> findAll() {
        TypedQuery<Incident> query = entityManager.createQuery(
            "FROM Incident ORDER BY createdAt DESC", Incident.class);
        return query.getResultList();
    }
    
    public List<Incident> findByStatus(String status) {
        TypedQuery<Incident> query = entityManager.createQuery(
            "FROM Incident WHERE status = :status ORDER BY createdAt DESC", Incident.class);
        query.setParameter("status", status);
        return query.getResultList();
    }
    
    public List<Incident> findByReportedBy(Long reportedBy) {
        TypedQuery<Incident> query = entityManager.createQuery(
            "FROM Incident WHERE reportedBy = :reportedBy ORDER BY createdAt DESC", Incident.class);
        query.setParameter("reportedBy", reportedBy);
        return query.getResultList();
    }
    
    public List<Incident> findByAssignedTo(Long assignedTo) {
        TypedQuery<Incident> query = entityManager.createQuery(
            "FROM Incident WHERE assignedTo = :assignedTo ORDER BY createdAt DESC", Incident.class);
        query.setParameter("assignedTo", assignedTo);
        return query.getResultList();
    }
    
    public List<Incident> findByResourceId(Long resourceId) {
        TypedQuery<Incident> query = entityManager.createQuery(
            "FROM Incident WHERE resourceId = :resourceId ORDER BY createdAt DESC", Incident.class);
        query.setParameter("resourceId", resourceId);
        return query.getResultList();
    }
    
    public List<Incident> findBySeverity(String severity) {
        TypedQuery<Incident> query = entityManager.createQuery(
            "FROM Incident WHERE severity = :severity ORDER BY createdAt DESC", Incident.class);
        query.setParameter("severity", severity);
        return query.getResultList();
    }
    
    public List<Incident> findNewIncidents() {
        TypedQuery<Incident> query = entityManager.createQuery(
            "FROM Incident WHERE status = 'new' ORDER BY createdAt DESC", Incident.class);
        return query.getResultList();
    }
    
    public Incident update(Incident incident) {
        return entityManager.merge(incident);
    }
    
    public void deleteById(Long incidentId) {
        Incident incident = entityManager.find(Incident.class, incidentId);
        if (incident != null) {
            entityManager.remove(incident);
        }
    }
}