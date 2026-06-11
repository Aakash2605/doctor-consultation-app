package com.clinic.appointment.controller;

import java.util.List;
import java.util.UUID;

import com.clinic.appointment.model.Doctor;
import com.clinic.appointment.model.Slot;
import com.clinic.appointment.service.DoctorService;
import com.clinic.appointment.repository.DoctorRepository;
import com.clinic.appointment.repository.SlotRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin(origins = "http://localhost:4200") 
public class DoctorController {

    private final DoctorService doctorService;
    private final DoctorRepository doctorRepo; // Now the controller has database access!
    private final SlotRepository slotRepo;     // Now the controller can clear slots!

    // Injecting the repositories directly into the constructor
    public DoctorController(DoctorService doctorService, DoctorRepository doctorRepo, SlotRepository slotRepo) {
        this.doctorService = doctorService;
        this.doctorRepo = doctorRepo;
        this.slotRepo = slotRepo;
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

    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable UUID id, @RequestBody Doctor doctorDetails) {
        return doctorRepo.findById(id)
            .map(existingDoctor -> {
                existingDoctor.setName(doctorDetails.getName());
                existingDoctor.setSpecialization(doctorDetails.getSpecialization());
                existingDoctor.setExperience(doctorDetails.getExperience());
                existingDoctor.setFee(doctorDetails.getFee());
                Doctor updatedDoctor = doctorRepo.save(existingDoctor);
                return ResponseEntity.ok(updatedDoctor);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable UUID id) {
        return doctorRepo.findById(id)
            .map(doctor -> {
                // Clears dependent slots to avoid SQLite foreign key constraint errors
                if (doctor.getSlots() != null && !doctor.getSlots().isEmpty()) {
                    slotRepo.deleteAll(doctor.getSlots());
                }
                
                doctorRepo.delete(doctor);
                return ResponseEntity.noContent().<Void>build();
            })
            .orElse(ResponseEntity.notFound().build());
    }
}