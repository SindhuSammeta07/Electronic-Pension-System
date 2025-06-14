package com.example.pension.controller;

import com.example.pension.model.Admin;
import com.example.pension.model.Payment;
import com.example.pension.model.PensionApplication;
import com.example.pension.model.Pensioner;
import com.example.pension.repository.AdminRepository;
import com.example.pension.repository.PaymentRepository;
import com.example.pension.repository.PensionApplicationRepository;
import com.example.pension.repository.PensionerRepository;
import com.example.pension.service.OtpService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:8000")
@RequestMapping("/api")
public class RegistrationController {

    private final AdminRepository adminRepository;
    private final PensionerRepository pensionerRepository;
    private final OtpService otpService;
    private final PensionApplicationRepository pensionApplicationRepository;
    private final PaymentRepository paymentRepository;


    public RegistrationController(AdminRepository adminRepository, PensionerRepository pensionerRepository, OtpService otpService, PensionApplicationRepository pensionApplicationRepository, PaymentRepository paymentRepository) {
        this.adminRepository = adminRepository;
        this.pensionerRepository = pensionerRepository;
        this.otpService = otpService;
        this.pensionApplicationRepository = pensionApplicationRepository;
        this.paymentRepository = paymentRepository;
    }

    @GetMapping("/get-all-pensioner")
    public List<Pensioner> getAllPensioner() {
        return pensionerRepository.findAll();
    }

    @GetMapping("/get-all-admin")
    public List<Admin> getAllAdmin() {
        return adminRepository.findAll();
    }

    @GetMapping("/get-all-application")
    public List<PensionApplication> getAllApplication() {
        return pensionApplicationRepository.findAll();
    }

    @GetMapping("/get-all-payments")
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // OTP generation and verification for Admin
    @PostMapping("/admin/generate-otp")
    public ResponseEntity<?> generateAdminOtp(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        otpService.sendOtp(email);
        return ResponseEntity.ok(Map.of("success", true, "message", "OTP sent to " + email));
    }

    @PostMapping("/admin/verify-otp")
    public ResponseEntity<?> verifyAdminOtp(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String otp = payload.get("otp");
        if (otpService.verifyOtp(email, otp)) {
            return ResponseEntity.ok(Map.of("success", true, "message", "OTP verified successfully"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", "Invalid OTP"));
    }

    @PostMapping("/admin/register")
    public ResponseEntity<?> registerAdmin(@RequestBody Admin admin) {
        adminRepository.save(admin);
        return ResponseEntity.ok(Map.of("success", true, "message", "Admin registered successfully"));
    }

    // OTP generation and verification for Pensioner
    @PostMapping("/pensioner/generate-otp")
    public ResponseEntity<?> generatePensionerOtp(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        otpService.sendOtp(email);
        return ResponseEntity.ok(Map.of("success", true, "message", "OTP sent to " + email));
    }

    @PostMapping("/pensioner/verify-otp")
    public ResponseEntity<?> verifyPensionerOtp(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String otp = payload.get("otp");
        if (otpService.verifyOtp(email, otp)) {
            return ResponseEntity.ok(Map.of("success", true, "message", "OTP verified successfully"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", "Invalid OTP"));
    }

    @PostMapping("/pensioner/register")
    public ResponseEntity<?> registerPensioner(@RequestBody Pensioner pensioner) {
        // Generate unique Pensioner ID
        long count = pensionerRepository.count();
        String generatedId = String.format("PEN%04d", count + 1);
        pensioner.setPensionerId(generatedId);

        pensionerRepository.save(pensioner);
        return ResponseEntity.ok(Map.of("success", true, "message", "Pensioner registered successfully", "pensionerId", generatedId));
    }

    @DeleteMapping("/delete-pensioner/{id}")
    public ResponseEntity<?> deletePensioner(@PathVariable Long id) {
        return pensionerRepository.findById(id)
                .map(pensioner -> {
                    pensionerRepository.delete(pensioner);
                    return ResponseEntity.ok().body("Pensioner deleted successfully.");
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pensioner not found."));
    }

    @PostMapping("/pensioner/submit-application")
    public ResponseEntity<?> submitApplication(
            @RequestParam("applicantType") String applicantType,
            @RequestParam("fullName") String fullName,
            @RequestParam(value = "photo", required = false) MultipartFile photo,
            @RequestParam("dob") String dob,
            @RequestParam("gender") String gender,
            @RequestParam("maritalStatus") String maritalStatus,
            @RequestParam(value = "spouseName", required = false) String spouseName,
            @RequestParam(value = "parentName", required = false) String parentName,
            @RequestParam("contact") String contact,
            @RequestParam("email") String email,
            @RequestParam("address") String address,
            @RequestParam("designation") String designation,
            @RequestParam("aadhar") String aadhar,
            @RequestParam(value = "rationCard", required = false) MultipartFile rationCard,
            @RequestParam(value = "casteCertificate", required = false) MultipartFile casteCertificate,
            @RequestParam("pensionCategory") String pensionCategory,
            @RequestParam("pensionerId") String pensionerId
    ) {
        try {
            // Check if the pensionerId exists in the database
            Optional<PensionApplication> existingApplicationOpt = pensionApplicationRepository.findByPensionerId(pensionerId);

            PensionApplication application;
            if (existingApplicationOpt.isPresent()) {
                // Update existing record
                application = existingApplicationOpt.get();
            } else {
                // Create a new record
                application = new PensionApplication();
                application.setPensionerId(pensionerId);
                long count = pensionApplicationRepository.count();
                String generatedId = String.format("APP%04d", count + 1);
                application.setAppId(generatedId);

            }
            application.setStatus("Pending");
            application.setAppliedDate(String.valueOf(LocalDate.now())); // Set today's date
            application.setApprovedDate(null); // Initially empty
            // Common fields (for both update and create)
            application.setApplicantType(applicantType);
            application.setFullName(fullName);
            application.setDob(dob);
            application.setGender(gender);
            application.setMaritalStatus(maritalStatus);
            application.setSpouseName(spouseName);
            application.setParentName(parentName);
            application.setContact(contact);
            application.setEmail(email);
            application.setAddress(address);
            application.setDesignation(designation);
            application.setAadhar(aadhar);
            application.setPensionCategory(pensionCategory);

            // Determine pension amount based on category
            int pensionAmount = determinePensionAmount(pensionCategory);
            application.setPensionAmount(pensionAmount);

            // Handle binary files
            if (photo != null && !photo.isEmpty()) {
                application.setPhoto(photo.getBytes());
            }
            if (rationCard != null && !rationCard.isEmpty()) {
                application.setRationCard(rationCard.getBytes());
            }
            if (casteCertificate != null && !casteCertificate.isEmpty()) {
                application.setCasteCertificate(casteCertificate.getBytes());
            }

            // Save the application (new or updated) to the database
            PensionApplication savedApplication = pensionApplicationRepository.save(application);

            // Return a success response
            return ResponseEntity.ok(savedApplication);
        } catch (Exception e) {
            // Handle errors and return an appropriate response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing the application: " + e.getMessage());
        }
    }

    @GetMapping("/pensioner/track-application/{pensionId}")
    public ResponseEntity<?> trackApplication(@PathVariable String pensionId) {
        try {
            Optional<PensionApplication> application = pensionApplicationRepository.findByPensionerId(pensionId);

            if (application.isPresent()) {
                // Map the application entity to a response DTO
                PensionApplication app = application.get();
                Map<String, Object> response = new HashMap<>();
                response.put("name", app.getFullName());
                response.put("applicationType", app.getApplicantType());
                response.put("status", app.getStatus());
                response.put("processingDate", app.getAppliedDate());
                response.put("ApprovedDate", app.getApprovedDate());
                response.put("approvedAmount", app.getPensionAmount() > 0 ? "₹" + app.getPensionAmount() : "N/A");

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pension ID not found. Please check and try again.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the application: " + e.getMessage());
        }
    }

    @GetMapping("/pensioner/recent-application/{pensionId}")
    public ResponseEntity<?> recentApplication(@PathVariable String pensionId) {
        try {
            Optional<PensionApplication> application = pensionApplicationRepository.findByPensionerId(pensionId);

            if (application.isPresent()) {
                // Map the application entity to a response DTO
                PensionApplication app = application.get();

                return ResponseEntity.ok(app);
            } else {
                return ResponseEntity.status(HttpStatus.CREATED).body("No records found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the application: " + e.getMessage());
        }
    }

//    @GetMapping("/generate/{pensionId}")
//    public ResponseEntity<?> generatePayments(@PathVariable String pensionId) {
//        try {
//            // Fetch pensioner application by pensionerId
//            PensionApplication pensioner = pensionApplicationRepository
//                    .findByPensionerId(pensionId)
//                    .orElseThrow(() -> new RuntimeException("Pensioner not found"));
//
//            LocalDate appliedDate = LocalDate.parse(pensioner.getAppliedDate()); // Assuming `getAppliedDate()` exists
//            LocalDate currentDate = LocalDate.now();
//            double amount = pensioner.getPensionAmount(); // Example fixed amount per payment
//            String status = "Pending";
//
//            List<Payment> newPayments = new ArrayList<>();
//
//            // Generate payments for every 28th day from appliedDate until today
//            LocalDate nextPaymentDate = appliedDate.plusDays(30);
//            while (!nextPaymentDate.isAfter(currentDate)) {
//                // Check if a payment already exists for this date
//                boolean paymentExists = !paymentRepository
//                        .findByPensionerIdAndPaymentDate(pensionId, nextPaymentDate)
//                        .isEmpty();
//
//                if (!paymentExists) {
//                    // Create a new payment record
//                    Payment payment = new Payment();
//                    payment.setPensionerId(pensionId);
//                    payment.setPaymentDate(nextPaymentDate);
//                    payment.setAmount(amount);
//                    payment.setStatus(status);
//                    newPayments.add(payment);
//                }
//
//                // Move to the next 28th day
//                nextPaymentDate = nextPaymentDate.plusDays(30);
//            }
//
//            paymentRepository.saveAll(newPayments);
//            // Save all new payments to the database
//            Object payments = paymentRepository.findByPensionerId(pensionId);
//            return ResponseEntity.ok(payments);
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Error generating payments: " + e.getMessage());
//        }
//    }

    @GetMapping("/generate/{pensionId}")
    public ResponseEntity<?> generatePayments(@PathVariable String pensionId) {
        try {
            // Fetch pensioner application by pensionerId
            PensionApplication pensioner = pensionApplicationRepository
                    .findByPensionerId(pensionId)
                    .orElseThrow(() -> new RuntimeException("Pensioner not found"));

            LocalDate appliedDate = LocalDate.parse(pensioner.getAppliedDate()); // Assuming `getAppliedDate()` exists
            LocalDate currentDate = LocalDate.now();
            double amount = pensioner.getPensionAmount(); // Example fixed amount per payment
            String status = "Approved";

            List<Payment> newPayments = new ArrayList<>();

            // Generate payments for every 28th day from appliedDate until today
            LocalDate nextPaymentDate = appliedDate.plusDays(30);
            while (!nextPaymentDate.isAfter(currentDate)) {
                // Check if a payment already exists for this date
                boolean paymentExists = paymentRepository
                        .findByPensionerIdAndPaymentDate(pensionId, nextPaymentDate)
                        .stream()
                        .findAny()
                        .isPresent();

                if (!paymentExists) {
                    // Create a new payment record
                    Payment payment = new Payment();
                    payment.setPensionerId(pensionId);
                    payment.setPaymentDate(nextPaymentDate);
                    payment.setAmount(amount);
                    payment.setStatus(status);
                    newPayments.add(payment);
                }

                // Move to the next 28th day
                nextPaymentDate = nextPaymentDate.plusDays(30);
            }

            // Save all new payments to the database
            paymentRepository.saveAll(newPayments);

            // Fetch all payments for the given pensionerId
            List<Payment> payments = paymentRepository.findByPensionerId(pensionId);

            // Return the response as an array of payment objects
            return ResponseEntity.ok(payments);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error generating payments: " + e.getMessage());
        }
    }
    // 1. Get the total number of pensioners
    @GetMapping("/total-pensioners")
    public long getTotalPensioners() {
        return pensionerRepository.count();
    }

    // 2. Get the total number of pending applications
    @GetMapping("/total-pending-applications")
    public long getTotalPendingApplications() {
        return pensionApplicationRepository.countByStatus("pending");
    }

    // 3. Get the total pension amount
    @GetMapping("/total-pension-amount")
    public double getTotalPensionAmount() {
        return paymentRepository.findAll().stream()
                .mapToDouble(Payment::getAmount)  // Assuming `getAmount()` returns the payment amount
                .sum();
    }

    @PostMapping("/approve-application/{appId}")
    public ResponseEntity<String> approveApplication(@PathVariable String appId) {
        Optional<PensionApplication> applicationOpt = pensionApplicationRepository.findByPensionerId(appId);
        if (applicationOpt.isPresent()) {
            PensionApplication application = applicationOpt.get();
            application.setStatus("Approved");
            pensionApplicationRepository.save(application);
            return ResponseEntity.ok("Application approved");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Application not found");
        }
    }

    @PostMapping("/reject-application/{appId}")
    public ResponseEntity<String> rejectApplication(@PathVariable String appId) {
        Optional<PensionApplication> applicationOpt = pensionApplicationRepository.findByPensionerId(appId);
        if (applicationOpt.isPresent()) {
            PensionApplication application = applicationOpt.get();
            application.setStatus("Rejected");
            pensionApplicationRepository.save(application);
            return ResponseEntity.ok("Application rejected");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Application not found");
        }
    }

    // Helper method to determine pension amount based on category
    private int determinePensionAmount(String pensionCategory) {
        switch (pensionCategory) {
            case "oldAge":
                return 2000;
            case "widow":
                return 2500;
            case "disabled":
                return 3000;
            case "weaver":
                return 1800;
            case "toddyTappers":
                return 2200;
            case "fishermen":
                return 2400;
            case "art":
                return 2600;
            case "dialysis":
                return 3200;
            default:
                return 0; // Default amount if no valid category is found
        }
    }
}