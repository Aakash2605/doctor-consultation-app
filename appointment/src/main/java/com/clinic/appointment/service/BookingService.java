package com.clinic.appointment.service;

import com.clinic.appointment.model.Booking;
import com.clinic.appointment.model.Slot;
import com.clinic.appointment.repository.BookingRepository;
import com.clinic.appointment.repository.SlotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final SlotRepository slotRepository;

    public BookingService(BookingRepository bookingRepository, SlotRepository slotRepository) {
        this.bookingRepository = bookingRepository;
        this.slotRepository = slotRepository;
    }

    // @Transactional ensures that if two people click book at the same time, 
    // the database lock we built earlier will safely roll back the loser's transaction.
    @Transactional
    public Booking bookAppointment(String userName, UUID slotId) {
        
        // 1. Find the requested time slot
        Slot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Error: Time slot not found."));

        // 2. Double-check if it was booked milliseconds before this request
        if (slot.isBooked()) {
            throw new RuntimeException("Sorry, this slot was just booked by someone else.");
        }

        // 3. Mark the slot as unavailable
        slot.setBooked(true);
        slotRepository.save(slot);

        // 4. Generate the final booking ticket and save it
        Booking newBooking = new Booking(userName, slot);
        return bookingRepository.save(newBooking);
    }

    // Fetch all appointments for a specific patient
    public List<Booking> getUserAppointments(String userName) {
        return bookingRepository.findByUserName(userName);
    }
}