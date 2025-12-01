package org.is.iscourse.controller;

import java.util.List;

import org.is.iscourse.model.dto.TopicVersionDto;
import org.is.iscourse.service.TopicVersionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/topic-versions")
public class TopicVersionController {
    
    private final TopicVersionService topicVersionService;
    
    public TopicVersionController(TopicVersionService topicVersionService) {
        this.topicVersionService = topicVersionService;
    }
    
    @GetMapping("/topic/{topicId}")
    public ResponseEntity<List<TopicVersionDto>> getVersionsByTopic(@PathVariable Long topicId) {
        List<TopicVersionDto> versions = topicVersionService.getVersionsByTopicId(topicId);
        return ResponseEntity.ok(versions);
    }
    
    @GetMapping("/{versionId}")
    public ResponseEntity<TopicVersionDto> getVersionById(@PathVariable Long versionId) {
        return topicVersionService.getVersionById(versionId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/topic/{topicId}/latest")
    public ResponseEntity<TopicVersionDto> getLatestVersion(@PathVariable Long topicId) {
        return topicVersionService.getLatestVersionByTopicId(topicId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<TopicVersionDto> createVersion(@RequestBody TopicVersionDto versionDto) {
        return topicVersionService.createVersion(versionDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TopicVersionDto>> getVersionsByUser(@PathVariable Long userId) {
        List<TopicVersionDto> versions = topicVersionService.getVersionsByUser(userId);
        return ResponseEntity.ok(versions);
    }
    
    @GetMapping("/topic/{topicId}/count")
    public ResponseEntity<Long> getVersionCount(@PathVariable Long topicId) {
        Long count = topicVersionService.countVersionsByTopicId(topicId);
        return ResponseEntity.ok(count);
    }
}