package org.is.iscourse.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.is.iscourse.model.dto.BookingDto;
import org.is.iscourse.model.entity.Booking;
import org.is.iscourse.model.entity.BookingMaterial;
import org.is.iscourse.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    
    @Autowired
    private BookingService bookingService;
    
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody BookingDto bookingDto) {
        Booking booking = bookingService.createBooking(bookingDto);
        return ResponseEntity.ok(booking);
    }
    
    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }
    
    @GetMapping("/{bookingId}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long bookingId) {
        Booking booking = bookingService.getBookingById(bookingId);
        return ResponseEntity.ok(booking);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getUserBookings(@PathVariable Long userId) {
        List<Booking> bookings = bookingService.getUserBookings(userId);
        return ResponseEntity.ok(bookings);
    }
    
    @GetMapping("/resource/{resourceId}")
    public ResponseEntity<List<Booking>> getResourceBookings(@PathVariable Long resourceId) {
        List<Booking> bookings = bookingService.getResourceBookings(resourceId);
        return ResponseEntity.ok(bookings);
    }
    
    @GetMapping("/availability")
    public ResponseEntity<List<Booking>> getResourceAvailability(
            @RequestParam Long resourceId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        List<Booking> bookings = bookingService.getResourceAvailability(resourceId, start, end);
        return ResponseEntity.ok(bookings);
    }
    
    @GetMapping("/{bookingId}/materials")
    public ResponseEntity<List<BookingMaterial>> getBookingMaterials(@PathVariable Long bookingId) {
        List<BookingMaterial> materials = bookingService.getBookingMaterials(bookingId);
        return ResponseEntity.ok(materials);
    }
    
    @PutMapping("/{bookingId}/confirm")
    public ResponseEntity<Booking> confirmBooking(@PathVariable Long bookingId) {
        Booking booking = bookingService.confirmBooking(bookingId);
        return ResponseEntity.ok(booking);
    }
    
    @PutMapping("/{bookingId}/cancel")
    public ResponseEntity<Booking> cancelBooking(@PathVariable Long bookingId) {
        Booking booking = bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok(booking);
    }
    
    @PutMapping("/{bookingId}/issue-materials")
    public ResponseEntity<String> issueMaterials(
            @PathVariable Long bookingId,
            @RequestParam Long materialId,
            @RequestParam BigDecimal actualQty,
            @RequestParam Long issuedBy) {
        bookingService.issueMaterials(bookingId, materialId, actualQty, issuedBy);
        return ResponseEntity.ok("Materials issued successfully");
    }
    
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long bookingId) {
        bookingService.deleteBooking(bookingId);
        return ResponseEntity.ok("Booking deleted successfully");
    }
}