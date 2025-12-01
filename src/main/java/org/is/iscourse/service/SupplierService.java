package org.is.iscourse.service;

import java.util.List;

import org.is.iscourse.model.dto.SupplierDto;
import org.is.iscourse.model.entity.Supplier;
import org.is.iscourse.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SupplierService {
    
    @Autowired
    private SupplierRepository supplierRepository;
    
    public Supplier createSupplier(SupplierDto supplierDto) {
        Supplier supplier = new Supplier(supplierDto.getName());
        supplier.setContactInfo(supplierDto.getContactInfo() != null ? supplierDto.getContactInfo() : "{}");
        supplier.setLeadTimeDays(supplierDto.getLeadTimeDays());
        
        return supplierRepository.save(supplier);
    }
    
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }
    
    public Supplier getSupplierById(Long supplierId) {
        return supplierRepository.findById(supplierId)
            .orElseThrow(() -> new RuntimeException("Supplier not found"));
    }
}