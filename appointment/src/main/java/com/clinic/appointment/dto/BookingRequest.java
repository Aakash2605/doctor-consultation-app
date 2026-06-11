package com.clinic.appointment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class BookingRequest {

    @NotBlank(message = "User name is required")
    private String userName;

    @NotNull(message = "Slot ID is required")
    private UUID slotId;

    // --- Getters and Setters ---
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public UUID getSlotId() { return slotId; }
    public void setSlotId(UUID slotId) { this.slotId = slotId; }
}