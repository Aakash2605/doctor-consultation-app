package com.clinic.appointment.repository;

import com.clinic.appointment.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, UUID> {
    // JpaRepository gives us save(), findAll(), findById(), and delete() automatically!
}