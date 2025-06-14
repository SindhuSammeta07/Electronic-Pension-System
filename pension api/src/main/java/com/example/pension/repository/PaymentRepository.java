package com.example.pension.repository;

import com.example.pension.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByPensionerIdAndPaymentDate(String pensionerId, LocalDate paymentDate);
    List<Payment> findByPensionerId(String pensionerId);
}
