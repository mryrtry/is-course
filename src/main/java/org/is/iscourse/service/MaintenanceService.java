package org.is.iscourse.service;

import java.math.BigDecimal;
import java.util.List;

import org.is.iscourse.model.dto.MaintenanceMaterialDto;
import org.is.iscourse.model.dto.MaintenanceRecordDto;
import org.is.iscourse.model.entity.MaintenanceRecord;
import org.is.iscourse.model.entity.MaintenanceUsedMaterial;
import org.is.iscourse.model.entity.Material;
import org.is.iscourse.model.entity.User;
import org.is.iscourse.repository.MaintenanceRecordRepository;
import org.is.iscourse.repository.MaintenanceUsedMaterialRepository;
import org.is.iscourse.repository.MaterialRepository;
import org.is.iscourse.repository.ResourceRepository;
import org.is.iscourse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MaintenanceService {
    
    @Autowired
    private MaintenanceRecordRepository maintenanceRecordRepository;
    
    @Autowired
    private MaintenanceUsedMaterialRepository usedMaterialRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private MaterialRepository materialRepository;
    
    @Autowired
    private ResourceRepository resourceRepository;
    
    public MaintenanceRecord createMaintenanceRecord(MaintenanceRecordDto recordDto) {
        // Проверка существования ресурса
        resourceRepository.findById(recordDto.getResourceId())
            .orElseThrow(() -> new RuntimeException("Resource not found"));
        
        User performedBy = userRepository.findById(recordDto.getPerformedBy())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        MaintenanceRecord record = new MaintenanceRecord(recordDto.getResourceId(), recordDto.getWorkDescription());
        record.setPerformedBy(performedBy);
        record.setHoursSpent(recordDto.getHoursSpent());
        
        // Добавление использованных материалов
        if (recordDto.getUsedMaterials() != null) {
            for (MaintenanceMaterialDto materialDto : recordDto.getUsedMaterials()) {
                Material material = materialRepository.findById(materialDto.getMaterialId())
                    .orElseThrow(() -> new RuntimeException("Material not found"));
                
                // Проверка доступности материала
                if (material.getStockQty().compareTo(materialDto.getQty()) < 0) {
                    throw new RuntimeException("Insufficient stock for material: " + material.getName());
                }
                
                record.addUsedMaterial(material, materialDto.getQty(), materialDto.getUnitCost());
                
                // Обновление стоимости записи
                BigDecimal materialCost = materialDto.getQty().multiply(
                    materialDto.getUnitCost() != null ? materialDto.getUnitCost() : material.getUnitCost()
                );
                record.setCost(record.getCost().add(materialCost));
                
                // Списание материала со склада
                material.setStockQty(material.getStockQty().subtract(materialDto.getQty()));
                materialRepository.save(material);
            }
        }
        
        return maintenanceRecordRepository.save(record);
    }
    
    public List<MaintenanceRecord> getMaintenanceHistoryByResource(Long resourceId) {
        return maintenanceRecordRepository.findByResourceId(resourceId);
    }
    
    public List<MaintenanceRecord> getMaintenanceHistoryByUser(Long userId) {
        return maintenanceRecordRepository.findByPerformedBy(userId);
    }
    
    public List<MaintenanceRecord> getAllMaintenanceRecords() {
        return maintenanceRecordRepository.findAll();
    }
    
    public List<MaintenanceUsedMaterial> getUsedMaterials(Long maintenanceId) {
        return usedMaterialRepository.findByMaintenanceId(maintenanceId);
    }
    
    public MaintenanceRecord getMaintenanceRecordById(Long maintenanceId) {
        return maintenanceRecordRepository.findById(maintenanceId)
            .orElseThrow(() -> new RuntimeException("Maintenance record not found"));
    }
}
