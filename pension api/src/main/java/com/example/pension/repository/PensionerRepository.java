package com.example.pension.repository;

import com.example.pension.model.Pensioner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PensionerRepository extends JpaRepository<Pensioner, Long> {

    // Custom query method to find a Pensioner by their email
    Optional<Pensioner> findByEmail(String email);
    Optional<Pensioner> findByEmailAndPassword(String email, String password);
    Optional<Pensioner> findByPensionerId(String pensionerId);

}
