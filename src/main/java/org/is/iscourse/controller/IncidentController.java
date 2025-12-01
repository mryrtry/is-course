package org.is.iscourse.controller;

import org.is.iscourse.model.dto.IncidentDto;
import org.is.iscourse.service.IncidentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incidents")
public class IncidentController {
    
    private final IncidentService incidentService;
    
    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    @PostMapping
    public ResponseEntity<IncidentDto> createIncident(@RequestBody IncidentDto incidentDto) {
        return ResponseEntity.ok(incidentService.createIncident(incidentDto));
    }

    @GetMapping
    public ResponseEntity<List<IncidentDto>> getAllIncidents() {
        return ResponseEntity.ok(incidentService.getAllIncidents());
    }

    @GetMapping("/new")
    public ResponseEntity<List<IncidentDto>> getNewIncidents() {
        return ResponseEntity.ok(incidentService.getNewIncidents());
    }

    @GetMapping("/assigned-to/{assignedTo}")
    public ResponseEntity<List<IncidentDto>> getIncidentsAssignedTo(@PathVariable Long assignedTo) {
        return ResponseEntity.ok(incidentService.getIncidentsAssignedTo(assignedTo));
    }

    @GetMapping("/resource/{resourceId}")
    public ResponseEntity<List<IncidentDto>> getIncidentsByResource(@PathVariable Long resourceId) {
        return ResponseEntity.ok(incidentService.getIncidentsByResource(resourceId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<IncidentDto>> getIncidentsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(incidentService.getIncidentsByUser(userId));
    }

    @GetMapping("/{incidentId}")
    public ResponseEntity<IncidentDto> getIncidentById(@PathVariable Long incidentId) {
        return incidentService.getIncidentById(incidentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<IncidentDto>> getIncidentsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(incidentService.getIncidentsByStatus(status));
    }
    
    @PutMapping("/{incidentId}")
    public ResponseEntity<IncidentDto> updateIncident(
            @PathVariable Long incidentId,
            @RequestBody IncidentDto incidentDto) {
        return incidentService.updateIncident(incidentId, incidentDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    
    @DeleteMapping("/{incidentId}")
    public ResponseEntity<Void> deleteIncident(@PathVariable Long incidentId) {
        return incidentService.deleteIncident(incidentId)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }
}