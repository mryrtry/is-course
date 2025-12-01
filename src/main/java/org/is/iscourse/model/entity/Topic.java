package org.is.iscourse.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "topics", schema = "fab")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_id")
    private Long topicId;
    
    @Column(name = "title", nullable = false)
    private String title;
    
    @Column(name = "body", columnDefinition = "TEXT")
    private String body;
    
    @Column(name = "author_id")
    private Long authorId;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "status")
    private String status = "draft";
    
    // Исправляем маппинг для NUMERIC(3,2) - используем Double без precision/scale
    @Column(name = "rating")
    private Double rating = 0.0;
    
    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TopicVersion> versions = new ArrayList<>();
    
    public Topic() {}
    
    public Topic(String title, String body, Long authorId) {
        this.title = title;
        this.body = body;
        this.authorId = authorId;
        this.createdAt = LocalDateTime.now();
    }
    
    public Long getTopicId() { return topicId; }
    public void setTopicId(Long topicId) { this.topicId = topicId; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }
    
    public Long getAuthorId() { return authorId; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }
    
    public List<TopicVersion> getVersions() { return versions; }
    public void setVersions(List<TopicVersion> versions) { this.versions = versions; }
    
    public void addVersion(TopicVersion version) {
        versions.add(version);
        version.setTopic(this);
    }
}