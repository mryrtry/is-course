package org.is.iscourse.controller;

import java.math.BigDecimal;
import java.util.List;

import org.is.iscourse.model.dto.PurchaseRequestDto;
import org.is.iscourse.model.entity.PurchaseRequest;
import org.is.iscourse.model.entity.PurchaseRequestItem;
import org.is.iscourse.service.PurchaseRequestService;
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
@RequestMapping("/api/purchase-requests")
public class PurchaseRequestController {
    
    @Autowired
    private PurchaseRequestService purchaseRequestService;
    
    @PostMapping
    public ResponseEntity<PurchaseRequest> createPurchaseRequest(@RequestBody PurchaseRequestDto prDto) {
        PurchaseRequest pr = purchaseRequestService.createPurchaseRequest(prDto);
        return ResponseEntity.ok(pr);
    }
    
    @PutMapping("/{prId}/approve")
    public ResponseEntity<PurchaseRequest> approvePurchaseRequest(@PathVariable Long prId) {
        PurchaseRequest pr = purchaseRequestService.approvePurchaseRequest(prId);
        return ResponseEntity.ok(pr);
    }
    
    @PutMapping("/{prId}/reject")
    public ResponseEntity<PurchaseRequest> rejectPurchaseRequest(@PathVariable Long prId, @RequestParam String reason) {
        PurchaseRequest pr = purchaseRequestService.rejectPurchaseRequest(prId, reason);
        return ResponseEntity.ok(pr);
    }
    
    @PutMapping("/{prId}/complete")
    public ResponseEntity<PurchaseRequest> completePurchaseRequest(@PathVariable Long prId) {
        PurchaseRequest pr = purchaseRequestService.completePurchaseRequest(prId);
        return ResponseEntity.ok(pr);
    }
    
    @PutMapping("/{prId}/approve-item")
    public ResponseEntity<String> approvePurchaseRequestItem(
            @PathVariable Long prId,
            @RequestParam Long materialId,
            @RequestParam BigDecimal approvedQty) {
        purchaseRequestService.approvePurchaseRequestItem(prId, materialId, approvedQty);
        return ResponseEntity.ok("Item approved successfully");
    }
    
    @GetMapping
    public ResponseEntity<List<PurchaseRequest>> getAllPurchaseRequests() {
        List<PurchaseRequest> prs = purchaseRequestService.getAllPurchaseRequests();
        return ResponseEntity.ok(prs);
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<PurchaseRequest>> getPurchaseRequestsByStatus(@PathVariable String status) {
        List<PurchaseRequest> prs = purchaseRequestService.getPurchaseRequestsByStatus(status);
        return ResponseEntity.ok(prs);
    }
    
    @GetMapping("/{prId}/items")
    public ResponseEntity<List<PurchaseRequestItem>> getPurchaseRequestItems(@PathVariable Long prId) {
        List<PurchaseRequestItem> items = purchaseRequestService.getPurchaseRequestItems(prId);
        return ResponseEntity.ok(items);
    }
}