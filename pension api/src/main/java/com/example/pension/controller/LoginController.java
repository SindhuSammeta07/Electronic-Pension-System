package com.example.pension.controller;

import com.example.pension.model.Admin;
import com.example.pension.model.Pensioner;
import com.example.pension.model.SupportTicket;
import com.example.pension.repository.AdminRepository;
import com.example.pension.repository.PensionerRepository;
import com.example.pension.repository.SupportTicketRepository;
import com.example.pension.service.OtpService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:8000")
@RequestMapping("/api")
public class LoginController {

    private final AdminRepository adminRepository;
    private final PensionerRepository pensionerRepository;
    private final SupportTicketRepository supportTicketRepository;
    private final OtpService otpService;

    public LoginController(AdminRepository adminRepository, PensionerRepository pensionerRepository, SupportTicketRepository supportTicketRepository, OtpService otpService) {
        this.adminRepository = adminRepository;
        this.pensionerRepository = pensionerRepository;
        this.supportTicketRepository = supportTicketRepository;
        this.otpService = otpService;
    }

    // OTP generation and verification for Admin login
    @PostMapping("/admin/login/generate-otp")
    public ResponseEntity<?> generateAdminLoginOtp(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        otpService.sendOtp(email);
        return ResponseEntity.ok(Map.of("success", true, "message", "OTP sent to " + email));
    }

    @PostMapping("/admin/login/verify-otp")
    public ResponseEntity<?> verifyAdminLoginOtp(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String otp = payload.get("otp");

        // Verify OTP
        if (otpService.verifyOtp(email, otp)) {
            // Find the admin by email
            Optional<Admin> admin = adminRepository.findByEmail(email);
            if (admin.isPresent()) {
                return ResponseEntity.ok(Map.of("success", true, "message", "OTP verified successfully", "adminId", admin.get().getAdminId()));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("success", false, "message", "Admin not found"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", "Invalid OTP"));
    }

    // Set new password for Admin
    @PostMapping("/admin/login/set-new-password")
    public ResponseEntity<?> setAdminNewPassword(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String newPassword = payload.get("newPassword");

        // Find the admin by email
        Optional<Admin> admin = adminRepository.findByEmail(email);
        if (admin.isPresent()) {
            Admin existingAdmin = admin.get();
            existingAdmin.setPassword(newPassword);
            adminRepository.save(existingAdmin);
            return ResponseEntity.ok(Map.of("success", true, "message", "Password updated successfully"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("success", false, "message", "Admin not found"));
    }

    // OTP generation and verification for Pensioner login
    @PostMapping("/pensioner/login/generate-otp")
    public ResponseEntity<?> generatePensionerLoginOtp(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        otpService.sendOtp(email);
        return ResponseEntity.ok(Map.of("success", true, "message", "OTP sent to " + email));
    }

    @PostMapping("/pensioner/login/verify-otp")
    public ResponseEntity<?> verifyPensionerLoginOtp(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String otp = payload.get("otp");

        // Verify OTP
        if (otpService.verifyOtp(email, otp)) {
            // Find the pensioner by email
            Optional<Pensioner> pensioner = pensionerRepository.findByEmail(email);
            if (pensioner.isPresent()) {
                return ResponseEntity.ok(Map.of("success", true, "message", "OTP verified successfully", "pensionerId", pensioner.get().getPensionerId()));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("success", false, "message", "Pensioner not found"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", "Invalid OTP"));
    }

    // Set new password for Pensioner
    @PostMapping("/pensioner/login/set-new-password")
    public ResponseEntity<?> setPensionerNewPassword(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String newPassword = payload.get("newPassword");

        // Find the pensioner by email
        Optional<Pensioner> pensioner = pensionerRepository.findByEmail(email);
        if (pensioner.isPresent()) {
            Pensioner existingPensioner = pensioner.get();
            existingPensioner.setPassword(newPassword);
            pensionerRepository.save(existingPensioner);
            return ResponseEntity.ok(Map.of("success", true, "message", "Password updated successfully"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("success", false, "message", "Pensioner not found"));
    }
    // Admin login
    @PostMapping("/admin/login")
    public ResponseEntity<?> loginAdmin(@RequestBody Map<String, String> payload) {
        String password = payload.get("password");
        String email = payload.get("email");

        // Find admin by email and password
        Optional<Admin> existingAdmin = adminRepository.findByEmailAndPassword(email, password);
        if (existingAdmin.isPresent()) {
            return ResponseEntity.ok(Map.of("success", true, "message", "Admin logged in successfully", "adminId", existingAdmin.get().getAdminId(),"adminName",existingAdmin.get().getFullname()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", "Invalid credentials"));
    }

    // Pensioner login
    @PostMapping("/pensioner/login")
    public ResponseEntity<?> loginPensioner(@RequestBody Map<String, String> payload) {
        String password = payload.get("password");
        String email = payload.get("email");

        // Find pensioner by email and password
        Optional<Pensioner> existingPensioner = pensionerRepository.findByEmailAndPassword(email, password);
        if (existingPensioner.isPresent()) {
            return ResponseEntity.ok(Map.of("success", true, "message", "Pensioner logged in successfully", "pensionerId", existingPensioner.get().getPensionerId(), "pensionerName",existingPensioner.get().getFullname()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", "Invalid credentials"));
    }

    @GetMapping("/pensioner/details/{id}")
    public ResponseEntity<?> pensionerDetails(@PathVariable String id) {

        // Find pensioner by email and password
        Optional<Pensioner> existingPensioner = pensionerRepository.findByPensionerId(id);
        if (existingPensioner.isPresent()) {
            return ResponseEntity.ok(Map.of("success", true, "message", "Pensioner logged in successfully", "pensioner", existingPensioner.get()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", "Invalid credentials"));
    }
    @PutMapping("/pensioner/update/{id}")
    public ResponseEntity<?> updatePensionerProfile(@PathVariable String id, @RequestBody Pensioner updatedDetails) {
        Optional<Pensioner> existingPensioner = pensionerRepository.findByPensionerId(id);

        if (existingPensioner.isPresent()) {
            Pensioner pensioner = existingPensioner.get();
            pensioner.setFullname(updatedDetails.getFullname());
            pensioner.setEmail(updatedDetails.getEmail());
            pensioner.setPhone(updatedDetails.getPhone());
            pensioner.setMaritalStatus(updatedDetails.getMaritalStatus());
            pensioner.setSpouseName(updatedDetails.getSpouseName());
            pensioner.setParentName(updatedDetails.getParentName());
            pensioner.setAddress(updatedDetails.getAddress());
            pensioner.setPassword(updatedDetails.getPassword());
            pensionerRepository.save(pensioner);

            return ResponseEntity.ok("Profile updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pensioner not found.");
        }
    }

    @PostMapping("/support/create-ticket")
    public ResponseEntity<?> createTicket(@RequestBody SupportTicket ticket) {
        try {
            ticket.setCreatedAt(LocalDateTime.now());
            SupportTicket savedTicket = supportTicketRepository.save(ticket);
            return ResponseEntity.ok(savedTicket);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating ticket: " + e.getMessage());
        }
    }
}
