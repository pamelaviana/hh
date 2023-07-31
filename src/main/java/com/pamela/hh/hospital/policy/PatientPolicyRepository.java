package com.pamela.hh.hospital.policy;

import com.pamela.hh.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface PatientPolicyRepository extends JpaRepository<PatientPolicy, Long> {

    @Query("SELECT p FROM PatientPolicy p WHERE p.firstName = :firstName AND p.lastName = :lastName AND p.email = :email")
    Optional<PatientPolicy> findByFirstNameAndLastNameAndEmail(String firstName, String lastName, String email);
}
