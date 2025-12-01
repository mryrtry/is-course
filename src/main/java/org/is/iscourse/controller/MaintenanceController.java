package org.is.iscourse.controller;

import java.util.List;

import org.is.iscourse.model.dto.MaintenanceRecordDto;
import org.is.iscourse.model.entity.MaintenanceRecord;
import org.is.iscourse.model.entity.MaintenanceUsedMaterial;
import org.is.iscourse.service.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/maintenance")
public class MaintenanceController {
    
    @Autowired
    private MaintenanceService maintenanceService;
    
    @PostMapping
    public ResponseEntity<MaintenanceRecord> createMaintenanceRecord(@RequestBody MaintenanceRecordDto recordDto) {
        MaintenanceRecord record = maintenanceService.createMaintenanceRecord(recordDto);
        return ResponseEntity.ok(record);
    }
    
    @GetMapping("/resource/{resourceId}")
    public ResponseEntity<List<MaintenanceRecord>> getResourceMaintenanceHistory(@PathVariable Long resourceId) {
        List<MaintenanceRecord> records = maintenanceService.getMaintenanceHistoryByResource(resourceId);
        return ResponseEntity.ok(records);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MaintenanceRecord>> getUserMaintenanceHistory(@PathVariable Long userId) {
        List<MaintenanceRecord> records = maintenanceService.getMaintenanceHistoryByUser(userId);
        return ResponseEntity.ok(records);
    }
    
    @GetMapping
    public ResponseEntity<List<MaintenanceRecord>> getAllMaintenanceRecords() {
        List<MaintenanceRecord> records = maintenanceService.getAllMaintenanceRecords();
        return ResponseEntity.ok(records);
    }
    
    @GetMapping("/{maintenanceId}/materials")
    public ResponseEntity<List<MaintenanceUsedMaterial>> getUsedMaterials(@PathVariable Long maintenanceId) {
        List<MaintenanceUsedMaterial> materials = maintenanceService.getUsedMaterials(maintenanceId);
        return ResponseEntity.ok(materials);
    }
    
    @GetMapping("/{maintenanceId}")
    public ResponseEntity<MaintenanceRecord> getMaintenanceRecord(@PathVariable Long maintenanceId) {
        MaintenanceRecord record = maintenanceService.getMaintenanceRecordById(maintenanceId);
        return ResponseEntity.ok(record);
    }
}