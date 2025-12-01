package org.is.iscourse.model.dto;

import java.time.LocalDateTime;

public class TopicVersionDto {
    private Long versionId;
    private Long topicId;
    private Integer versionNumber;
    private String content;
    private LocalDateTime createdAt;
    private Long createdBy;

    public TopicVersionDto() {}
    
    public TopicVersionDto(Long topicId, String content, Long createdBy) {
        this.topicId = topicId;
        this.content = content;
        this.createdBy = createdBy;
    }

    public Long getVersionId() { return versionId; }
    public void setVersionId(Long versionId) { this.versionId = versionId; }
    
    public Long getTopicId() { return topicId; }
    public void setTopicId(Long topicId) { this.topicId = topicId; }
    
    public Integer getVersionNumber() { return versionNumber; }
    public void setVersionNumber(Integer versionNumber) { this.versionNumber = versionNumber; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public Long getCreatedBy() { return createdBy; }
    public void setCreatedBy(Long createdBy) { this.createdBy = createdBy; }
}