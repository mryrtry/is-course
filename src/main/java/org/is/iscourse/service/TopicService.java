package org.is.iscourse.service;

import org.is.iscourse.model.dto.*;
import org.is.iscourse.model.entity.Topic;
import org.is.iscourse.model.entity.TopicVersion;
import org.is.iscourse.repository.TopicRepository;
import org.is.iscourse.repository.TopicVersionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TopicService {
    
    private final TopicRepository topicRepository;
    private final TopicVersionRepository topicVersionRepository;
    
    public TopicService(TopicRepository topicRepository, TopicVersionRepository topicVersionRepository) {
        this.topicRepository = topicRepository;
        this.topicVersionRepository = topicVersionRepository;
    }
    
    public List<TopicDto> getAllPublishedTopics() {
        return topicRepository.findByStatus("published").stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<TopicDto> searchTopics(String query) {
        return topicRepository.searchPublishedTopics(query).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public Optional<TopicDto> getTopicById(Long topicId) {
        return topicRepository.findById(topicId)
                .map(this::convertToDto);
    }
    
    public Optional<TopicDto> getTopicWithVersions(Long topicId) {
        return topicRepository.findByIdWithVersions(topicId)
                .map(this::convertToDto);
    }
    
    public List<TopicDto> getTopicsByAuthor(Long authorId) {
        return topicRepository.findByAuthorId(authorId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public TopicDto createTopic(TopicDto topicDto) {
        Topic topic = new Topic(topicDto.getTitle(), topicDto.getBody(), topicDto.getAuthorId());
        if (topicDto.getStatus() != null) {
            topic.setStatus(topicDto.getStatus());
        }
        
        Topic savedTopic = topicRepository.save(topic);
        
        TopicVersion version = new TopicVersion(savedTopic, topicDto.getBody(), topicDto.getAuthorId());
        topicVersionRepository.save(version);
        
        return convertToDto(savedTopic);
    }
    
    @Transactional
    public Optional<TopicDto> updateTopic(Long topicId, TopicDto topicDto) {
        return topicRepository.findById(topicId).map(topic -> {
            boolean contentChanged = false;
            
            if (topicDto.getTitle() != null) {
                topic.setTitle(topicDto.getTitle());
            }
            if (topicDto.getBody() != null && !topicDto.getBody().equals(topic.getBody())) {
                topic.setBody(topicDto.getBody());
                contentChanged = true;
            }
            if (topicDto.getStatus() != null) {
                topic.setStatus(topicDto.getStatus());
            }
            if (topicDto.getRating() != null) {
                topic.setRating(topicDto.getRating());
            }
            
            Topic updatedTopic = topicRepository.update(topic);
            
            if (contentChanged) {
                createNewVersion(updatedTopic, topicDto.getBody());
            }
            
            return convertToDto(updatedTopic);
        });
    }
    
    @Transactional
    public boolean deleteTopic(Long topicId) {
        try {
            topicRepository.deleteById(topicId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    @Transactional
    public Optional<TopicVersionDto> createTopicVersion(TopicVersionDto versionDto) {
        return topicRepository.findById(versionDto.getTopicId()).map(topic -> {
            Long nextVersionNumber = topicVersionRepository.countVersionsByTopicId(topic.getTopicId()) + 1;
            
            TopicVersion version = new TopicVersion(topic, versionDto.getContent(), versionDto.getCreatedBy());
            version.setVersionNumber(nextVersionNumber.intValue());
            
            TopicVersion savedVersion = topicVersionRepository.save(version);
            
            topic.setBody(versionDto.getContent());
            topicRepository.update(topic);
            
            return convertToVersionDto(savedVersion);
        });
    }
    
    public List<TopicVersionDto> getTopicVersions(Long topicId) {
        return topicVersionRepository.findByTopicIdOrderByVersionNumberDesc(topicId).stream()
                .map(this::convertToVersionDto)
                .collect(Collectors.toList());
    }
    
    private void createNewVersion(Topic topic, String content) {
        Long nextVersionNumber = topicVersionRepository.countVersionsByTopicId(topic.getTopicId()) + 1;
        
        TopicVersion version = new TopicVersion(topic, content, topic.getAuthorId());
        version.setVersionNumber(nextVersionNumber.intValue());
        
        topicVersionRepository.save(version);
    }
    
    private TopicDto convertToDto(Topic topic) {
        TopicDto dto = new TopicDto();
        dto.setTopicId(topic.getTopicId());
        dto.setTitle(topic.getTitle());
        dto.setBody(topic.getBody());
        dto.setAuthorId(topic.getAuthorId());
        dto.setCreatedAt(topic.getCreatedAt());
        dto.setStatus(topic.getStatus());
        dto.setRating(topic.getRating());
        
        if (topic.getVersions() != null && !topic.getVersions().isEmpty()) {
            dto.setVersions(topic.getVersions().stream()
                    .map(this::convertToVersionDto)
                    .collect(Collectors.toList()));
        }
        
        return dto;
    }
    
    private TopicVersionDto convertToVersionDto(TopicVersion version) {
        TopicVersionDto dto = new TopicVersionDto();
        dto.setVersionId(version.getVersionId());
        dto.setTopicId(version.getTopic().getTopicId());
        dto.setVersionNumber(version.getVersionNumber());
        dto.setContent(version.getContent());
        dto.setCreatedAt(version.getCreatedAt());
        dto.setCreatedBy(version.getCreatedBy());
        return dto;
    }
}