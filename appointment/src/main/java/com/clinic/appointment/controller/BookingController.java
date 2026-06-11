package com.clinic.appointment.controller;

import com.clinic.appointment.dto.BookingRequest;
import com.clinic.appointment.model.Booking;
import com.clinic.appointment.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:4200")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<Booking> bookAppointment(@Valid @RequestBody BookingRequest request) {
        Booking booking = bookingService.bookAppointment(request.getUserName(), request.getSlotId());
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/user/{userName}")
    public ResponseEntity<List<Booking>> getUserAppointments(@PathVariable String userName) {
        return ResponseEntity.ok(bookingService.getUserAppointments(userName));
    }
}