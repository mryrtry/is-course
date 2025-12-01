package org.is.iscourse.service;

import org.is.iscourse.model.entity.Induction;
import org.is.iscourse.model.entity.User;
import org.is.iscourse.model.dto.InductionDto;
import org.is.iscourse.repository.InductionRepository;
import org.is.iscourse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class InductionService {
    
    @Autowired
    private InductionRepository inductionRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public Induction createInduction(InductionDto inductionDto) {
        User createdBy = userRepository.findById(inductionDto.getCreatedBy())
            .orElseThrow(() -> new RuntimeException("User not found with id: " + inductionDto.getCreatedBy()));
        
        Induction induction = new Induction();
        induction.setTitle(inductionDto.getTitle());
        induction.setDescription(inductionDto.getDescription());
        induction.setVersion(inductionDto.getVersion());
        induction.setTheoryContentLink(inductionDto.getTheoryContentLink());
        induction.setCreatedBy(createdBy);
        induction.setDurationHours(inductionDto.getDurationHours());
        
        return inductionRepository.save(induction);
    }
    
    public List<Induction> getAllInductions() {
        return inductionRepository.findAll();
    }
    
    public Induction getInductionById(Long id) {
        return inductionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Induction not found with id: " + id));
    }
}