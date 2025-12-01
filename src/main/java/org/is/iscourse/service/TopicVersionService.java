package org.is.iscourse.service;

import org.is.iscourse.model.dto.TopicVersionDto;
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
public class TopicVersionService {
    
    private final TopicVersionRepository topicVersionRepository;
    private final TopicRepository topicRepository;
    
    public TopicVersionService(TopicVersionRepository topicVersionRepository, TopicRepository topicRepository) {
        this.topicVersionRepository = topicVersionRepository;
        this.topicRepository = topicRepository;
    }
    
    public List<TopicVersionDto> getVersionsByTopicId(Long topicId) {
        return topicVersionRepository.findByTopicIdOrderByVersionNumberDesc(topicId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public Optional<TopicVersionDto> getVersionById(Long versionId) {
        return topicVersionRepository.findById(versionId)
                .map(this::convertToDto);
    }
    
    public Optional<TopicVersionDto> getLatestVersionByTopicId(Long topicId) {
        return topicVersionRepository.findLatestVersionByTopicId(topicId)
                .map(this::convertToDto);
    }
    
    public List<TopicVersionDto> getVersionsByUser(Long userId) {
        return topicVersionRepository.findByCreatedBy(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public Optional<TopicVersionDto> createVersion(TopicVersionDto versionDto) {
        Optional<Topic> topic = topicRepository.findById(versionDto.getTopicId());
        return topic.map(t -> {
            Long nextVersionNumber = topicVersionRepository.countVersionsByTopicId(t.getTopicId()) + 1;
            
            TopicVersion version = new TopicVersion(t, versionDto.getContent(), versionDto.getCreatedBy());
            version.setVersionNumber(nextVersionNumber.intValue());
            
            TopicVersion savedVersion = topicVersionRepository.save(version);
            return convertToDto(savedVersion);
        });
    }
    
    public Long countVersionsByTopicId(Long topicId) {
        return topicVersionRepository.countVersionsByTopicId(topicId);
    }
    
    private TopicVersionDto convertToDto(TopicVersion version) {
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