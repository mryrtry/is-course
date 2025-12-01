package org.is.iscourse.repository;

import org.is.iscourse.model.entity.Topic;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TopicRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public Topic save(Topic topic) {
        entityManager.persist(topic);
        return topic;
    }
    
    public Optional<Topic> findById(Long topicId) {
        Topic topic = entityManager.find(Topic.class, topicId);
        return Optional.ofNullable(topic);
    }
    
    public List<Topic> findByStatus(String status) {
        TypedQuery<Topic> query = entityManager.createQuery(
            "FROM Topic WHERE status = :status", Topic.class);
        query.setParameter("status", status);
        return query.getResultList();
    }
    
    public List<Topic> findByAuthorId(Long authorId) {
        TypedQuery<Topic> query = entityManager.createQuery(
            "FROM Topic WHERE authorId = :authorId", Topic.class);
        query.setParameter("authorId", authorId);
        return query.getResultList();
    }
    
    public List<Topic> searchPublishedTopics(String searchQuery) {
        TypedQuery<Topic> query = entityManager.createQuery(
            "FROM Topic WHERE (LOWER(title) LIKE LOWER(:query) OR " +
            "LOWER(body) LIKE LOWER(:query)) AND status = 'published'", Topic.class);
        query.setParameter("query", "%" + searchQuery + "%");
        return query.getResultList();
    }
    
    public Optional<Topic> findByIdWithVersions(Long topicId) {
        TypedQuery<Topic> query = entityManager.createQuery(
            "SELECT t FROM Topic t LEFT JOIN FETCH t.versions WHERE t.topicId = :topicId", Topic.class);
        query.setParameter("topicId", topicId);
        try {
            Topic topic = query.getSingleResult();
            return Optional.of(topic);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    
    public Topic update(Topic topic) {
        return entityManager.merge(topic);
    }
    
    public void deleteById(Long topicId) {
        Topic topic = entityManager.find(Topic.class, topicId);
        if (topic != null) {
            entityManager.remove(topic);
        }
    }
}