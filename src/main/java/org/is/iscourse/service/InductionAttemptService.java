package org.is.iscourse.service;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.is.iscourse.model.dto.InductionAttemptDto;
import org.is.iscourse.model.entity.Induction;
import org.is.iscourse.model.entity.InductionAttempt;
import org.is.iscourse.model.entity.User;
import org.is.iscourse.repository.InductionAttemptRepository;
import org.is.iscourse.repository.InductionRepository;
import org.is.iscourse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class InductionAttemptService {
    
    @Autowired
    private InductionAttemptRepository attemptRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private InductionRepository inductionRepository;
    
    public InductionAttempt startAttempt(InductionAttemptDto attemptDto) {
        User user = userRepository.findById(attemptDto.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        Induction induction = inductionRepository.findById(attemptDto.getInductionId())
            .orElseThrow(() -> new RuntimeException("Induction not found"));
        
        InductionAttempt attempt = new InductionAttempt(user, induction);
        attempt.setStatus(attemptDto.getStatus());
        attempt.setExpiresAt(attemptDto.getExpiresAt());
        
        return attemptRepository.save(attempt);
    }
    
    public InductionAttempt completeAttempt(Long attemptId, BigDecimal score, Long signedByUserId) {
        InductionAttempt attempt = attemptRepository.findById(attemptId)
            .orElseThrow(() -> new RuntimeException("Attempt not found"));
        
        User signedBy = userRepository.findById(signedByUserId)
            .orElseThrow(() -> new RuntimeException("Signing user not found"));
        
        attempt.setStatus("completed");
        attempt.setScore(score);
        attempt.setPracticalSignedBy(signedBy);
        attempt.setPassedAt(LocalDateTime.now());
        
        return attemptRepository.save(attempt);
    }
    
    public List<InductionAttempt> getUserAttempts(Long userId) {
        return attemptRepository.findByUserId(userId);
    }
    
    public List<InductionAttempt> getInductionAttempts(Long inductionId) {
        return attemptRepository.findByInductionId(inductionId);
    }
}