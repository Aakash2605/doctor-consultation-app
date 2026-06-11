
package com.clinic.appointment.service;

import com.clinic.appointment.model.Doctor;
import com.clinic.appointment.model.Slot;
import com.clinic.appointment.repository.DoctorRepository;
import com.clinic.appointment.repository.SlotRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final SlotRepository slotRepository;

    // Spring Boot automatically injects the repositories here
    public DoctorService(DoctorRepository doctorRepository, SlotRepository slotRepository) {
        this.doctorRepository = doctorRepository;
        this.slotRepository = slotRepository;
    }

    // Fetch all doctors to display on the frontend
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }



    // Add a new time slot for a specific doctor
    public Slot addSlot(UUID doctorId, Slot slot) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found."));
        slot.setDoctor(doctor);
        slot.setBooked(false);
        return slotRepository.save(slot);
    }
    // Save a brand new doctor to the database
    public Doctor addDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    // Fetch only the UNBOOKED slots for a specific doctor
    public List<Slot> getAvailableSlots(UUID doctorId) {
        return slotRepository.findByDoctorIdAndIsBookedFalse(doctorId);
    }
}