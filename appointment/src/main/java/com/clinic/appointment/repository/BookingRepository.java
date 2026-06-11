package com.clinic.appointment.repository;

import com.clinic.appointment.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
    
    // Magic Query: Finds all bookings made by a specific user name
    List<Booking> findByUserName(String userName);
}