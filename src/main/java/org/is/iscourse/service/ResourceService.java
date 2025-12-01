package org.is.iscourse.service;

import java.math.BigDecimal;
import java.util.List;

import org.is.iscourse.model.dto.ResourceDto;
import org.is.iscourse.model.entity.Resource;
import org.is.iscourse.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ResourceService {
    
    @Autowired
    private ResourceRepository resourceRepository;
    
    public Resource createResource(ResourceDto resourceDto) {
        if (resourceDto.getSerialNumber() != null && 
            resourceRepository.findBySerialNumber(resourceDto.getSerialNumber()).isPresent()) {
            throw new RuntimeException("Resource with this serial number already exists");
        }
        
        Resource resource = new Resource(resourceDto.getName(), resourceDto.getType());
        resource.setSerialNumber(resourceDto.getSerialNumber());
        resource.setModel(resourceDto.getModel());
        resource.setTechnicalSpecs(resourceDto.getTechnicalSpecs() != null ? resourceDto.getTechnicalSpecs() : "{}");
        resource.setStatus(resourceDto.getStatus() != null ? resourceDto.getStatus() : "operational");
        resource.setUsageCostPerHour(resourceDto.getUsageCostPerHour() != null ? resourceDto.getUsageCostPerHour() : BigDecimal.ZERO);
        
        return resourceRepository.save(resource);
    }
    
    public Resource updateResource(Long resourceId, ResourceDto resourceDto) {
        Resource resource = resourceRepository.findById(resourceId)
            .orElseThrow(() -> new RuntimeException("Resource not found"));

        if (resourceDto.getSerialNumber() != null && 
            resourceRepository.findBySerialNumber(resourceDto.getSerialNumber())
                .filter(r -> !r.getResourceId().equals(resourceId))
                .isPresent()) {
            throw new RuntimeException("Another resource with this serial number already exists");
        }
        
        resource.setName(resourceDto.getName());
        resource.setType(resourceDto.getType());
        resource.setSerialNumber(resourceDto.getSerialNumber());
        resource.setModel(resourceDto.getModel());
        
        if (resourceDto.getTechnicalSpecs() != null) {
            resource.setTechnicalSpecs(resourceDto.getTechnicalSpecs());
        }
        
        if (resourceDto.getStatus() != null) {
            resource.setStatus(resourceDto.getStatus());
        }
        
        if (resourceDto.getUsageCostPerHour() != null) {
            resource.setUsageCostPerHour(resourceDto.getUsageCostPerHour());
        }
        
        return resourceRepository.save(resource);
    }
    
    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }
    
    public List<Resource> getResourcesByType(String type) {
        return resourceRepository.findByType(type);
    }
    
    public List<Resource> getResourcesByStatus(String status) {
        return resourceRepository.findByStatus(status);
    }
    
    public Resource getResourceById(Long resourceId) {
        return resourceRepository.findById(resourceId)
            .orElseThrow(() -> new RuntimeException("Resource not found"));
    }
    
    public void deleteResource(Long resourceId) {
        Resource resource = resourceRepository.findById(resourceId)
            .orElseThrow(() -> new RuntimeException("Resource not found"));

        resourceRepository.delete(resource);
    }
}
