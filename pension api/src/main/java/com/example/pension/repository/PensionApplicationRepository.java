package com.example.pension.repository;

import com.example.pension.model.PensionApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PensionApplicationRepository extends JpaRepository<PensionApplication, Long> {
    Optional<PensionApplication> findByPensionerId(String pensionerId);
    public long countByStatus(String status);

}
