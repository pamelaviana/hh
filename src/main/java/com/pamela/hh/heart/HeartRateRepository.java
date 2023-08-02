package com.pamela.hh.heart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface HeartRateRepository extends JpaRepository<HeartRate, Long> {

    @Query("SELECT h FROM HeartRate h WHERE h.id = ?1")
    Optional<HeartRate> findByUUID(String id);

    @Transactional
    @Modifying
    @Query("DELETE FROM HeartRate h WHERE h.user.id = ?1")
    void deleteByUserId(Long id);

    @Query("SELECT h FROM HeartRate h WHERE h.user.id = ?1")
    Optional<List<HeartRate>> findByPatientId(Long id);

    @Query("SELECT h FROM HeartRate h WHERE h.user.id = ?1 ORDER BY h.timestamp DESC")
    Optional<List<HeartRate>> findTopByPatientIdOrderByDateDesc(Long id);

    @Query("SELECT CASE WHEN COUNT(h) > 0 THEN TRUE ELSE FALSE END FROM HeartRate h WHERE h.user.id = ?1")
    Optional<Boolean> existsByUserId(Long id);
}
