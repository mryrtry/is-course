package org.is.iscourse.service;

import org.is.iscourse.model.dto.IncidentDto;
import org.is.iscourse.model.entity.Incident;
import org.is.iscourse.repository.IncidentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IncidentService {
    
    private final IncidentRepository incidentRepository;
    
    public IncidentService(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }
    
    
    @Transactional
    public IncidentDto createIncident(IncidentDto incidentDto) {
        Incident incident = new Incident(
            incidentDto.getReportedBy(),
            incidentDto.getResourceId(),
            incidentDto.getType(),
            incidentDto.getDescription()
        );
        
        if (incidentDto.getSeverity() != null) {
            incident.setSeverity(incidentDto.getSeverity());
        }
        
        Incident savedIncident = incidentRepository.save(incident);
        return convertToDto(savedIncident);
    }

    public List<IncidentDto> getAllIncidents() {
        return incidentRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<IncidentDto> getNewIncidents() {
        return incidentRepository.findNewIncidents().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<IncidentDto> getIncidentsAssignedTo(Long assignedTo) {
        return incidentRepository.findByAssignedTo(assignedTo).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<IncidentDto> getIncidentsByResource(Long resourceId) {
        return incidentRepository.findByResourceId(resourceId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<IncidentDto> getIncidentsByUser(Long userId) {
        return incidentRepository.findByReportedBy(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public Optional<IncidentDto> getIncidentById(Long incidentId) {
        return incidentRepository.findById(incidentId)
                .map(this::convertToDto);
    }
    
    public List<IncidentDto> getIncidentsByStatus(String status) {
        return incidentRepository.findByStatus(status).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public Optional<IncidentDto> updateIncident(Long incidentId, IncidentDto incidentDto) {
        return incidentRepository.findById(incidentId).map(incident -> {
            if (incidentDto.getAssignedTo() != null) {
                incident.setAssignedTo(incidentDto.getAssignedTo());
            }
            if (incidentDto.getStatus() != null) {
                incident.setStatus(incidentDto.getStatus());
            }
            if (incidentDto.getSeverity() != null) {
                incident.setSeverity(incidentDto.getSeverity());
            }
            if (incidentDto.getResolutionNotes() != null) {
                incident.setResolutionNotes(incidentDto.getResolutionNotes());
            }
            if (incidentDto.getType() != null) {
                incident.setType(incidentDto.getType());
            }
            if (incidentDto.getDescription() != null) {
                incident.setDescription(incidentDto.getDescription());
            }
            
            Incident updatedIncident = incidentRepository.update(incident);
            return convertToDto(updatedIncident);
        });
    }
    
    @Transactional
    public boolean deleteIncident(Long incidentId) {
        try {
            incidentRepository.deleteById(incidentId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    private IncidentDto convertToDto(Incident incident) {
        IncidentDto dto = new IncidentDto();
        dto.setIncidentId(incident.getIncidentId());
        dto.setReportedBy(incident.getReportedBy());
        dto.setResourceId(incident.getResourceId());
        dto.setAssignedTo(incident.getAssignedTo());
        dto.setType(incident.getType());
        dto.setDescription(incident.getDescription());
        dto.setStatus(incident.getStatus());
        dto.setSeverity(incident.getSeverity());
        dto.setCreatedAt(incident.getCreatedAt());
        dto.setResolutionNotes(incident.getResolutionNotes());
        return dto;
    }
}