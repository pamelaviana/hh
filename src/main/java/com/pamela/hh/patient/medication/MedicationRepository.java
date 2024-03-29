package com.pamela.hh.patient.medication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface MedicationRepository extends JpaRepository<Medication, String> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Medication m WHERE m.patient.id = ?1")
    void deleteByPatientId(Long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Medication m WHERE m.doctor.id = ?1")
    void deleteByDoctorId(Long id);

    @Query("SELECT m FROM Medication m WHERE m.patient.id = ?1")
    Optional<List<Medication>> findByPatientId(Long id);

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END FROM Medication m WHERE m.doctor.id = ?1")
    Optional<Boolean> existsByDoctorId(Long id);
}
