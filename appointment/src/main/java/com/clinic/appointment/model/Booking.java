package com.clinic.appointment.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_name", nullable = false)
    private String userName;

    // This unique constraint enforces your conflict prevention strategy at the DB level
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "slot_id", referencedColumnName = "id", unique = true, nullable = false)
    private Slot slot;

    // Default Constructor (Required by JPA)
    public Booking() {}

    // Parameterized Constructor
    public Booking(String userName, Slot slot) {
        this.userName = userName;
        this.slot = slot;
    }

    // --- Getters and Setters ---

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public Slot getSlot() { return slot; }
    public void setSlot(Slot slot) { this.slot = slot; }
}