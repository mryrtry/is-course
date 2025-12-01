package org.is.iscourse.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.is.iscourse.model.dto.PurchaseRequestDto;
import org.is.iscourse.model.dto.PurchaseRequestItemDto;
import org.is.iscourse.model.entity.Material;
import org.is.iscourse.model.entity.PurchaseRequest;
import org.is.iscourse.model.entity.PurchaseRequestItem;
import org.is.iscourse.model.entity.Supplier;
import org.is.iscourse.model.entity.User;
import org.is.iscourse.repository.MaterialRepository;
import org.is.iscourse.repository.PurchaseRequestItemRepository;
import org.is.iscourse.repository.PurchaseRequestRepository;
import org.is.iscourse.repository.SupplierRepository;
import org.is.iscourse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PurchaseRequestService {
    
    @Autowired
    private PurchaseRequestRepository purchaseRequestRepository;
    
    @Autowired
    private PurchaseRequestItemRepository purchaseRequestItemRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private SupplierRepository supplierRepository;
    
    @Autowired
    private MaterialRepository materialRepository;
    
    public PurchaseRequest createPurchaseRequest(PurchaseRequestDto prDto) {
        User createdBy = userRepository.findById(prDto.getCreatedBy())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        Supplier supplier = supplierRepository.findById(prDto.getSupplierId())
            .orElseThrow(() -> new RuntimeException("Supplier not found"));
        
        PurchaseRequest purchaseRequest = new PurchaseRequest(createdBy, supplier);
        purchaseRequest.setExpectedDelivery(prDto.getExpectedDelivery());
        purchaseRequest.setNotes(prDto.getNotes());
        
        // Добавление товаров
        if (prDto.getItems() != null) {
            for (PurchaseRequestItemDto itemDto : prDto.getItems()) {
                Material material = materialRepository.findById(itemDto.getMaterialId())
                    .orElseThrow(() -> new RuntimeException("Material not found"));
                
                purchaseRequest.addItem(material, itemDto.getRequestedQty(), itemDto.getPrice());
            }
        }
        
        return purchaseRequestRepository.save(purchaseRequest);
    }
    
    public PurchaseRequest approvePurchaseRequest(Long prId) {
        PurchaseRequest pr = purchaseRequestRepository.findById(prId)
            .orElseThrow(() -> new RuntimeException("Purchase request not found"));
        
        pr.setStatus("approved");
        
        // Расчет даты доставки на основе lead time поставщика
        if (pr.getSupplier().getLeadTimeDays() != null) {
            pr.setExpectedDelivery(LocalDateTime.now().plusDays(pr.getSupplier().getLeadTimeDays()));
        }
        
        return purchaseRequestRepository.save(pr);
    }
    
    public PurchaseRequest rejectPurchaseRequest(Long prId, String reason) {
        PurchaseRequest pr = purchaseRequestRepository.findById(prId)
            .orElseThrow(() -> new RuntimeException("Purchase request not found"));
        
        pr.setStatus("rejected");
        pr.setNotes(pr.getNotes() != null ? pr.getNotes() + "\nRejected: " + reason : "Rejected: " + reason);
        
        return purchaseRequestRepository.save(pr);
    }
    
    public PurchaseRequest completePurchaseRequest(Long prId) {
        PurchaseRequest pr = purchaseRequestRepository.findById(prId)
            .orElseThrow(() -> new RuntimeException("Purchase request not found"));
        
        if (!"approved".equals(pr.getStatus())) {
            throw new RuntimeException("Only approved purchase requests can be completed");
        }
        
        // Пополнение запасов материалов
        List<PurchaseRequestItem> items = purchaseRequestItemRepository.findByPrId(prId);
        for (PurchaseRequestItem item : items) {
            Material material = item.getMaterial();
            BigDecimal receivedQty = item.getApprovedQty() != null ? item.getApprovedQty() : item.getRequestedQty();
            material.setStockQty(material.getStockQty().add(receivedQty));
            materialRepository.save(material);
        }
        
        pr.setStatus("completed");
        return purchaseRequestRepository.save(pr);
    }
    
    public void approvePurchaseRequestItem(Long prId, Long materialId, BigDecimal approvedQty) {
        purchaseRequestItemRepository.updateApprovedQty(prId, materialId, approvedQty);
    }
    
    public List<PurchaseRequest> getAllPurchaseRequests() {
        return purchaseRequestRepository.findAll();
    }
    
    public List<PurchaseRequest> getPurchaseRequestsByStatus(String status) {
        return purchaseRequestRepository.findByStatus(status);
    }
    
    public List<PurchaseRequestItem> getPurchaseRequestItems(Long prId) {
        return purchaseRequestItemRepository.findByPrId(prId);
    }
}
