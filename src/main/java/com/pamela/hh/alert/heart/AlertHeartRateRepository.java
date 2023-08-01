package com.pamela.hh.alert.heart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface AlertHeartRateRepository extends JpaRepository<AlertHeartRate, String> {

    @Transactional
    @Modifying
    @Query("DELETE FROM AlertHeartRate a WHERE a.patient.id = ?1")
    void deleteByPatientId(Long id);
}
