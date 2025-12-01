package org.is.iscourse.controller;

import java.util.List;

import org.is.iscourse.model.dto.AdmissionDto;
import org.is.iscourse.model.entity.Admission;
import org.is.iscourse.service.AdmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admissions")
public class AdmissionController {
    
    @Autowired
    private AdmissionService admissionService;
    
    @PostMapping("/grant")
    public ResponseEntity<Admission> grantAdmission(@RequestBody AdmissionDto admissionDto) {
        Admission admission = admissionService.grantAdmission(admissionDto);
        return ResponseEntity.ok(admission);
    }
    
    @PutMapping("/{admissionId}/revoke")
    public ResponseEntity<String> revokeAdmission(@PathVariable Long admissionId) {
        admissionService.revokeAdmission(admissionId);
        return ResponseEntity.ok("Admission revoked successfully");
    }
    
    @GetMapping("/check")
    public ResponseEntity<Boolean> checkAdmission(
            @RequestParam Long userId, 
            @RequestParam Long resourceId) {
        boolean hasAdmission = admissionService.checkUserAdmission(userId, resourceId);
        return ResponseEntity.ok(hasAdmission);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Admission>> getUserAdmissions(@PathVariable Long userId) {
        List<Admission> admissions = admissionService.getUserAdmissions(userId);
        return ResponseEntity.ok(admissions);
    }
    
    @GetMapping("/resource/{resourceId}")
    public ResponseEntity<List<Admission>> getResourceAdmissions(@PathVariable Long resourceId) {
        List<Admission> admissions = admissionService.getResourceAdmissions(resourceId);
        return ResponseEntity.ok(admissions);
    }
}
