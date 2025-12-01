package org.is.iscourse.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.is.iscourse.model.dto.AdmissionDto;
import org.is.iscourse.model.dto.InductionAttemptDto;
import org.is.iscourse.model.entity.Admission;
import org.is.iscourse.model.entity.Induction;
import org.is.iscourse.model.entity.InductionAttempt;
import org.is.iscourse.model.entity.User;
import org.is.iscourse.repository.AdmissionRepository;
import org.is.iscourse.repository.InductionAttemptRepository;
import org.is.iscourse.repository.InductionRepository;
import org.is.iscourse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class AdmissionService {
    
    @Autowired
    private AdmissionRepository admissionRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public Admission grantAdmission(AdmissionDto admissionDto) {
        if (admissionRepository.findByUserAndResource(admissionDto.getUserId(), admissionDto.getResourceId()).isPresent()) {
            throw new RuntimeException("Admission already exists for this user and resource");
        }
        
        User user = userRepository.findById(admissionDto.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        Admission admission = new Admission(user, admissionDto.getResourceId(), admissionDto.getLevel());
        
        if (admissionDto.getGrantedBy() != null) {
            User grantedBy = userRepository.findById(admissionDto.getGrantedBy())
                .orElseThrow(() -> new RuntimeException("Granted by user not found"));
            admission.setGrantedBy(grantedBy);
        }
    
        admission.setExpiresAt(admissionDto.getExpiresAt());
        admission.setStatus(admissionDto.getStatus());
        admission.setNotes(admissionDto.getNotes());
        admission.setStatus(admissionDto.getStatus());
        
        return admissionRepository.save(admission);
    }
    
    public void revokeAdmission(Long admissionId) {
        Admission admission = admissionRepository.findById(admissionId)
            .orElseThrow(() -> new RuntimeException("Admission not found"));
        admission.setStatus("revoked");
        admissionRepository.save(admission);
    }
    
    public boolean checkUserAdmission(Long userId, Long resourceId) {
        return admissionRepository.hasActiveAdmission(userId, resourceId);
    }
    
    public List<Admission> getUserAdmissions(Long userId) {
        return admissionRepository.findByUserId(userId);
    }
    
    public List<Admission> getResourceAdmissions(Long resourceId) {
        return admissionRepository.findByResourceId(resourceId);
    }
}
