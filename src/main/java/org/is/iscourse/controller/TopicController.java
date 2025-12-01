package org.is.iscourse.controller;

import org.is.iscourse.model.dto.TopicDto;
import org.is.iscourse.model.dto.TopicVersionDto;
import org.is.iscourse.service.TopicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/topics")
public class TopicController {
    
    private final TopicService topicService;
    
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping
    public ResponseEntity<List<TopicDto>> getPublishedTopics() {
        return ResponseEntity.ok(topicService.getAllPublishedTopics());
    }

    @GetMapping("/search")
    public ResponseEntity<List<TopicDto>> searchTopics(@RequestParam String query) {
        return ResponseEntity.ok(topicService.searchTopics(query));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDto> getTopic(@PathVariable Long id) {
        Optional<TopicDto> topic = topicService.getTopicById(id);
        return topic.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/with-versions")
    public ResponseEntity<TopicDto> getTopicWithVersions(@PathVariable Long id) {
        Optional<TopicDto> topic = topicService.getTopicWithVersions(id);
        return topic.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<TopicDto>> getTopicsByAuthor(@PathVariable Long authorId) {
        return ResponseEntity.ok(topicService.getTopicsByAuthor(authorId));
    }

    @PostMapping
    public ResponseEntity<TopicDto> createTopic(@RequestBody TopicDto topicDto) {
        return ResponseEntity.ok(topicService.createTopic(topicDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicDto> updateTopic(
            @PathVariable Long id, 
            @RequestBody TopicDto topicDto) {
        Optional<TopicDto> updatedTopic = topicService.updateTopic(id, topicDto);
        return updatedTopic.map(ResponseEntity::ok)
                          .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        return topicService.deleteTopic(id) 
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/{topicId}/versions")
    public ResponseEntity<List<TopicVersionDto>> getTopicVersions(@PathVariable Long topicId) {
        return ResponseEntity.ok(topicService.getTopicVersions(topicId));
    }

    @PostMapping("/{topicId}/versions")
    public ResponseEntity<TopicVersionDto> createTopicVersion(
            @PathVariable Long topicId,
            @RequestBody TopicVersionDto versionDto) {
        versionDto.setTopicId(topicId);
        Optional<TopicVersionDto> version = topicService.createTopicVersion(versionDto);
        return version.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
}