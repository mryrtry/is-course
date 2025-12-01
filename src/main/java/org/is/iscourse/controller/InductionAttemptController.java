package org.is.iscourse.controller;

import java.math.BigDecimal;
import java.util.List;

import org.is.iscourse.model.dto.InductionAttemptDto;
import org.is.iscourse.model.entity.InductionAttempt;
import org.is.iscourse.service.InductionAttemptService;
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
@RequestMapping("/api/attempts")
public class InductionAttemptController {
    
    @Autowired
    private InductionAttemptService attemptService;
    
    @PostMapping("/start")
    public ResponseEntity<InductionAttempt> startAttempt(@RequestBody InductionAttemptDto attemptDto) {
        InductionAttempt attempt = attemptService.startAttempt(attemptDto);
        return ResponseEntity.ok(attempt);
    }
    
    @PutMapping("/{attemptId}/complete")
    public ResponseEntity<InductionAttempt> completeAttempt(
            @PathVariable Long attemptId,
            @RequestParam BigDecimal score,
            @RequestParam Long signedBy) {
        InductionAttempt attempt = attemptService.completeAttempt(attemptId, score, signedBy);
        return ResponseEntity.ok(attempt);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<InductionAttempt>> getUserAttempts(@PathVariable Long userId) {
        List<InductionAttempt> attempts = attemptService.getUserAttempts(userId);
        return ResponseEntity.ok(attempts);
    }
    
    @GetMapping("/induction/{inductionId}")
    public ResponseEntity<List<InductionAttempt>> getInductionAttempts(@PathVariable Long inductionId) {
        List<InductionAttempt> attempts = attemptService.getInductionAttempts(inductionId);
        return ResponseEntity.ok(attempts);
    }
}