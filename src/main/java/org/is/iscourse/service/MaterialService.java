package org.is.iscourse.service;

import java.math.BigDecimal;
import java.util.List;

import org.is.iscourse.model.dto.MaterialDto;
import org.is.iscourse.model.entity.Material;
import org.is.iscourse.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MaterialService {
    
    @Autowired
    private MaterialRepository materialRepository;
    
    public Material createMaterial(MaterialDto materialDto) {
        Material material = new Material(materialDto.getName(), materialDto.getCategory());
        material.setSpecifications(materialDto.getSpecifications() != null ? materialDto.getSpecifications() : "{}");
        material.setUnit(materialDto.getUnit() != null ? materialDto.getUnit() : "pcs");
        material.setUnitCost(materialDto.getUnitCost() != null ? materialDto.getUnitCost() : BigDecimal.ZERO);
        material.setStockQty(materialDto.getStockQty() != null ? materialDto.getStockQty() : BigDecimal.ZERO);
        material.setMinStockThreshold(materialDto.getMinStockThreshold() != null ? materialDto.getMinStockThreshold() : BigDecimal.ZERO);
        
        return materialRepository.save(material);
    }
    
    public List<Material> getAllMaterials() {
        return materialRepository.findAll();
    }
    
    public List<Material> getMaterialsByCategory(String category) {
        return materialRepository.findByCategory(category);
    }
    
    public List<Material> getLowStockMaterials() {
        return materialRepository.findLowStock();
    }
    
    public Material getMaterialById(Long materialId) {
        return materialRepository.findById(materialId)
            .orElseThrow(() -> new RuntimeException("Material not found with id: " + materialId));
    }
    
    public Material updateMaterial(Long materialId, MaterialDto materialDto) {
        Material material = getMaterialById(materialId);
        
        material.setName(materialDto.getName());
        material.setCategory(materialDto.getCategory());
        if (materialDto.getSpecifications() != null) {
            material.setSpecifications(materialDto.getSpecifications());
        }
        if (materialDto.getUnit() != null) {
            material.setUnit(materialDto.getUnit());
        }
        if (materialDto.getUnitCost() != null) {
            material.setUnitCost(materialDto.getUnitCost());
        }
        if (materialDto.getStockQty() != null) {
            material.setStockQty(materialDto.getStockQty());
        }
        if (materialDto.getMinStockThreshold() != null) {
            material.setMinStockThreshold(materialDto.getMinStockThreshold());
        }
        
        return materialRepository.save(material);
    }
    
    public Material updateStock(Long materialId, BigDecimal newStock) {
        Material material = getMaterialById(materialId);
        material.setStockQty(newStock);
        return materialRepository.save(material);
    }
    
    public void deleteMaterial(Long materialId) {
        Material material = getMaterialById(materialId);
        materialRepository.delete(material);
    }
}
