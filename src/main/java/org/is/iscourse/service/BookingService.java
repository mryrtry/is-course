package org.is.iscourse.service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.is.iscourse.model.dto.BookingDto;
import org.is.iscourse.model.dto.BookingMaterialDto;
import org.is.iscourse.model.entity.Booking;
import org.is.iscourse.model.entity.BookingMaterial;
import org.is.iscourse.model.entity.Material;
import org.is.iscourse.model.entity.Resource;
import org.is.iscourse.model.entity.User;
import org.is.iscourse.repository.BookingMaterialRepository;
import org.is.iscourse.repository.BookingRepository;
import org.is.iscourse.repository.MaterialRepository;
import org.is.iscourse.repository.ResourceRepository;
import org.is.iscourse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BookingService {
    
    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private BookingMaterialRepository bookingMaterialRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private MaterialRepository materialRepository;
    
    @Autowired
    private ResourceRepository resourceRepository;
    
    @Autowired
    private AdmissionService admissionService;
    
    public Booking createBooking(BookingDto bookingDto) {
        // Проверка допуска пользователя
        if (!admissionService.checkUserAdmission(bookingDto.getUserId(), bookingDto.getResourceId())) {
            throw new RuntimeException("User does not have admission for this resource");
        }
        
        // Проверка конфликтующих бронирований
        List<Booking> conflicts = bookingRepository.findConflictingBookings(
            bookingDto.getResourceId(), bookingDto.getStartTime(), bookingDto.getEndTime());
        
        if (!conflicts.isEmpty()) {
            throw new RuntimeException("Resource is already booked for the selected time period");
        }
        
        User user = userRepository.findById(bookingDto.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        Booking booking = new Booking(user, bookingDto.getResourceId(), bookingDto.getPurpose(), 
                                   bookingDto.getStartTime(), bookingDto.getEndTime());
        
        // Добавление материалов
        if (bookingDto.getMaterials() != null) {
            for (BookingMaterialDto materialDto : bookingDto.getMaterials()) {
                Material material = materialRepository.findById(materialDto.getMaterialId())
                    .orElseThrow(() -> new RuntimeException("Material not found: " + materialDto.getMaterialId()));
                
                // Проверка доступности материала
                if (material.getStockQty().compareTo(materialDto.getPlannedQty()) < 0) {
                    throw new RuntimeException("Insufficient stock for material: " + material.getName());
                }
                
                booking.addMaterial(material, materialDto.getPlannedQty());
            }
        }
        
        // Расчет стоимости
        calculateBookingCost(booking);
        
        return bookingRepository.save(booking);
    }
    
    private void calculateBookingCost(Booking booking) {
        Resource resource = resourceRepository.findById(booking.getResourceId())
            .orElseThrow(() -> new RuntimeException("Resource not found"));
        
        // Расчет стоимости по времени использования
        long hours = Duration.between(booking.getStartTime(), booking.getEndTime()).toHours();
        BigDecimal timeCost = resource.getUsageCostPerHour().multiply(BigDecimal.valueOf(hours));
        
        // Расчет стоимости материалов
        BigDecimal materialCost = BigDecimal.ZERO;
        for (BookingMaterial bookingMaterial : booking.getBookingMaterials()) {
            materialCost = materialCost.add(
                bookingMaterial.getMaterial().getUnitCost().multiply(bookingMaterial.getPlannedQty())
            );
        }
        
    }
    
    public void issueMaterials(Long bookingId, Long materialId, BigDecimal actualQty, Long issuedBy) {
        bookingMaterialRepository.issueMaterials(bookingId, materialId, actualQty, issuedBy);
        
        // Обновление остатка материала
        BookingMaterial bookingMaterial = bookingMaterialRepository.findByBookingAndMaterial(bookingId, materialId)
            .orElseThrow(() -> new RuntimeException("Booking material not found"));
        
        Material material = bookingMaterial.getMaterial();
        material.setStockQty(material.getStockQty().subtract(actualQty));
        materialRepository.save(material);
    }
    
    public List<Booking> getUserBookings(Long userId) {
        return bookingRepository.findByUserId(userId);
    }
    
    public List<Booking> getResourceBookings(Long resourceId) {
        return bookingRepository.findByResourceId(resourceId);
    }
    
    public List<Booking> getResourceAvailability(Long resourceId, LocalDateTime startDate, LocalDateTime endDate) {
        return bookingRepository.findConflictingBookings(resourceId, startDate, endDate);
    }
    
    public Booking getBookingById(Long bookingId) {
        return bookingRepository.findById(bookingId)
            .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));
    }
    
    public Booking confirmBooking(Long bookingId) {
        Booking booking = getBookingById(bookingId);
        booking.setStatus("confirmed");
        return bookingRepository.save(booking);
    }
    
    public Booking cancelBooking(Long bookingId) {
        Booking booking = getBookingById(bookingId);
        booking.setStatus("cancelled");
        return bookingRepository.save(booking);
    }
    
    public List<BookingMaterial> getBookingMaterials(Long bookingId) {
        return bookingMaterialRepository.findByBookingId(bookingId);
    }
    
    public void deleteBooking(Long bookingId) {
        Booking booking = getBookingById(bookingId);
        bookingRepository.delete(booking);
    }
    
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
}
