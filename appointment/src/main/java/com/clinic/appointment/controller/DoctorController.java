package com.clinic.appointment.controller;

import com.clinic.appointment.model.Doctor;
import com.clinic.appointment.model.Slot;
import com.clinic.appointment.service.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin(origins = "http://localhost:4200") 
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

// Endpoint: POST http://localhost:8080/api/doctors/{doctorId}/slots
    @PostMapping("/{doctorId}/slots")
    public ResponseEntity<Slot> addSlot(@PathVariable UUID doctorId, @RequestBody Slot slot) {
        return ResponseEntity.ok(doctorService.addSlot(doctorId, slot));
    }
    
    @PostMapping
    public ResponseEntity<Doctor> addDoctor(@RequestBody Doctor doctor) {
        return ResponseEntity.ok(doctorService.addDoctor(doctor));
    }

    @GetMapping("/{doctorId}/slots")
    public ResponseEntity<List<Slot>> getAvailableSlots(@PathVariable UUID doctorId) {
        return ResponseEntity.ok(doctorService.getAvailableSlots(doctorId));
    }
}