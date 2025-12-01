package org.is.iscourse.controller;

import org.is.iscourse.model.entity.Induction;
import org.is.iscourse.model.dto.InductionDto;
import org.is.iscourse.service.InductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


// TODO - add verification of user by id. sessions.

@RestController
@RequestMapping("/api/inductions")
public class InductionController {
    
    @Autowired
    private InductionService inductionService;
    
    @PostMapping
    public ResponseEntity<Induction> createInduction(@RequestBody InductionDto inductionDto) {
        Induction induction = inductionService.createInduction(inductionDto);
        return ResponseEntity.ok(induction);
    }
    
    @GetMapping
    public ResponseEntity<List<Induction>> getAllInductions() {
        List<Induction> inductions = inductionService.getAllInductions();
        return ResponseEntity.ok(inductions);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Induction> getInductionById(@PathVariable Long id) {
        Induction induction = inductionService.getInductionById(id);
        return ResponseEntity.ok(induction);
    }
}