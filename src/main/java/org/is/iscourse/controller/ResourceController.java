package org.is.iscourse.controller;

import java.util.List;

import org.is.iscourse.model.dto.ResourceDto;
import org.is.iscourse.model.entity.Resource;
import org.is.iscourse.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {
    
    @Autowired
    private ResourceService resourceService;
    
    @PostMapping
    public ResponseEntity<Resource> createResource(@RequestBody ResourceDto resourceDto) {
        Resource resource = resourceService.createResource(resourceDto);
        return ResponseEntity.ok(resource);
    }
    
    @PutMapping("/{resourceId}")
    public ResponseEntity<Resource> updateResource(
            @PathVariable Long resourceId, 
            @RequestBody ResourceDto resourceDto) {
        Resource resource = resourceService.updateResource(resourceId, resourceDto);
        return ResponseEntity.ok(resource);
    }
    
    @GetMapping
    public ResponseEntity<List<Resource>> getAllResources() {
        List<Resource> resources = resourceService.getAllResources();
        return ResponseEntity.ok(resources);
    }
    
    @GetMapping("/{resourceId}")
    public ResponseEntity<Resource> getResourceById(@PathVariable Long resourceId) {
        Resource resource = resourceService.getResourceById(resourceId);
        return ResponseEntity.ok(resource);
    }
    
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Resource>> getResourcesByType(@PathVariable String type) {
        List<Resource> resources = resourceService.getResourcesByType(type);
        return ResponseEntity.ok(resources);
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Resource>> getResourcesByStatus(@PathVariable String status) {
        List<Resource> resources = resourceService.getResourcesByStatus(status);
        return ResponseEntity.ok(resources);
    }
    
    @DeleteMapping("/{resourceId}")
    public ResponseEntity<String> deleteResource(@PathVariable Long resourceId) {
        resourceService.deleteResource(resourceId);
        return ResponseEntity.ok("Resource deleted successfully");
    }
}