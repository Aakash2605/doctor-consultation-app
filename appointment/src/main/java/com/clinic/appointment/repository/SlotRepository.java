package com.clinic.appointment.repository;

import com.clinic.appointment.model.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface SlotRepository extends JpaRepository<Slot, UUID> {
    
    // Magic Query: Finds all available slots for a specific doctor
    List<Slot> findByDoctorIdAndIsBookedFalse(UUID doctorId);
}