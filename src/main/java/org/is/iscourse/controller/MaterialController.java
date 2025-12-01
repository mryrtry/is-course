package org.is.iscourse.controller;

import java.math.BigDecimal;
import java.util.List;

import org.is.iscourse.model.dto.MaterialDto;
import org.is.iscourse.model.entity.Material;
import org.is.iscourse.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/materials")
public class MaterialController {
    
    @Autowired
    private MaterialService materialService;
    
    @PostMapping
    public ResponseEntity<Material> createMaterial(@RequestBody MaterialDto materialDto) {
        Material material = materialService.createMaterial(materialDto);
        return ResponseEntity.ok(material);
    }
    
    @GetMapping
    public ResponseEntity<List<Material>> getAllMaterials() {
        List<Material> materials = materialService.getAllMaterials();
        return ResponseEntity.ok(materials);
    }
    
    @GetMapping("/{materialId}")
    public ResponseEntity<Material> getMaterialById(@PathVariable Long materialId) {
        Material material = materialService.getMaterialById(materialId);
        return ResponseEntity.ok(material);
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Material>> getMaterialsByCategory(@PathVariable String category) {
        List<Material> materials = materialService.getMaterialsByCategory(category);
        return ResponseEntity.ok(materials);
    }
    
    @GetMapping("/low-stock")
    public ResponseEntity<List<Material>> getLowStockMaterials() {
        List<Material> materials = materialService.getLowStockMaterials();
        return ResponseEntity.ok(materials);
    }
    
    @PutMapping("/{materialId}")
    public ResponseEntity<Material> updateMaterial(@PathVariable Long materialId, @RequestBody MaterialDto materialDto) {
        Material material = materialService.updateMaterial(materialId, materialDto);
        return ResponseEntity.ok(material);
    }
    
    @PutMapping("/{materialId}/stock")
    public ResponseEntity<Material> updateStock(@PathVariable Long materialId, @RequestParam BigDecimal stockQty) {
        Material material = materialService.updateStock(materialId, stockQty);
        return ResponseEntity.ok(material);
    }
    
    @DeleteMapping("/{materialId}")
    public ResponseEntity<String> deleteMaterial(@PathVariable Long materialId) {
        materialService.deleteMaterial(materialId);
        return ResponseEntity.ok("Material deleted successfully");
    }
}