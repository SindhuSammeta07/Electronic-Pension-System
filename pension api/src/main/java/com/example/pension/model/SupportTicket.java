package com.example.pension.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class SupportTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private String phoneNumber;
    private String ticketCategory;
    private String description;
    private LocalDateTime createdAt;
    private String pensionerid;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTicketCategory() {
        return ticketCategory;
    }

    public void setTicketCategory(String ticketCategory) {
        this.ticketCategory = ticketCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getPensionerid() {
        return pensionerid;
    }

    public void setPensionerid(String pensionerid) {
        this.pensionerid = pensionerid;
    }

    public SupportTicket(String fullName, String email, String phoneNumber, String ticketCategory, String description, LocalDateTime createdAt, String pensionerid) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.ticketCategory = ticketCategory;
        this.description = description;
        this.createdAt = createdAt;
        this.pensionerid = pensionerid;
    }

    public SupportTicket(){}
}
