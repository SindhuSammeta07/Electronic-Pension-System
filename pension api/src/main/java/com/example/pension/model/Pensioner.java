package com.example.pension.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Pensioner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullname;
    private String email;
    private String phone;
    private String dob;
    private String pensionerId;
    private String username;
    private String password;
    private String maritalStatus; // Single, Married, Divorced, Widowed
    private String spouseName;    // Optional (if maritalStatus = Married)
    private String parentName;    // Optional (if maritalStatus != Married)
    private String address;
    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPensionerId() {
        return pensionerId;
    }

    public void setPensionerId(String pensionerId) {
        this.pensionerId = pensionerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getSpouseName() {
        return spouseName;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Pensioner(String fullname, String email, String phone, String dob, String pensionerId, String username, String password, String maritalStatus, String spouseName, String parentName, String address) {
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
        this.pensionerId = pensionerId;
        this.username = username;
        this.password = password;
        this.maritalStatus = maritalStatus;
        this.spouseName = spouseName;
        this.parentName = parentName;
        this.address = address;
    }

    public Pensioner(){}
}
