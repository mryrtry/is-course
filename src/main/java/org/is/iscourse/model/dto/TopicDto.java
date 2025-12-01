package org.is.iscourse.model.dto;

import java.time.LocalDateTime;
import java.util.List;


public class TopicDto {
    private Long topicId;
    private String title;
    private String body;
    private Long authorId;
    private LocalDateTime createdAt;
    private String status;
    private Double rating;
    private List<TopicVersionDto> versions;

    public TopicDto() {}
    
    public TopicDto(String title, String body, Long authorId) {
        this.title = title;
        this.body = body;
        this.authorId = authorId;
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
    
    public List<TopicVersionDto> getVersions() { return versions; }
    public void setVersions(List<TopicVersionDto> versions) { this.versions = versions; }
}