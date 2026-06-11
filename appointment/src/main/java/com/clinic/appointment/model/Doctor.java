package com.clinic.appointment.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String specialization;
    private int experience;
    private double fee;

    // A doctor can have many available time slots
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Slot> slots = new ArrayList<>();

    // Default Constructor (Required by JPA)
    public Doctor() {}

    // Parameterized Constructor
    public Doctor(String name, String specialization, int experience, double fee) {
        this.name = name;
        this.specialization = specialization;
        this.experience = experience;
        this.fee = fee;
    }

    // --- Getters and Setters ---
    
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public int getExperience() { return experience; }
    public void setExperience(int experience) { this.experience = experience; }

    public double getFee() { return fee; }
    public void setFee(double fee) { this.fee = fee; }

    public List<Slot> getSlots() { return slots; }
    public void setSlots(List<Slot> slots) { this.slots = slots; }
}