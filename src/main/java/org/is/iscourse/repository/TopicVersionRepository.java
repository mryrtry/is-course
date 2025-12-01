package org.is.iscourse.repository;

import org.is.iscourse.model.entity.Topic;
import org.is.iscourse.model.entity.TopicVersion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TopicVersionRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public TopicVersion save(TopicVersion version) {
        entityManager.persist(version);
        return version;
    }
    
    public Optional<TopicVersion> findById(Long versionId) {
        TopicVersion version = entityManager.find(TopicVersion.class, versionId);
        return Optional.ofNullable(version);
    }
    
    public List<TopicVersion> findByTopicIdOrderByVersionNumberDesc(Long topicId) {
        TypedQuery<TopicVersion> query = entityManager.createQuery(
            "FROM TopicVersion WHERE topic.topicId = :topicId ORDER BY versionNumber DESC", 
            TopicVersion.class);
        query.setParameter("topicId", topicId);
        return query.getResultList();
    }
    
    public Optional<TopicVersion> findLatestVersionByTopicId(Long topicId) {
        TypedQuery<TopicVersion> query = entityManager.createQuery(
            "FROM TopicVersion WHERE topic.topicId = :topicId ORDER BY versionNumber DESC", 
            TopicVersion.class);
        query.setParameter("topicId", topicId);
        query.setMaxResults(1);
        try {
            TopicVersion version = query.getSingleResult();
            return Optional.of(version);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    
    public List<TopicVersion> findByCreatedBy(Long createdBy) {
        TypedQuery<TopicVersion> query = entityManager.createQuery(
            "FROM TopicVersion WHERE createdBy = :createdBy ORDER BY createdAt DESC", 
            TopicVersion.class);
        query.setParameter("createdBy", createdBy);
        return query.getResultList();
    }
    
    public Long countVersionsByTopicId(Long topicId) {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(*) FROM TopicVersion WHERE topic.topicId = :topicId", Long.class);
        query.setParameter("topicId", topicId);
        return query.getSingleResult();
    }
}