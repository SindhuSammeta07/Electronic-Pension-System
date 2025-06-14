package com.example.pension.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pension_applications")
public class PensionApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pensionerId; // Unique identifier
    private String applicantType;
    private String fullName;
    private String dob;
    private String gender;
    private String maritalStatus;
    private String spouseName;
    private String parentName;
    private String contact;
    private String email;
    private String address;
    private String designation;
    private String aadhar;
    private String pensionCategory;
    private String status;
    private String appliedDate;
    private String approvedDate;
    private int pensionAmount;
    private String appId; // Unique identifier

    @Lob
    private byte[] photo; // Store photo as a binary BLOB

    @Lob
    private byte[] rationCard; // Store ration card as a binary BLOB

    @Lob
    private byte[] casteCertificate; // Store caste certificate as a binary BLOB
    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPensionerId() {
        return pensionerId;
    }

    public void setPensionerId(String pensionerId) {
        this.pensionerId = pensionerId;
    }

    public String getApplicantType() {
        return applicantType;
    }

    public void setApplicantType(String applicantType) {
        this.applicantType = applicantType;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getPensionCategory() {
        return pensionCategory;
    }

    public void setPensionCategory(String pensionCategory) {
        this.pensionCategory = pensionCategory;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public byte[] getRationCard() {
        return rationCard;
    }

    public void setRationCard(byte[] rationCard) {
        this.rationCard = rationCard;
    }

    public byte[] getCasteCertificate() {
        return casteCertificate;
    }

    public void setCasteCertificate(byte[] casteCertificate) {
        this.casteCertificate = casteCertificate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(String appliedDate) {
        this.appliedDate = appliedDate;
    }

    public String getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(String approvedDate) {
        this.approvedDate = approvedDate;
    }

    public int getPensionAmount() {
        return pensionAmount;
    }

    public void setPensionAmount(int pensionAmount) {
        this.pensionAmount = pensionAmount;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public PensionApplication(String pensionerId, String applicantType, String fullName, String dob, String gender, String maritalStatus, String spouseName, String parentName, String contact, String email, String address, String designation, String aadhar, String pensionCategory, String status, String appliedDate, String approvedDate, int pensionAmount, String appId, byte[] photo, byte[] rationCard, byte[] casteCertificate) {
        this.pensionerId = pensionerId;
        this.applicantType = applicantType;
        this.fullName = fullName;
        this.dob = dob;
        this.gender = gender;
        this.maritalStatus = maritalStatus;
        this.spouseName = spouseName;
        this.parentName = parentName;
        this.contact = contact;
        this.email = email;
        this.address = address;
        this.designation = designation;
        this.aadhar = aadhar;
        this.pensionCategory = pensionCategory;
        this.status = status;
        this.appliedDate = appliedDate;
        this.approvedDate = approvedDate;
        this.pensionAmount = pensionAmount;
        this.appId = appId;
        this.photo = photo;
        this.rationCard = rationCard;
        this.casteCertificate = casteCertificate;
    }

    public PensionApplication(){}
}
